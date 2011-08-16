/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import org.apache.mina.core.session.IoSession;
import org.osgi.framework.Bundle;
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
	
	private String snsId = "251760162";
	
	private UserAPI userAPI;
	
	private DeviceAPI deviceAPI;
	
	private BundleContext bundleContext;
	
	private ArrayList<IEndpoint> endpoints;
	
	/**
	 * 
	 */
	public Activator() {
		// TODO Auto-generated constructor stub
		endpoints = new ArrayList<IEndpoint>();
		userAPI = new UserAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

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
		deviceAPI = new DeviceAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				Object[] devices = ((Collection<?>) message).toArray();
				for (int i=0; i<devices.length; i++) {
					try {
						Activator.this.initializeEndpoint((Device) devices[i]);
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
		
	}
	
	/**
	 * 
	 * @param device
	 * @throws Exception 
	 */
	private void initializeEndpoint(Device device) throws Exception {
		Hashtable<String, String> params = new Hashtable<String, String>();
		String symbolicName = device.getSymbolicName();
		String tempName = symbolicName.substring(symbolicName.lastIndexOf(".") + 1);
		String name = String.valueOf(tempName.charAt(0)).toUpperCase() + tempName.substring(1);
		params.put("packageName", symbolicName.replace("10line", "tenline") + "." + name);
		Bundle bundle = getBundle(symbolicName);
		params.put("port", bundle.getHeaders().get("port").toString());
		if (params.get("port").indexOf("COM") >= 0) {
			params.put("baudRate", bundle.getHeaders().get("Baud-Rate").toString());
			params.put("dataBits", bundle.getHeaders().get("Data-Bits").toString());
			params.put("stopBits", bundle.getHeaders().get("Stop-Bits").toString());
			params.put("parity", bundle.getHeaders().get("Parity").toString());
			params.put("flowControl", bundle.getHeaders().get("Flow-Control").toString());
			IEndpoint endpoint = new MinaSerialEndpoint();
			Hashtable<Device, IoSession> mapping = new Hashtable<Device, IoSession>();
			mapping.put(device, null);
			endpoint.initialize(params, mapping);
			endpoints.add(endpoint);
		} // TCP
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

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		this.bundleContext = bundleContext;
		userAPI.show("snsId=='" + snsId + "'");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		this.bundleContext = null;
		while (endpoints.size() > 0) {
			endpoints.get(0).close();
			endpoints.remove(0);
		}
	}

}
