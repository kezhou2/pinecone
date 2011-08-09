package com.tenline.pinecone.platform.osgi.monitor.factory;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.tenline.pinecone.platform.osgi.monitor.mina.protocol.AbstractProtocolDecoder;
import com.tenline.pinecone.platform.osgi.monitor.mina.protocol.AbstractProtocolEncoder;
import com.tenline.pinecone.platform.osgi.monitor.model.Device;

public class PineConeProtocolFactory implements ProtocolCodecFactory {

	private AbstractProtocolDecoder decoder;
	private AbstractProtocolEncoder encoder;

	public PineConeProtocolFactory(Device device) {
		this.decoder = device.getDecoder();
		this.encoder = device.getEncoder();
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

}
