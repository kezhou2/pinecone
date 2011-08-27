/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Variable;

/**
 * @author Bill
 *
 */
public interface IProtocolEncoder {

	/**
	 * Build Packet
	 * @param device
	 * @return
	 */
	byte[] buildPacket(Device device);

	/**
	 * Build Packet Type
	 * @param variable
	 * @return
	 */
	byte[] buildPacketType(Variable variable);

	/**
	 * Build Packet Data
	 * @param variable
	 * @return
	 */
	byte[] buildPacketData(Variable variable); 

	/**
	 * Build Packet Check
	 * @param bytes
	 * @return
	 */
	byte buildPacketCheck(byte[] bytes);
	
}
