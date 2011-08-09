package com.tenline.pinecone.platform.osgi.monitor.mina.protocol;

import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @author wong
 *
 */
public abstract class AbstractProtocolDecoder extends ProtocolDecoderAdapter {
	/**
	 * device id
	 */
	protected String deviceID;
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	/**
	 * Split Packet Data
	 * 
	 * @param packet
	 * @param output
	 */
	protected abstract void splitPacket(byte[] packet,
			ProtocolDecoderOutput output);

}
