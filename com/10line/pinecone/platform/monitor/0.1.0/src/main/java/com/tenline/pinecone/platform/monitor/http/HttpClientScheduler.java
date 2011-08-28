/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractScheduler;

/**
 * @author Bill
 *
 */
public class HttpClientScheduler extends AbstractScheduler {
	
	/**
	 * Protocol Encoder
	 */
	private AbstractHttpClientProtocolEncoder encoder;

	/**
	 * 
	 */
	public HttpClientScheduler() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void dispatch(Device device) {
		encoder.encode(device);
		super.dispatch(device);
	}

	/**
	 * @param encoder the encoder to set
	 */
	public void setEncoder(AbstractHttpClientProtocolEncoder encoder) {
		this.encoder = encoder;
	}

	/**
	 * @return the encoder
	 */
	public AbstractHttpClientProtocolEncoder getEncoder() {
		return encoder;
	}

}
