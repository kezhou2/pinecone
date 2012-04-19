/**
 * 
 */
package com.tenline.pinecone.platform.monitor.desktop;

import java.util.ArrayList;

import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractEndpointAdmin;
import com.tenline.pinecone.platform.monitor.BundleHelper;
import com.tenline.pinecone.platform.monitor.IEndpoint;
import com.tenline.pinecone.platform.monitor.httpcomponents.HttpClientEndpoint;
import com.tenline.pinecone.platform.monitor.mina.MinaSerialEndpoint;

/**
 * @author Bill
 *
 */
public class EndpointAdmin extends AbstractEndpointAdmin {

	/**
	 * 
	 */
	public EndpointAdmin() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initializeEndpoint(ArrayList<IEndpoint> endpoints, Device device) {
		// TODO Auto-generated method stub
		Bundle bundle = BundleHelper.getBundle(device);
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
