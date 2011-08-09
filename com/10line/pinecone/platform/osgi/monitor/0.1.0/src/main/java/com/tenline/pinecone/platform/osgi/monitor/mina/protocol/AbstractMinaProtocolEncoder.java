/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.mina.protocol;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.tenline.pinecone.platform.osgi.monitor.model.Command;
import com.tenline.pinecone.platform.osgi.monitor.model.Device;

/**
 * @author xuel
 *
 */
public abstract class AbstractMinaProtocolEncoder extends ProtocolEncoderAdapter {

	/**
	 * 
	 */
	public AbstractMinaProtocolEncoder() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Build Packet
	 * @param device
	 * @return
	 */
	protected abstract byte[] buildPacket(Device device);

	/**
	 * Build Packet Type
	 * @param command
	 * @return
	 */
	protected abstract byte[] buildPacketType(Command command);

	/**
	 * Build Packet Data
	 * @param command
	 * @return
	 */
	protected abstract byte[] buildPacketData(Command command); 

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
