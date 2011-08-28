/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.BundleHelper;
import com.tenline.pinecone.platform.monitor.IEndpoint;
import com.tenline.pinecone.platform.monitor.Publisher;
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
	 * Http Client
	 */
	private HttpAsyncClient client;
	
	/**
	 * Protocol Codec Factory
	 */
	private HttpClientProtocolCodecFactory factory;
	
	/**
	 * Scheduler
	 */
	private HttpClientScheduler scheduler;
	
	/**
	 * Publisher
	 */
	private Publisher publisher;
	
	/**
	 * Subscriber
	 */
	private Subscriber subscriber;

	/**
	 * 
	 */
	public HttpClientEndpoint() {
		// TODO Auto-generated constructor stub
		try {
			client = new DefaultHttpAsyncClient();
		} catch (IOReactorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		try {
			factory.close();
			scheduler.stop();
			subscriber.stop();
			client.shutdown();
			logger.info("Close Endpoint");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(Device device) {
		// TODO Auto-generated method stub
		factory = new HttpClientProtocolCodecFactory();
		factory.initialize(BundleHelper.getBundle(device.getSymbolicName()));
		factory.getEncoder().setClient(client);
		
		scheduler = new HttpClientScheduler();
		scheduler.setEncoder(factory.getEncoder());
		factory.getBuilder().initializeReadQueue(scheduler.getReadQueue());
		scheduler.start();
		
		subscriber = new Subscriber();
		subscriber.setDevice(device);
		subscriber.setScheduler(scheduler);
		subscriber.start();
		
		publisher = new Publisher();
		publisher.setDevice(device);
		factory.getDecoder().setPublisher(publisher);
		
		client.start();
		logger.info("Initialize Endpoint");
	}

}
