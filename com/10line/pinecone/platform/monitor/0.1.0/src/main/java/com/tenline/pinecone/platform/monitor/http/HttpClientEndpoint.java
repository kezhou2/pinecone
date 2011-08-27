/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.BundleHelper;
import com.tenline.pinecone.platform.monitor.IEndpoint;
import com.tenline.pinecone.platform.monitor.Publisher;
import com.tenline.pinecone.platform.monitor.Subscriber;
import com.tenline.pinecone.platform.monitor.mina.MinaScheduler;

/**
 * @author Bill
 *
 */
public class HttpClientEndpoint implements IEndpoint {
	
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(HttpClientEndpoint.class);
	
	/**
	 * Http Client
	 */
	private HttpClient client;
	
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
		client = new HttpClient();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		subscriber.stop();
		logger.info("Close Endpoint");
	}

	@Override
	public void initialize(Device device) {
		// TODO Auto-generated method stub
		try {
			subscriber = new Subscriber();
			subscriber.setDevice(device);
//			subscriber.setScheduler(scheduler);
			subscriber.start();
			publisher = new Publisher();
			publisher.setDevice(device);
			logger.info("Initialize Endpoint");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * 
	 * @param device
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private AbstractProtocolBuilder getBuilder(Device device) throws ClassNotFoundException, 
		SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, 
		IllegalAccessException, InvocationTargetException {
		Bundle bundle = BundleHelper.getBundle(device.getSymbolicName());
		String symbolicName = bundle.getSymbolicName();
		String tempName = symbolicName.substring(symbolicName.lastIndexOf(".") + 1);
		String name = String.valueOf(tempName.charAt(0)).toUpperCase() + tempName.substring(1);
		String packageName = symbolicName + "." + name;
		Class<?> builderClass = Class.forName(packageName + "ProtocolBuilder");
		Constructor<?> builderConstructor = builderClass.getDeclaredConstructor(Bundle.class);
		return (AbstractProtocolBuilder) builderConstructor.newInstance(bundle);
	}

}
