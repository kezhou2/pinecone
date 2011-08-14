/**
 * 
 */
package com.tenline.pinecone.platform.osgi.device.efish;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.osgi.framework.BundleContext;

import com.tenline.pinecone.platform.osgi.monitor.mina.AbstractMinaProtocolDecoder;

/**
 * @author Bill
 *
 */
public class EfishProtocolDecoder extends AbstractMinaProtocolDecoder {

	/**
	 * 
	 * @param bundleContext
	 */
	public EfishProtocolDecoder(BundleContext bundleContext) {
		super(bundleContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected byte[] checkPacket(byte[] packet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void splitPacket(byte[] packet, ProtocolDecoderOutput output) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void splitPacketType(byte[] packet, ProtocolDecoderOutput output) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void splitPacketData(byte[] packet, ProtocolDecoderOutput output) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String splitPacketRejectedDescription(byte[] rejectedCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean doDecode(IoSession arg0, IoBuffer arg1,
			ProtocolDecoderOutput arg2) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
