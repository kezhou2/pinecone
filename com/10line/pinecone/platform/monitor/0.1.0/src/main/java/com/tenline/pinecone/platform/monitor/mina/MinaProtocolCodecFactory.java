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
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;

import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;

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
	
	public void initialize(Bundle bundle) {
		try {
			BundleContext context = bundle.getBundleContext();
			String filter = "(&(symbolicName="+bundle.getSymbolicName()+")(version="+bundle.getVersion().toString()+"))";
			decoder = (ProtocolDecoder) context.getService(context.getServiceReferences
					(AbstractMinaProtocolDecoder.class.getName(), filter)[0]);
			encoder = (ProtocolEncoder) context.getService(context.getServiceReferences
					(AbstractMinaProtocolEncoder.class.getName(), filter)[0]);
			builder = (AbstractProtocolBuilder) context.getService(context.getServiceReferences
					(AbstractProtocolBuilder.class.getName(), filter)[0]);
		} catch (InvalidSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
