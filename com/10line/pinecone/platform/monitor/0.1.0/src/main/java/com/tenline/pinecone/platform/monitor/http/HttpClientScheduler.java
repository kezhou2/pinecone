/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;

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
		try {
			Bundle bundle = BundleHelper.getBundle(device.getSymbolicName(), device.getVersion());
			BundleContext context = bundle.getBundleContext();
			String filter = "(&(symbolicName="+bundle.getSymbolicName()+")(version="+bundle.getVersion().toString()+"))";
			AbstractProtocolBuilder builder = (AbstractProtocolBuilder) context.getService(context.getServiceReferences
					(AbstractProtocolBuilder.class.getName(), filter)[0]);
			builder.initializeReadQueue(getReadQueue());
			executor = (AbstractHttpClientProtocolExecutor) context.getService(context.getServiceReferences
					(AbstractHttpClientProtocolExecutor.class.getName(), filter)[0]);
			publisher = new Publisher();
			publisher.setDevice(device);
			client = new DefaultHttpClient();
		} catch (InvalidSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
