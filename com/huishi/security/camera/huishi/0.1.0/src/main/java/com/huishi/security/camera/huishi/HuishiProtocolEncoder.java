/**
 * 
 */
package com.huishi.security.camera.huishi;

import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.monitor.http.AbstractHttpProtocolEncoder;

/**
 * @author Bill
 *
 */
public class HuishiProtocolEncoder extends AbstractHttpProtocolEncoder {

	/**
	 * @param bundle
	 */
	public HuishiProtocolEncoder(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] buildPacket(Device device) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] buildPacketType(Variable variable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] buildPacketData(Variable variable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte buildPacketCheck(byte[] bytes) {
		// TODO Auto-generated method stub
		return 0;
	}

}
