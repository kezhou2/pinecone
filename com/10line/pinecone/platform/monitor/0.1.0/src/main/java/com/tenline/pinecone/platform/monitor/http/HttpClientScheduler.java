/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.AbstractScheduler;
import com.tenline.pinecone.platform.monitor.BundleHelper;
import com.tenline.pinecone.platform.monitor.Publisher;
import com.tenline.pinecone.platform.monitor.ServiceHelper;

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
		((AbstractProtocolBuilder) ServiceHelper.waitForService
		(AbstractProtocolBuilder.class, bundle.getSymbolicName(), bundle.getVersion().toString()))
		.initializeReadQueue(getReadQueue());
		executor = (AbstractHttpClientProtocolExecutor) ServiceHelper.waitForService
			(AbstractHttpClientProtocolExecutor.class, bundle.getSymbolicName(), bundle.getVersion().toString());
		publisher = new Publisher();
		publisher.setDevice(device);
		client = new DefaultHttpClient();
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
