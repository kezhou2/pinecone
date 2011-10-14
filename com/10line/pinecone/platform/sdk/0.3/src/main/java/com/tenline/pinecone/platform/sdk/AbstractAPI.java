/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

/**
 * @author Bill
 *
 */
public abstract class AbstractAPI {

	protected String url;

	/**
	 * 
	 * @param host
	 * @param port
	 */
	public AbstractAPI(String host, String port) {
		// TODO Auto-generated constructor stub
		url = "http://" + host + ":" + port;
	}

}
