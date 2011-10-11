/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.net.HttpURLConnection;

/**
 * @author Bill
 *
 */
public abstract class ResourceAPI extends AbstractAPI {
	
	protected final static int TIMEOUT = 10000;
	
	protected HttpURLConnection connection;
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @param listener
	 */
	public ResourceAPI(String host, String port, APIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
	}

}
