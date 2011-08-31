/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.BundleHelper;
import com.tenline.pinecone.platform.monitor.IEndpoint;
import com.tenline.pinecone.platform.monitor.Publisher;
import com.tenline.pinecone.platform.monitor.Subscriber;

/**
 * @author Bill
 *
 */
public abstract class AbstractHttpClientEndpoint implements IEndpoint {
	
	/**
	 * Logger
	 */
	protected Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Bundle
	 */
	protected Bundle bundle;
	
	/**
	 * Http Client
	 */
	protected HttpClient client;
	
	/**
	 * Publisher
	 */
	protected Publisher publisher;
	
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
	public AbstractHttpClientEndpoint() {
		// TODO Auto-generated constructor stub
		client = new DefaultHttpClient();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		scheduler.stop();
		subscriber.stop();
		client.getConnectionManager().shutdown();
		logger.info("Close Endpoint");
	}

	@Override
	public void initialize(Device device) {
		// TODO Auto-generated method stub
		bundle = BundleHelper.getBundle(device.getSymbolicName(), device.getVersion());
		
		scheduler = new HttpClientScheduler();
		scheduler.setEndpoint(this);
		scheduler.start();
		
		try {
			getBuilder().initializeReadQueue(scheduler.getReadQueue());
		} catch (InvalidSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		subscriber = new Subscriber();
		subscriber.setDevice(device);
		subscriber.setScheduler(scheduler);
		subscriber.start();
		
		publisher = new Publisher();
		publisher.setDevice(device);
		
		logger.info("Initialize Endpoint");
	}
	
	/**
	 * 
	 * @return
	 * @throws InvalidSyntaxException
	 */
	private AbstractProtocolBuilder getBuilder() throws InvalidSyntaxException {
		BundleContext context = bundle.getBundleContext();
		String filter = "(&(symbolicName="+bundle.getSymbolicName()+")(version="+bundle.getVersion().toString()+"))";
		return (AbstractProtocolBuilder) context.getService(context.getServiceReferences
				(AbstractProtocolBuilder.class.getName(), filter)[0]);
	}
	
	/**
	 * 
	 * @param device
	 */
	protected abstract void execute(Device device);

}
