/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.net.HttpURLConnection;

/**
 * @author Bill
 *
 */
public abstract class AbstractAPI {
	
	protected String url;
	
	protected final static int TIMEOUT = 5000;
	
	protected HttpURLConnection connection;
	
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
