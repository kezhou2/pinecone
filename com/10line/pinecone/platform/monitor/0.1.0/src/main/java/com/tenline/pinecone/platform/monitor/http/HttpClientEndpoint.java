/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.IEndpoint;
import com.tenline.pinecone.platform.monitor.Subscriber;

/**
 * @author Bill
 *
 */
public class HttpClientEndpoint implements IEndpoint {
	
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Scheduler
	 */
	private HttpClientScheduler scheduler;
	
	/**
	 * Subscriber
	 */
	private Subscriber subscriber;

	/**
	 * 
	 */
	public HttpClientEndpoint() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		scheduler.stop();
		subscriber.stop();
		scheduler.getClient().getConnectionManager().shutdown();
		logger.info("Close Endpoint");
	}

	@Override
	public void initialize(Device device) {
		// TODO Auto-generated method stub
		scheduler = new HttpClientScheduler(device);
		scheduler.start();
		
		subscriber = new Subscriber();
		subscriber.setDevice(device);
		subscriber.setScheduler(scheduler);
		subscriber.start();
		
		logger.info("Initialize Endpoint");
	}

}
