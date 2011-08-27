/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.AbstractScheduler;

/**
 * @author Bill
 *
 */
public class HttpScheduler extends AbstractScheduler {
	
	/**
	 * Http Protocol Encoder
	 */
	private AbstractHttpProtocolEncoder encoder;

	/**
	 * 
	 * @param builder
	 */
	public HttpScheduler(AbstractProtocolBuilder builder) {
		super(builder);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void dispatch(Device device) {
		encoder.buildPacket(device);
		super.dispatch(device);
	}

	/**
	 * @param encoder the encoder to set
	 */
	public void setEncoder(AbstractHttpProtocolEncoder encoder) {
		this.encoder = encoder;
	}

	/**
	 * @return the encoder
	 */
	public AbstractHttpProtocolEncoder getEncoder() {
		return encoder;
	}

}
