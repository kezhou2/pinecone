/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.net.HttpURLConnection;

import com.tenline.pinecone.platform.sdk.oauth.AuthorizationAPI;

/**
 * @author Bill
 *
 */
public abstract class ResourceAPI extends AbstractAPI {
	
	protected final static int TIMEOUT = 10000;
	
	protected HttpURLConnection connection;
	
	protected AuthorizationAPI authorizationAPI;
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @param authorizationAPI
	 */
	public ResourceAPI(String host, String port, AuthorizationAPI authorizationAPI) {
		super(host, port);
		// TODO Auto-generated constructor stub
		this.authorizationAPI = authorizationAPI;
	}

}
