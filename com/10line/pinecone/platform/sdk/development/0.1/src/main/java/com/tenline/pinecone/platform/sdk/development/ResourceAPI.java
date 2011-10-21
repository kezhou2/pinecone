/**
 * 
 */
package com.tenline.pinecone.platform.sdk.development;

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
	 */
	public ResourceAPI(String host, String port) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}

}
