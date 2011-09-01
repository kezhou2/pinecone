/**
 * 
 */
package com.tenline.pinecone.platform.monitor.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.ServiceHelper;

/**
 * @author Bill
 *
 */
public class MinaProtocolCodecFactory implements ProtocolCodecFactory {
	
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Protocol Decoder
	 */
	private ProtocolDecoder decoder;
	
	/**
	 * Protocol Encoder
	 */
	private ProtocolEncoder encoder;
	
	/**
	 * Protocol Builder
	 */
	private AbstractProtocolBuilder builder;

	/**
	 * 
	 */
	public MinaProtocolCodecFactory() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param bundle
	 */
	public void initialize(Bundle bundle) {
		builder = (AbstractProtocolBuilder) ServiceHelper.waitForService
			(AbstractProtocolBuilder.class, bundle.getSymbolicName(), bundle.getVersion().toString());
		encoder = (ProtocolEncoder) ServiceHelper.waitForService
			(AbstractMinaProtocolEncoder.class, bundle.getSymbolicName(), bundle.getVersion().toString());
		decoder = (ProtocolDecoder) ServiceHelper.waitForService
			(AbstractMinaProtocolDecoder.class, bundle.getSymbolicName(), bundle.getVersion().toString());
		logger.info("Initialize Factory");
	}
	
	/**
	 * 
	 */
	public void close() {
		decoder = null;
		encoder = null;
		builder = null;
		logger.info("Close Factory");
	}
	
	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return encoder;
	}
	
	/**
	 * @return the builder
	 */
	public AbstractProtocolBuilder getBuilder() {
		return builder;
	}

}
