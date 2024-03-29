/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

/**
 * @author Bill
 *
 */
public abstract class AbstractAPI {
	
	protected String url;
	
	protected ClientRequest request;
	
	protected ClientResponse<?> response;
	
	protected APIListener listener;

	/**
	 * 
	 * @param host
	 * @param port
	 * @param listener
	 */
	public AbstractAPI(String host, String port, APIListener listener) {
		// TODO Auto-generated constructor stub
		url = "http://" + host + ":" + port;
		this.listener = listener;
	}

}
