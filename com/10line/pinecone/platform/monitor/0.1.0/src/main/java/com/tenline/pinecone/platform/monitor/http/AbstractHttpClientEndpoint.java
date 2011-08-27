/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.httpclient.HttpClient;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.AbstractScheduler;
import com.tenline.pinecone.platform.monitor.BundleHelper;
import com.tenline.pinecone.platform.monitor.IEndpoint;
import com.tenline.pinecone.platform.monitor.Publisher;
import com.tenline.pinecone.platform.monitor.Subscriber;

/**
 * @author Bill
 *
 */
public abstract class AbstractHttpClientEndpoint extends AbstractScheduler implements IEndpoint {
	
	/**
	 * Http Client
	 */
	protected HttpClient client;
	
	/**
	 * Publisher
	 */
	protected Publisher publisher;
	
	/**
	 * Subscriber
	 */
	protected Subscriber subscriber;
	
	/**
	 * 
	 */
	public AbstractHttpClientEndpoint() {
		super();
		// TODO Auto-generated constructor stub
		client = new HttpClient();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		stop();
		subscriber.stop();
		logger.info("Close Endpoint");
	}

	@Override
	public void initialize(Device device) {
		// TODO Auto-generated method stub
		getBuilder(device).initializeReadQueue(getReadQueue());
		subscriber = new Subscriber();
		subscriber.setDevice(device);
		subscriber.setScheduler(this);
		subscriber.start();
		publisher = new Publisher();
		publisher.setDevice(device);
		start();
		logger.info("Initialize Endpoint");
	}
	
	/**
	 * 
	 * @param device
	 * @return
	 */
	private AbstractProtocolBuilder getBuilder(Device device) {
		AbstractProtocolBuilder builder = null;
		try {
			Bundle bundle = BundleHelper.getBundle(device.getSymbolicName());
			String symbolicName = bundle.getSymbolicName();
			String tempName = symbolicName.substring(symbolicName.lastIndexOf(".") + 1);
			String name = String.valueOf(tempName.charAt(0)).toUpperCase() + tempName.substring(1);
			String packageName = symbolicName + "." + name;
			Class<?> builderClass = Class.forName(packageName + "ProtocolBuilder");
			Constructor<?> builderConstructor = builderClass.getDeclaredConstructor(Bundle.class);
			builder = (AbstractProtocolBuilder) builderConstructor.newInstance(bundle);
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
		return builder;
	}

}
