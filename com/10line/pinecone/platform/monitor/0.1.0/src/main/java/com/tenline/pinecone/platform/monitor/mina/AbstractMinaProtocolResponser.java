/**
 * 
 */
package com.tenline.pinecone.platform.monitor.mina;

import org.apache.asyncweb.client.AsyncHttpClientCallback;
import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.monitor.Publisher;

/**
 * @author Bill
 *
 */
public abstract class AbstractMinaProtocolResponser implements
		AsyncHttpClientCallback {

	/**
	 * Protocol Bundle
	 */
	protected Bundle bundle;
	
	/**
	 * Protocol Logger
	 */
	protected Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Protocol Requester
	 */
	private AbstractMinaProtocolRequester requester;
	
	/**
	 * Publisher
	 */
	protected Publisher publisher;

	/**
	 * 
	 * @param bundle
	 */
	public AbstractMinaProtocolResponser(Bundle bundle) {
		// TODO Auto-generated constructor stub
		this.bundle = bundle;
	}
	
	@Override
	public void onSent() {
		// TODO Auto-generated method stub
		requester.execute();
		logger.info("sent!");
	}

	@Override
	public void onException(Throwable cause) {
		// TODO Auto-generated method stub
		logger.error(cause.getMessage());
	}

	@Override
	public void onClosed() {
		// TODO Auto-generated method stub
		logger.info("closed!");
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

	/**
	 * @param requester the requester to set
	 */
	public void setRequester(AbstractMinaProtocolRequester requester) {
		this.requester = requester;
	}

	/**
	 * @return the requester
	 */
	public AbstractMinaProtocolRequester getRequester() {
		return requester;
	}
	
}
