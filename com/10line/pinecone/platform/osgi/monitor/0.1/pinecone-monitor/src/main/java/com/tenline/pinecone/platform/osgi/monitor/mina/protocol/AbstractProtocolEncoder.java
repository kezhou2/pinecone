package com.tenline.pinecone.platform.osgi.monitor.mina.protocol;

import org.apache.mina.filter.codec.ProtocolEncoderAdapter;

import com.tenline.pinecone.platform.osgi.monitor.model.Command;


public abstract class AbstractProtocolEncoder extends ProtocolEncoderAdapter {
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
	 * Build Packet Data
	 * 
	 * @param command
	 * @return
	 */
	public abstract byte[] buildPacketData(Command command);

}
