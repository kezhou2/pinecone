/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.Publisher;

/**
 * @author Bill
 *
 */
public abstract class AbstractHttpClientProtocolExecutor {

	/**
	 * Protocol Bundle
	 */
	protected Bundle bundle;
	
	/**
	 * Protocol Logger
	 */
	protected Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 
	 * @param bundle
	 */
	public AbstractHttpClientProtocolExecutor(Bundle bundle) {
		// TODO Auto-generated constructor stub
		this.bundle = bundle;
	}
	
	/**
	 * 
	 * @param client
	 * @param device
	 * @param publisher
	 */
	protected abstract void execute(HttpClient client, Device device, Publisher publisher);

}
