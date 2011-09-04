/**
 * 
 */
package com.tenline.pinecone.platform.monitor.mina;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.asyncweb.client.AsyncHttpClient;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.monitor.AbstractScheduler;

/**
 * @author Bill
 *
 */
public abstract class AbstractMinaProtocolRequester extends AbstractScheduler {
	
	/**
	 * Protocol Bundle
	 */
	protected Bundle bundle;
	
	/**
	 * Protocol Responser
	 */
	private AbstractMinaProtocolResponser responser;
	
	/**
	 * MINA Async HTTP Client
	 */
	private AsyncHttpClient client;
	
	/**
	 * 
	 * @param bundle
	 */
	public AbstractMinaProtocolRequester(Bundle bundle) {
		// TODO Auto-generated constructor stub
		this.bundle = bundle;
	}
	
	/**
	 * 
	 * @param path
	 */
	protected void request(String path) {
		try {
			String uri = "http://" + bundle.getHeaders().get("Address").toString() +
			 			 ":" + bundle.getHeaders().get("Port").toString() + path;
			client.connect(new URI(uri), responser);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			logger.error(ex.getMessage());
		}
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(AsyncHttpClient client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public AsyncHttpClient getClient() {
		return client;
	}

	/**
	 * @param responser the responser to set
	 */
	public void setResponser(AbstractMinaProtocolResponser responser) {
		this.responser = responser;
	}

	/**
	 * @return the responser
	 */
	public AbstractMinaProtocolResponser getResponser() {
		return responser;
	}

}
