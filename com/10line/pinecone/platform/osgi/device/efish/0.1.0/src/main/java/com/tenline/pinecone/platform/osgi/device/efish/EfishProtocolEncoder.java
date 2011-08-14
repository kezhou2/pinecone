/**
 * 
 */
package com.tenline.pinecone.platform.osgi.device.efish;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.osgi.monitor.mina.AbstractMinaProtocolEncoder;

/**
 * @author Bill
 *
 */
public class EfishProtocolEncoder extends AbstractMinaProtocolEncoder {

	/**
	 * 
	 */
	public EfishProtocolEncoder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void encode(IoSession arg0, Object arg1, ProtocolEncoderOutput arg2)
			throws Exception {
		// TODO Auto-generated method stub
		transmitPacket(buildPacket((Device) arg1), arg2);
	}

	@Override
	protected byte[] buildPacket(Device device) {
		// TODO Auto-generated method stub
		System.out.println(device.getVariables().size());
		return "OK".getBytes();
	}

	@Override
	protected byte[] buildPacketType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected byte[] buildPacketData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected byte buildPacketCheck(byte[] bytes) {
		// TODO Auto-generated method stub
		return 0;
	}

}
