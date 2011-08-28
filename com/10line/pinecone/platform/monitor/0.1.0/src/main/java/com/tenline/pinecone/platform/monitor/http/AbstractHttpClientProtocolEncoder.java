/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;

/**
 * @author Bill
 *
 */
public abstract class AbstractHttpClientProtocolEncoder {

	/**
	 * Protocol Bundle
	 */
	protected Bundle bundle;
	
	/**
	 * Protocol Logger
	 */
	protected Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Protocol Decoder
	 */
	protected AbstractHttpClientProtocolDecoder decoder;
	
	/**
	 * Http Client
	 */
	protected HttpAsyncClient client;
	
	/**
	 * 
	 * @param bundle
	 */
	public AbstractHttpClientProtocolEncoder(Bundle bundle) {
		// TODO Auto-generated constructor stub
		this.bundle = bundle;
	}
	
	/**
	 * 
	 * @param device
	 */
	protected abstract void encode(Device device);

	/**
	 * @param decoder the decoder to set
	 */
	public void setDecoder(AbstractHttpClientProtocolDecoder decoder) {
		this.decoder = decoder;
	}

	/**
	 * @return the decoder
	 */
	public AbstractHttpClientProtocolDecoder getDecoder() {
		return decoder;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(HttpAsyncClient client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public HttpAsyncClient getClient() {
		return client;
	}

}
