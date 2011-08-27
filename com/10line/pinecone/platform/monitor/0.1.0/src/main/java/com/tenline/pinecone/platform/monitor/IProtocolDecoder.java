/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

/**
 * @author Bill
 *
 */
public interface IProtocolDecoder {
	
	/**
	 * Check Packet
	 * @param packet
	 * @return
	 */
	byte[] checkPacket(byte[] packet);
	
	/**
	 * Split Packet
	 * @param packet
	 * @param output
	 */
	void splitPacket(byte[] packet, Object output);
	
	/**
	 * Split Packet Type
	 * @param packet
	 * @param output
	 */
	void splitPacketType(byte[] packet, Object output);
	
	/**
	 * Split Packet Data
	 * @param packet
	 * @param output
	 */
	void splitPacketData(byte[] packet, Object output);

	/**
	 * Split Packet Rejected Description
	 * @param rejectedCode
	 * @return
	 */
	String splitPacketRejectedDescription(byte[] rejectedCode);
	
}
