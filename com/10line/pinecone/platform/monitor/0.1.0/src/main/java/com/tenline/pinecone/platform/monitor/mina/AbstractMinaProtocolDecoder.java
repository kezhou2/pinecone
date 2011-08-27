/**
 * 
 */
package com.tenline.pinecone.platform.monitor.mina;

import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.osgi.framework.Bundle;

/**
 * @author Bill
 *
 */
public abstract class AbstractMinaProtocolDecoder extends CumulativeProtocolDecoder {
	
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
	public AbstractMinaProtocolDecoder(Bundle bundle) {
		// TODO Auto-generated constructor stub
		this.bundle = bundle;
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
