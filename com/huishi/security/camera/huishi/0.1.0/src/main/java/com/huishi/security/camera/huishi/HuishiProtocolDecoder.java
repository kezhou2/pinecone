/**
 * 
 */
package com.huishi.security.camera.huishi;

import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.monitor.http.AbstractHttpProtocolDecoder;

/**
 * @author Bill
 *
 */
public class HuishiProtocolDecoder extends AbstractHttpProtocolDecoder {

	/**
	 * @param bundle
	 */
	public HuishiProtocolDecoder(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] checkPacket(byte[] packet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void splitPacket(byte[] packet, Object output) {
		// TODO Auto-generated method stub

	}

	@Override
	public void splitPacketType(byte[] packet, Object output) {
		// TODO Auto-generated method stub

	}

	@Override
	public void splitPacketData(byte[] packet, Object output) {
		// TODO Auto-generated method stub

	}

	@Override
	public String splitPacketRejectedDescription(byte[] rejectedCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
