/**
 * 
 */
package com.tenline.pinecone.platform.monitor.httpcomponents;

import org.apache.http.HttpResponse;
import org.apache.http.nio.concurrent.FutureCallback;
import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.Publisher;

/**
 * @author Bill
 *
 */
public abstract class AbstractProtocolResponser implements
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
	public AbstractProtocolResponser(Bundle bundle) {
		// TODO Auto-generated constructor stub
		this.bundle = bundle;
	}
	
	@Override
	public void cancelled() {
		// TODO Auto-generated method stub
		logger.info("cancelled");
	}

	@Override
	public void failed(Exception arg0) {
		// TODO Auto-generated method stub
		logger.error(arg0.getMessage());
	}
	
	/**
	 * Start Responser
	 * @param device
	 */
	public void start(Device device) {
		publisher = new Publisher();
		publisher.setDevice(device);
		publisher.start();
		logger.info("Start Responser");
	}
	
	/**
	 * Stop Responser
	 */
	public void stop() {
		publisher.stop();
		logger.info("Stop Responser");
	}
	
}
