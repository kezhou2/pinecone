/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.monitor.http.HttpClientEndpoint;
import com.tenline.pinecone.platform.monitor.mina.MinaSerialEndpoint;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.DeviceAPI;

/**
 * @author Bill
 *
 */
public class EndpointAdmin {

	/**
	 * Web Service API
	 */
	private DeviceAPI deviceAPI;
	
	/**
	 * Endpoints
	 */
	private ArrayList<IEndpoint> endpoints;
	
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 
	 */
	public EndpointAdmin() {
		// TODO Auto-generated constructor stub
		deviceAPI = new DeviceAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub		
				Object[] devices = ((Collection<?>) message).toArray();
				for (int i = 0; i < devices.length; i++) {
					initializeEndpoint((Device) devices[i]);
				}		
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.error(error);
			}

		});
	}
	
	/**
	 * Initialize Endpoint Admin
	 * @param user
	 */
	public void initialize(User user) {
		try {
			endpoints = new ArrayList<IEndpoint>();
			deviceAPI.showByUser("id=='"+ user.getId() + "'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Close Endpoint Admin
	 */
	public void close() {
		while (endpoints.size() > 0) {
			endpoints.get(0).close();
			endpoints.remove(0);
		}
	}
	
	/**
	 * Initialize Endpoint
	 * @param device
	 */
	private void initializeEndpoint(Device device) {
		Bundle bundle = BundleHelper.getBundle(device.getSymbolicName(), device.getVersion());
		String type = bundle.getHeaders().get("Type").toString();
		IEndpoint endpoint = null;
		if (type.equals("Serial")) {
			endpoint = new MinaSerialEndpoint();
		} else if (type.equals("HttpClient")) {
			endpoint= new HttpClientEndpoint();
		}	
		endpoint.initialize(device);
		endpoints.add(endpoint);
	}

}
