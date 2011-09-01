/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.AbstractScheduler;
import com.tenline.pinecone.platform.monitor.BundleHelper;
import com.tenline.pinecone.platform.monitor.Publisher;

/**
 * @author Bill
 *
 */
public class HttpClientScheduler extends AbstractScheduler {
	
	/**
	 * Protocol Executor
	 */
	private AbstractHttpClientProtocolExecutor executor;
	
	/**
	 * Protocol Builder
	 */
	private AbstractProtocolBuilder builder;
	
	/**
	 * Http Client
	 */
	private HttpClient client;
	
	/**
	 * Publisher
	 */
	private Publisher publisher;

	/**
	 * 
	 * @param device
	 */
	public HttpClientScheduler(Device device) {
		super();
		// TODO Auto-generated constructor stub
		Bundle bundle = BundleHelper.getBundle(device.getSymbolicName(), device.getVersion());
		String filter = "(&(symbolicName="+bundle.getSymbolicName()+")(version="+bundle.getVersion().toString()+"))";
		waitForServices(filter, bundle.getBundleContext());
		builder.initializeReadQueue(getReadQueue());
		publisher = new Publisher();
		publisher.setDevice(device);
		client = new DefaultHttpClient();
	}
	
	/**
	 * 
	 * @param filter
	 * @param context
	 * @return
	 * @throws InvalidSyntaxException
	 */
	private AbstractProtocolBuilder getBuilderService(String filter, BundleContext context) throws InvalidSyntaxException {
		ServiceReference[] references = context.getServiceReferences(AbstractProtocolBuilder.class.getName(), filter);
		if(references != null) builder = (AbstractProtocolBuilder) context.getService(references[0]);
		return builder;
	}
	
	/**
	 * 
	 * @param filter
	 * @param context
	 * @return
	 * @throws InvalidSyntaxException
	 */
	private AbstractHttpClientProtocolExecutor getExecutorService(String filter, BundleContext context) throws InvalidSyntaxException {
		ServiceReference[] references = context.getServiceReferences(AbstractHttpClientProtocolExecutor.class.getName(), filter);
		if(references != null) executor = (AbstractHttpClientProtocolExecutor) context.getService(references[0]);
		return executor;
	}
	
	/**
	 * 
	 * @param filter
	 * @param context
	 */
	private void waitForServices(String filter, BundleContext context) {
		int secondsToWait = 10;
		int secondsPassed = 0;
		try {
			while (secondsPassed < secondsToWait && 
				   getExecutorService(filter, context) == null && 
				   getBuilderService(filter, context) == null) {
				Thread.sleep(++secondsPassed * 1000);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}
	
	@Override
	protected void dispatch(Device device) {
		executor.execute(client, device, publisher);
		super.dispatch(device);
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(HttpClient client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public HttpClient getClient() {
		return client;
	}

}
