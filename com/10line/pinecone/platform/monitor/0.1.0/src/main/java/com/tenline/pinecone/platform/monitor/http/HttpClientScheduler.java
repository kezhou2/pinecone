/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractScheduler;

/**
 * @author Bill
 *
 */
public class HttpClientScheduler extends AbstractScheduler {
	
	/**
	 * Http Client Endpoint
	 */
	private AbstractHttpClientEndpoint endpoint;

	/**
	 * 
	 */
	public HttpClientScheduler() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void dispatch(Device device) {
		endpoint.execute(device);
		super.dispatch(device);
	}

	/**
	 * @param endpoint the endpoint to set
	 */
	public void setEndpoint(AbstractHttpClientEndpoint endpoint) {
		this.endpoint = endpoint;
	}

	/**
	 * @return the endpoint
	 */
	public AbstractHttpClientEndpoint getEndpoint() {
		return endpoint;
	}

}
