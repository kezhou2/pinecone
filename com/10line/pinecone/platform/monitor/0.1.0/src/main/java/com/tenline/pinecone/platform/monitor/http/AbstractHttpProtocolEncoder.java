/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.monitor.IProtocolEncoder;

/**
 * @author Bill
 *
 */
public abstract class AbstractHttpProtocolEncoder implements IProtocolEncoder {

	/**
	 * Protocol Bundle
	 */
	protected Bundle bundle;
	
	/**
	 * Protocol Logger
	 */
	protected Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Http Client
	 */
	protected HttpClient client;
	
	/**
	 * 
	 * @param bundle
	 */
	public AbstractHttpProtocolEncoder(Bundle bundle) {
		// TODO Auto-generated constructor stub
		this.bundle = bundle;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(HttpClient client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public HttpClient getClient() {
		return client;
	}

}
