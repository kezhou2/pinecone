/**
 * 
 */
package com.tenline.pinecone.platform.monitor.mina;

import org.apache.asyncweb.client.AsyncHttpClient;
import org.apache.log4j.Logger;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.IEndpoint;
import com.tenline.pinecone.platform.monitor.Publisher;
import com.tenline.pinecone.platform.monitor.ServiceHelper;
import com.tenline.pinecone.platform.monitor.Subscriber;

/**
 * @author Bill
 *
 */
public class MinaHttpClientEndpoint implements IEndpoint {

	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Requester
	 */
	private AbstractMinaProtocolRequester requester;
	
	/**
	 * Responser
	 */
	private AbstractMinaProtocolResponser responser;
	
	/**
	 * Builder
	 */
	private AbstractProtocolBuilder builder;
	
	/**
	 * Subscriber
	 */
	private Subscriber subscriber;
	
	/**
	 * Publisher
	 */
	private Publisher publisher;
	
	/**
	 * 
	 */
	public MinaHttpClientEndpoint() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		requester.getClient().disconnect();
		publisher.stop();
		requester.stop();
		subscriber.stop();
		logger.info("Close Endpoint");
	}

	@Override
	public void initialize(Device device) {
		// TODO Auto-generated method stub
		publisher = new Publisher();
		publisher.setDevice(device);
		publisher.start();
		responser = (AbstractMinaProtocolResponser) ServiceHelper.waitForService
				(AbstractMinaProtocolResponser.class, device.getSymbolicName(), device.getVersion());
		responser.setPublisher(publisher);
		requester = (AbstractMinaProtocolRequester) ServiceHelper.waitForService
				(AbstractMinaProtocolRequester.class, device.getSymbolicName(), device.getVersion());
		requester.setClient(new AsyncHttpClient(new NioSocketConnector()));
		requester.setResponser(responser);
		responser.setRequester(requester);
		requester.start();
		builder = (AbstractProtocolBuilder) ServiceHelper.waitForService
				(AbstractProtocolBuilder.class, device.getSymbolicName(), device.getVersion());
		builder.initializeReadQueue(requester.getReadQueue());
		subscriber = new Subscriber();
		subscriber.setDevice(device);
		subscriber.setScheduler(requester);
		subscriber.start();
		logger.info("Initialize Endpoint");
	}

}
