/**
 * 
 */
package com.tenline.pinecone.platform.monitor.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.monitor.Activator;

/**
 * @author Bill
 *
 */
public abstract class AbstractMinaProtocolEncoder extends ProtocolEncoderAdapter {

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
	 * @param device
	 */
	public AbstractMinaProtocolEncoder(Device device) {
		// TODO Auto-generated constructor stub
		bundle = Activator.getBundle(device.getSymbolicName());
	}
	
	/**
	 * Build Packet
	 * @param device
	 * @return
	 */
	protected abstract byte[] buildPacket(Device device);

	/**
	 * Build Packet Type
	 * @param variable
	 * @return
	 */
	protected abstract byte[] buildPacketType(Variable variable);

	/**
	 * Build Packet Data
	 * @param variable
	 * @return
	 */
	protected abstract byte[] buildPacketData(Variable variable); 

	/**
	 * Build Packet Check
	 * @param bytes
	 * @return
	 */
	protected abstract byte buildPacketCheck(byte[] bytes);
	
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
