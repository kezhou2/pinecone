/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;

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
		bundle = BundleHelper.getBundle(device.getSymbolicName());
		
		scheduler = new HttpClientScheduler();
		scheduler.setEndpoint(this);
		scheduler.start();
		
		getBuilder().initializeReadQueue(scheduler.getReadQueue());
		
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
	 */
	private AbstractProtocolBuilder getBuilder() {
		try {
			Class<?> builderClass = Class.forName(BundleHelper.getPackageName(bundle.getSymbolicName()) + "ProtocolBuilder");
			Constructor<?> builderConstructor = builderClass.getDeclaredConstructor(Bundle.class);
			return (AbstractProtocolBuilder) builderConstructor.newInstance(bundle);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param device
	 */
	protected abstract void execute(Device device);

}
