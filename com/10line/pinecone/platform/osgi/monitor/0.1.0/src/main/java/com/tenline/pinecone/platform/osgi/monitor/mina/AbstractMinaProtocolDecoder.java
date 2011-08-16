/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.mina;

import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @author Bill
 *
 */
public abstract class AbstractMinaProtocolDecoder extends CumulativeProtocolDecoder {
	
	/**
	 * 
	 */
	public AbstractMinaProtocolDecoder() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Check Packet
	 * @param packet
	 * @return
	 */
	protected abstract byte[] checkPacket(byte[] packet);
	
	/**
	 * Split Packet
	 * @param packet
	 * @param output
	 */
	protected abstract void splitPacket(byte[] packet, ProtocolDecoderOutput output);
	
	/**
	 * Split Packet Type
	 * @param packet
	 * @param output
	 */
	protected abstract void splitPacketType(byte[] packet, ProtocolDecoderOutput output);
	
	/**
	 * Split Packet Data
	 * @param packet
	 * @param output
	 */
	protected abstract void splitPacketData(byte[] packet, ProtocolDecoderOutput output);

	/**
	 * Split Packet Rejected Description
	 * @param rejectedCode
	 * @return
	 */
	protected abstract String splitPacketRejectedDescription(byte[] rejectedCode);
	
}
