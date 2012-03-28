/**
 * 
 */
package com.tenline.pinecone.platform.monitor.httpcomponents;

import java.net.URI;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.reactor.IOReactorException;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.AbstractScheduler;
import com.tenline.pinecone.platform.monitor.ServiceHelper;

/**
 * @author Bill
 *
 */
public abstract class AbstractProtocolRequester extends AbstractScheduler {
	
	/**
	 * Protocol Bundle
	 */
	protected Bundle bundle;
	
	/**
	 * Protocol Responser
	 */
	private AbstractProtocolResponser responser;
	
	/**
	 * HTTP Async Client
	 */
	private HttpAsyncClient client;
	
	/**
	 * HTTP GET Method
	 */
	private HttpGet get;
	
	/**
	 * 
	 * @param bundle
	 */
	public AbstractProtocolRequester(Bundle bundle) {
		// TODO Auto-generated constructor stub
		this.bundle = bundle;
	}
	
	/**
	 * Start Requester
	 * @param device
	 */
	public void start(Device device) {
		try {
			get = new HttpGet();
			client = new DefaultHttpAsyncClient();
			client.start();
			((AbstractProtocolBuilder) ServiceHelper.waitForService
					(AbstractProtocolBuilder.class, device.getSymbolicName(), device.getVersion()))
					.initializeReadQueue(getReadQueue());
			responser = (AbstractProtocolResponser) ServiceHelper.waitForService
				(AbstractProtocolResponser.class, bundle.getSymbolicName(), bundle.getVersion().toString());
			responser.start(this, device);
			super.start();
			logger.info("Start Requester");
		} catch (IOReactorException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Stop Requester
	 */
	@Override
	public void stop() {
		try {
			get.abort();
			client.shutdown();
			responser.stop();
			super.stop();
			logger.info("Stop Requester");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Send Request
	 * @param uri
	 */
	protected void sendRequest(String uri) {
		try {
			get.setURI(new URI(uri));
			client.execute(get, responser);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			logger.error(ex.getMessage());
		}
	}

}
