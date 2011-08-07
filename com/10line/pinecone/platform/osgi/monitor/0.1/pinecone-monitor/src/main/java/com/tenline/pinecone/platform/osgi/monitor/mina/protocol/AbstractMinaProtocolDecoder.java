package com.tenline.pinecone.platform.osgi.monitor.mina.protocol;


import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.osgi.framework.BundleContext;

/**
 * @author xuel
 * 
 */
public abstract class AbstractMinaProtocolDecoder extends
		CumulativeProtocolDecoder {

	/**
	 * 
	 * @param bundleContext
	 * @param localizationType
	 */
	public AbstractMinaProtocolDecoder(BundleContext bundleContext,
			String localizationType) {
	}

	/**
	 * Check Packet
	 * 
	 * @param packet
	 * @return
	 */
	protected abstract byte[] checkPacket(byte[] packet);

	/**
	 * Split Packet
	 * 
	 * @param packet
	 * @param output
	 */
	protected abstract void splitPacket(byte[] packet,
			ProtocolDecoderOutput output);

	/**
	 * Split Packet Type
	 * 
	 * @param packet
	 * @param output
	 */
	protected abstract void splitPacketType(byte[] packet,
			ProtocolDecoderOutput output);

	/**
	 * Split Packet Data
	 * 
	 * @param packet
	 * @param output
	 */
	protected abstract void splitPacketData(byte[] packet,
			ProtocolDecoderOutput output);

	/**
	 * Split Packet Rejected Description
	 * 
	 * @param rejectedCode
	 * @return
	 */
	protected abstract String splitPacketRejectedDescription(byte[] rejectedCode);

}
