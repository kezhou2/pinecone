/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import org.apache.http.HttpResponse;
import org.apache.http.nio.concurrent.FutureCallback;
import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.monitor.Publisher;

/**
 * @author Bill
 *
 */
public abstract class AbstractHttpClientProtocolDecoder implements
		FutureCallback<HttpResponse> {

	/**
	 * Protocol Bundle
	 */
	protected Bundle bundle;
	
	/**
	 * Protocol Logger
	 */
	protected Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Publisher
	 */
	protected Publisher publisher;
	
	/**
	 * 
	 * @param bundle
	 */
	public AbstractHttpClientProtocolDecoder(Bundle bundle) {
		// TODO Auto-generated constructor stub
		this.bundle = bundle;
	}

	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the publisher
	 */
	public Publisher getPublisher() {
		return publisher;
	}

}
