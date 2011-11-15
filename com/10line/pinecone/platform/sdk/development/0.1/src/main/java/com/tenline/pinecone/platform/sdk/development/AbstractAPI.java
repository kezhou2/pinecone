/**
 * 
 */
package com.tenline.pinecone.platform.sdk.development;

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
	 * @param context
	 */
	public AbstractAPI(String host, String port, String context) {
		// TODO Auto-generated constructor stub
		url = "http://" + host + ":" + port;
		if (context != null) url += "/" + context;
	}

}
