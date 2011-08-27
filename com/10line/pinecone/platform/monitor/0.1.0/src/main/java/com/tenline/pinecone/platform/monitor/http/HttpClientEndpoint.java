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
	 * Scheduler
	 */
	private HttpScheduler scheduler;

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
		scheduler.stop();
		subscriber.stop();
		logger.info("Close Endpoint");
	}

	@Override
	public void initialize(Device device) {
		// TODO Auto-generated method stub
		try {
			Bundle bundle = BundleHelper.getBundle(device.getSymbolicName());
			String symbolicName = bundle.getSymbolicName();
			String tempName = symbolicName.substring(symbolicName.lastIndexOf(".") + 1);
			String name = String.valueOf(tempName.charAt(0)).toUpperCase() + tempName.substring(1);
			String packageName = symbolicName + "." + name;
			Class<?> decoderClass = Class.forName(packageName + "ProtocolDecoder");
			Constructor<?> decoderConstructor = decoderClass.getDeclaredConstructor(Bundle.class);
			AbstractHttpProtocolDecoder decoder = (AbstractHttpProtocolDecoder) decoderConstructor.newInstance(bundle);
			Class<?> encoderClass = Class.forName(packageName + "ProtocolEncoder");
			Constructor<?> encoderConstructor = encoderClass.getDeclaredConstructor(Bundle.class);
			AbstractHttpProtocolEncoder encoder = (AbstractHttpProtocolEncoder) encoderConstructor.newInstance(bundle);
			Class<?> builderClass = Class.forName(packageName + "ProtocolBuilder");
			Constructor<?> builderConstructor = builderClass.getDeclaredConstructor(Bundle.class);
			scheduler = new HttpScheduler((AbstractProtocolBuilder) builderConstructor.newInstance(bundle));
			scheduler.setEncoder(encoder);
			encoder.setClient(client);
			scheduler.start();
			subscriber = new Subscriber();
			subscriber.setDevice(device);
			subscriber.setScheduler(scheduler);
			subscriber.start();
			publisher = new Publisher();
			publisher.setDevice(device);
			decoder.setPublisher(publisher);
			logger.info("Initialize Endpoint");
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
	}

}
