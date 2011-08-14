/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.osgi.monitor.mina.MinaSerialEndpoint;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.ChannelAPI;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;

/**
 * @author Bill
 *
 */
public class Activator implements BundleActivator {
	
	private String host = "pinecone.web.service.10line.cc";
	
	private String port = "80";
	
	private String snsId = "251760162";
	
	private UserAPI userAPI;
	
	private DeviceAPI deviceAPI;
	
	private BundleContext bundleContext;
	
	private EventAdmin admin;
	
	private JAXBContext context;
	private Unmarshaller unmarshaller;
	private Marshaller marshaller;
	
	private Hashtable<String, IEndpoint> endpoints;
	private Hashtable<String, Timer> timers;
	private Hashtable<String, MessageHandler> handlers;
	
	/**
	 * 
	 */
	public Activator() {
		// TODO Auto-generated constructor stub
		try {
			context = JAXBContext.newInstance(Device.class);
			marshaller = context.createMarshaller();
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timers = new Hashtable<String, Timer>();
		handlers = new Hashtable<String, MessageHandler>();
		endpoints = new Hashtable<String, IEndpoint>();
		userAPI = new UserAPI(host, port, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				User user = (User) ((Collection<?>) message).toArray()[0];
				try {
					deviceAPI.showByUser("id=='"+user.getId()+"'");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				
			}
			
		});
		deviceAPI = new DeviceAPI(host, port, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				Device device = (Device) ((Collection<?>) message).toArray()[0];
				Activator.this.initializeEndpoint(device);
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	/**
	 * 
	 * @param device
	 */
	private void initializeEndpoint(Device device) {
		Hashtable<String, String> params = new Hashtable<String, String>();
		String symbolicName = device.getSymbolicName();
		String tempName = symbolicName.substring(symbolicName.lastIndexOf(".") + 1);
		String name = String.valueOf(tempName.charAt(0)).toUpperCase() + tempName.substring(1);
		params.put("packageName", symbolicName.replace("10line", "tenline") + "." + name);
		Bundle bundle = getBundle(symbolicName);
		params.put("port", bundle.getHeaders().get("port").toString());
		IEndpoint endpoint = null;
		String endpointId = null;
		if (params.get("port").indexOf("COM") >= 0) {
			endpointId = device.getId();
			params.put("baudRate", bundle.getHeaders().get("Baud-Rate").toString());
			params.put("dataBits", bundle.getHeaders().get("Data-Bits").toString());
			params.put("stopBits", bundle.getHeaders().get("Stop-Bits").toString());
			params.put("parity", bundle.getHeaders().get("Parity").toString());
			params.put("flowControl", bundle.getHeaders().get("Flow-Control").toString());
			endpoint = new MinaSerialEndpoint();
		} // TCP
		params.put("id", endpointId);
		endpoint.initialize(bundleContext, params);
		endpoints.put(endpointId, endpoint);
		Timer timer = new Timer();
		timer.schedule(new PollingTask(device.getId(), endpointId), 0, 1000);
		timers.put(device.getId(), timer);
		handlers.put(device.getId(), new MessageHandler(device.getId(), endpointId));
	}
	
	private class PollingTask extends TimerTask {
		
		private String id;
		private String subject;
		private ChannelAPI channelAPI;
		
		/**
		 * 
		 * @param subject
		 * @param id
		 */
		public PollingTask(String subject, String id) {
			this.subject = subject + "-application";
			this.id = id;
			channelAPI = new ChannelAPI(host, port, new APIListener() {

				@Override
				public void onMessage(Object message) {
					// TODO Auto-generated method stub
					try {
						Dictionary<String, Object> dic = new Hashtable<String, Object>();
						JSONObject obj = new JSONObject(new String((byte[]) message, "utf-8"));
						dic.put("message", unmarshaller.unmarshal(new MappedXMLStreamReader(obj, 
								new MappedNamespaceConvention(new Configuration()))));
						admin.postEvent(new Event("endpoint/write/" + PollingTask.this.id, dic));
					} catch (JAXBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (XMLStreamException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onError(String error) {
					// TODO Auto-generated method stub
					System.out.println(error);
				}
				
			});
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				channelAPI.subscribe(subject);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private class MessageHandler implements EventHandler {
		
		private String subject;
		private ChannelAPI channelAPI;
		private ServiceRegistration registration;
		
		/**
		 * 
		 * @param subject
		 * @param id
		 */
		public MessageHandler(String subject, String id) {
			this.subject = subject + "-device";
			setRegistration(bundleContext.registerService(EventHandler.class.getName(), this, getProperties(id)));
			channelAPI = new ChannelAPI(host, port, new APIListener() {

				@Override
				public void onMessage(Object message) {
					// TODO Auto-generated method stub
					System.out.println(message);
				}

				@Override
				public void onError(String error) {
					// TODO Auto-generated method stub
					System.out.println(error);
				}
				
			});
		}

		@Override
		public void handleEvent(Event arg0) {
			// TODO Auto-generated method stub
			try {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] bytes = new byte[1024];
				marshaller.marshal(arg0.getProperty("message"), new MappedXMLStreamWriter(
						new MappedNamespaceConvention(new Configuration()), 
						new OutputStreamWriter(output, "utf-8")));
				output.write(bytes); // refactor, may be bug
				channelAPI.publish(subject, "application/json", bytes);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * Get Service Properties
		 * @return
		 */
		private Hashtable<String, String> getProperties(String id) {
			Hashtable<String, String> properties = new Hashtable<String, String>();
			properties.put(EventConstants.EVENT_TOPIC, "endpoint/read/" + id);
			return properties;
		}

		/**
		 * @param registration the registration to set
		 */
		public void setRegistration(ServiceRegistration registration) {
			this.registration = registration;
		}

		/**
		 * @return the registration
		 */
		public ServiceRegistration getRegistration() {
			return registration;
		}
		
	}
	
	/**
	 * 
	 * @param symbolicName
	 * @return
	 */
	private Bundle getBundle(String symbolicName) {
		for (int i=0; i<bundleContext.getBundles().length; i++) {
			Bundle bundle = bundleContext.getBundles()[i];
			if (bundle.getSymbolicName().equals(symbolicName)) {
				return bundle;
			}
		}
		// Query OSGI Bundle Repository
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		this.bundleContext = bundleContext;
		admin = (EventAdmin) bundleContext.getService(bundleContext.getServiceReference(EventAdmin.class.getName()));
		userAPI.show("snsId=='" + snsId + "'");
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		this.bundleContext = null;
		for (Enumeration<String> i = endpoints.keys(); i.hasMoreElements();) {
			String key = i.nextElement();
			endpoints.get(key).close();
			endpoints.remove(key);
		}
		for (Enumeration<String> i = timers.keys(); i.hasMoreElements();) {
			String key = i.nextElement();
			timers.get(key).purge();
			timers.get(key).cancel();
			timers.remove(key);
		}
		for (Enumeration<String> i = handlers.keys(); i.hasMoreElements();) {
			String key = i.nextElement();
			handlers.get(key).getRegistration().unregister();
			handlers.remove(key);
		}
	}

}
