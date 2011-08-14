/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.osgi.monitor.mina.MinaSerialEndpoint;
import com.tenline.pinecone.platform.sdk.APIListener;
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
	
	private Hashtable<String, IEndpoint> endpoints;
	
	/**
	 * 
	 */
	public Activator() {
		// TODO Auto-generated constructor stub
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
		// Adjust Device Type
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("id", device.getId());
		String symbolicName = device.getSymbolicName();
		String tempName = symbolicName.substring(symbolicName.lastIndexOf(".") + 1);
		String name = String.valueOf(tempName.charAt(0)).toUpperCase() + tempName.substring(1);
		params.put("packageName", symbolicName.replace("10line", "tenline") + "." + name);
		// Setting up serial parameters
		params.put("port", "COM1");
		params.put("baudRate", "9600");
		params.put("dataBits", "8");
		params.put("stopBits", "1");
		params.put("parity", "none");
		params.put("flowControl", "none");
		//
		IEndpoint endpoint = new MinaSerialEndpoint();
		endpoint.initialize(bundleContext, params);
		endpoints.put(endpoint.toString(), endpoint);
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		this.bundleContext = bundleContext;
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
	}

}
