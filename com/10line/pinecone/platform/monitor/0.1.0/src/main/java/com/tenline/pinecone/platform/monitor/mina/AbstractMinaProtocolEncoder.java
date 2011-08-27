/**
 * 
 */
package com.tenline.pinecone.platform.monitor.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.monitor.IProtocolEncoder;

/**
 * @author Bill
 *
 */
public abstract class AbstractMinaProtocolEncoder extends ProtocolEncoderAdapter implements IProtocolEncoder {

	/**
	 * Protocol Bundle
	 */
	protected Bundle bundle;
	
	/**
	 * Protocol Logger
	 */
	protected Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 
	 * @param bundle
	 */
	public AbstractMinaProtocolEncoder(Bundle bundle) {
		// TODO Auto-generated constructor stub
		this.bundle = bundle;
	}
	
	/**
	 * Transmit Packet
	 * @param packet
	 * @param output
	 */
	protected void transmitPacket(byte[] packet, ProtocolEncoderOutput output) {
		IoBuffer buffer = IoBuffer.allocate(1).setAutoExpand(true);
		buffer.put(packet);
		buffer.flip();
		output.write(buffer);
	}
	
}
