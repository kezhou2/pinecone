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
	 * @param context
	 */
	public ResourceAPI(String host, String port, String context) {
		super(host, port, context);
		// TODO Auto-generated constructor stub
	}

}
