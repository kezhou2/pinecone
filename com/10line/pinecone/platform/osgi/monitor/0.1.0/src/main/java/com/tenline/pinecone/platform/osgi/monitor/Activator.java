/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
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
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.osgi.monitor.mina.MinaSerialEndpoint;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.ChannelAPI;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.VariableAPI;

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
	
	private VariableAPI variableAPI;
	
	private BundleContext bundleContext;
	
	private EventAdmin admin;
	
	private JAXBContext context;
	private Unmarshaller unmarshaller;
	private Marshaller marshaller;
	
	private Hashtable<String, IEndpoint> endpoints;
	private Hashtable<String, Scheduler> schedulers;
	private Hashtable<String, Handler> handlers;
	
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
		handlers = new Hashtable<String, Handler>();
		endpoints = new Hashtable<String, IEndpoint>();
		schedulers = new Hashtable<String, Scheduler>();
		userAPI = new UserAPI(host, port, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				Object[] users = ((Collection<?>) message).toArray();
				for (int i=0; i<users.length; i++) {
					try {
						deviceAPI.showByUser("id=='"+((User) users[i]).getId()+"'");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		deviceAPI = new DeviceAPI(host, port, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				Object[] devices = ((Collection<?>) message).toArray();
				for (int i=0; i<devices.length; i++) {
					Activator.this.initializeEndpoint((Device) devices[i]);
				}
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		variableAPI = new VariableAPI(host, port, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				Object[] variables = ((Collection<?>) message).toArray();
				for (int i=0; i<variables.length; i++) {
					Variable variable = (Variable) variables[i];
					if (variable.getType().indexOf("read") >= 0) {
						Device device = new Device();
						device.setVariables(new ArrayList<Variable>());
						Variable newVariable = new Variable();
						newVariable.setName(variable.getName()); 
						// variables.size() == 1, variables.get(0).getName() != null, items == null
						device.getVariables().add(newVariable);
						schedulers.get(variable.getDevice().getId()).addToReadQueue(device);
					}
					if (i == variables.length - 1) {
						schedulers.get(variable.getDevice().getId()).start();
					}
				}
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
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
		schedulers.put(device.getId(), new Scheduler(device.getId(), endpointId));
		handlers.put(device.getId(), new Handler(device.getId(), endpointId));
		try {
			variableAPI.showByDevice("id=='"+device.getId()+"'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class Scheduler {
		
		/**
		 * Write Queue
		 */
		private LinkedList<Device> writeQueue;
		
		/**
		 * Read Queue
		 */
		private LinkedList<Device> readQueue;
		
		/**
		 * Read Queue Index
		 */
		private int readIndex;
		
		/**
		 * Scheduler Timer
		 */
		private Timer timer;
		
		/**
		 * Scheduler Timer Task
		 */
		private TimerTask timerTask;
		
		/**
		 * Scheduler Timer Task Interval
		 */
		private static final int INTERVAL = 100;
		
		/**
		 * Scheduler Timer Task Interval After Task Starting
		 */
		private static final int AFTER_START_INTERVAL = 5000;
		
		/**
		 * Scheduler Last Queue Item
		 */
		private Device lastQueueItem;
		
		/**
		 * Scheduler Last Queue Item Type
		 */
		private int lastQueueItemType;
		private static final int NULL = 0;
		private static final int CONTROL = 1;
		private static final int QUERY = 2;
		
		/**
		 * Scheduler Write Queue Try Counter 
		 */
		private int writeQueueTryCounter;
		
		/**
		 * Scheduler Write Queue Max Try Times
		 */
		private static final int MAX_TRY_TIMES = 3;
		
		/**
		 * Endpoint's Id
		 */
		private String endpointId;
		
		/**
		 * Device's Id
		 */
		private String deviceId;
		
		/**
		 * Channel API
		 */
		private ChannelAPI channelAPI;
		
		/**
		 * 
		 * @param deviceId
		 * @param endpointId
		 */
		public Scheduler(String deviceId, String endpointId) {
			writeQueue = new LinkedList<Device>();
			readQueue = new LinkedList<Device>();
			this.endpointId = endpointId;
			this.deviceId = deviceId;
			channelAPI = new ChannelAPI(host, port, new APIListener() {
				
				private String oldName;

				@Override
				public void onMessage(Object message) {
					// TODO Auto-generated method stub
					try {
						JSONObject obj = new JSONObject(new String((byte[]) message, "utf-8"));
						Device device = (Device) unmarshaller.unmarshal(new MappedXMLStreamReader(obj, 
								new MappedNamespaceConvention(new Configuration()))); 
						// variables.size() == 1, variables.get(0).getName() != null
						// items.size() == 1, items.get(0).getValue() != null
						String name = ((Variable) device.getVariables().toArray()[0]).getName();
						if (!oldName.equals(name)) {
							Scheduler.this.addToWriteQueue(device);
							oldName = name;
						}
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
		
		/**
		 * Start Scheduler
		 */
		public void start() {
			timer = new Timer();
			timerTask = new TimerTask(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (lastQueueItemType == CONTROL) {				
						if (writeQueueTryCounter < MAX_TRY_TIMES) {
							writeQueue.addFirst(lastQueueItem);	
							writeQueueTryCounter++;
						} else {
							writeQueueTryCounter = 0;
						}
					}
					execute();
					try {
						channelAPI.subscribe(deviceId + "-application");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			};
			timer.schedule(timerTask, AFTER_START_INTERVAL, INTERVAL);
		}
		
		/**
		 * Execute Scheduler
		 */
		public void execute() {
			if (!writeQueue.isEmpty()) {
				lastQueueItem = writeQueue.removeFirst();
				lastQueueItemType = CONTROL;
				dispatch(lastQueueItem);
			} else {
				lastQueueItem = null;
				lastQueueItemType = NULL;
				if (!readQueue.isEmpty()) {
					readIndex %= readQueue.size();
					lastQueueItem = readQueue.get(readIndex);
					lastQueueItemType = QUERY;
					dispatch(lastQueueItem);
					readIndex++;	
				} else {
					lastQueueItem = null;
					lastQueueItemType = NULL;
				}
			}
		}
		
		/**
		 * Dispatch to endpoint
		 * @param device
		 */
		private void dispatch(Device device) {
			Dictionary<String, Object> dic = new Hashtable<String, Object>();
			dic.put("message", device);
			admin.postEvent(new Event("endpoint/write/" + endpointId, dic));
		}
		
		/**
		 * Stop Scheduler
		 */
		public void stop() {
			timerTask.cancel();
			timer.purge();
			writeQueue.clear();
			readQueue.clear();
		}
		
		/**
		 * 
		 * @param device
		 */
		public void addToWriteQueue(Device device) {
			writeQueue.addLast(device);
		}
		
		/**
		 * 
		 * @param device
		 */
		@SuppressWarnings("unused")
		public void removeFromWriteQueue(Device device) {
			writeQueue.remove(device);
		}
		
		/**
		 * 
		 * @param device
		 */
		public void addToReadQueue(Device device) {
			readQueue.addLast(device);
		}
		
		/**
		 * 
		 * @param device
		 */
		@SuppressWarnings("unused")
		public void removeFromReadQueue(Device device) {
			readQueue.remove(device);
		}
		
	}
	
	private class Handler implements EventHandler {
		
		private String deviceId;
		private ChannelAPI channelAPI;
		private ServiceRegistration registration;
		
		/**
		 * 
		 * @param deviceId
		 * @param endpointId
		 */
		public Handler(String deviceId, String endpointId) {
			this.deviceId = deviceId;
			setRegistration(bundleContext.registerService(EventHandler.class.getName(), this, getProperties(endpointId)));
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
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
			try {
				Device device = (Device) event.getProperty("message");
				Variable variable = (Variable) device.getVariables().toArray()[0];
				if (variable.getItems() == null) { // write response
					Scheduler scheduler = schedulers.get(deviceId);
					scheduler.lastQueueItemType = Scheduler.NULL;
				}
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				marshaller.marshal(device, new MappedXMLStreamWriter(
						new MappedNamespaceConvention(new Configuration()), new OutputStreamWriter(output, "utf-8")));
				channelAPI.publish(deviceId + "-device", "application/json", output.toByteArray());
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
		for (Enumeration<String> i = schedulers.keys(); i.hasMoreElements();) {
			String key = i.nextElement();
			schedulers.get(key).stop();
			schedulers.remove(key);
		}
		for (Enumeration<String> i = handlers.keys(); i.hasMoreElements();) {
			String key = i.nextElement();
			handlers.get(key).getRegistration().unregister();
			handlers.remove(key);
		}
	}

}
