/**
 * 
 */
package com.tenline.pinecone.platform.monitor.httpcomponents;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.IEndpoint;
import com.tenline.pinecone.platform.monitor.ServiceHelper;
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
	 * Requester
	 */
	private AbstractProtocolRequester requester;
	
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
		requester.stop();
		subscriber.stop();
		logger.info("Close Endpoint");
	}

	@Override
	public void initialize(Device device) {
		// TODO Auto-generated method stub
		requester = (AbstractProtocolRequester) ServiceHelper.waitForService
				(AbstractProtocolRequester.class, device.getSymbolicName(), device.getVersion());
		subscriber = new Subscriber();
		subscriber.setDevice(device);
		subscriber.setScheduler(requester);
		subscriber.start();
		logger.info("Initialize Endpoint");
		requester.start(device);
	}

}
