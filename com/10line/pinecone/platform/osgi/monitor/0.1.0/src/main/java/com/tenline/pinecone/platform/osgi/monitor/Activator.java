/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

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
	
	private Hashtable<String, IEndpoint> endpoints;
	private Hashtable<String, Timer> timers;
	
	/**
	 * 
	 */
	public Activator() {
		// TODO Auto-generated constructor stub
		timers = new Hashtable<String, Timer>();
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
					Dictionary<String, Object> dic = new Hashtable<String, Object>();
					dic.put("message", new String((byte[]) message));
					admin.postEvent(new Event("endpoint/" + PollingTask.this.id, dic));
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
	}

}
