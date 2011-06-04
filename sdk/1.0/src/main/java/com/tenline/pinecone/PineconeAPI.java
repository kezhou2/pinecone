/**
 * 
 */
package com.tenline.pinecone;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

/**
 * @author Bill
 *
 */
public abstract class PineconeAPI {
	
	protected String url;
	
	protected ClientRequest request;
	
	protected ClientResponse<?> response;
	
	protected PineconeAPIListener listener;

	/**
	 * 
	 * @param host
	 * @param port
	 * @param listener
	 */
	public PineconeAPI(String host, String port, PineconeAPIListener listener) {
		// TODO Auto-generated constructor stub
		url = "http://" + host + ":" + port;
		this.listener = listener;
	}

}
