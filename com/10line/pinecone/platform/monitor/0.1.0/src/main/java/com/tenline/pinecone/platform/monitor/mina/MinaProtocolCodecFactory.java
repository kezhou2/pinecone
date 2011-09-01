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
import org.osgi.framework.ServiceReference;

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
	
	/**
	 * 
	 * @param bundle
	 */
	public void initialize(Bundle bundle) {
		String filter = "(&(symbolicName="+bundle.getSymbolicName()+")(version="+bundle.getVersion().toString()+"))";
		waitForServices(filter, bundle.getBundleContext());
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
	
	/**
	 * 
	 * @param filter
	 * @param context
	 * @return
	 * @throws InvalidSyntaxException
	 */
	private ProtocolDecoder getDecoderService(String filter, BundleContext context) throws InvalidSyntaxException {
		ServiceReference[] references = context.getServiceReferences(AbstractMinaProtocolDecoder.class.getName(), filter);
		if(references != null) decoder = (ProtocolDecoder) context.getService(references[0]);
		return decoder;
	}
	
	/**
	 * 
	 * @param filter
	 * @param context
	 * @return
	 * @throws InvalidSyntaxException
	 */
	private ProtocolEncoder getEncoderService(String filter, BundleContext context) throws InvalidSyntaxException {
		ServiceReference[] references = context.getServiceReferences(AbstractMinaProtocolEncoder.class.getName(), filter);
		if(references != null) encoder = (ProtocolEncoder) context.getService(references[0]);
		return encoder;
	}
	
	/**
	 * 
	 * @param filter
	 * @param context
	 * @return
	 * @throws InvalidSyntaxException
	 */
	private AbstractProtocolBuilder getBuilderService(String filter, BundleContext context) throws InvalidSyntaxException {
		ServiceReference[] references = context.getServiceReferences(AbstractProtocolBuilder.class.getName(), filter);
		if(references != null) builder = (AbstractProtocolBuilder) context.getService(references[0]);
		return builder;
	}
	
	/**
	 * 
	 * @param filter
	 * @param context
	 */
	private void waitForServices(String filter, BundleContext context) {
		int secondsToWait = 10;
		int secondsPassed = 0;
		try {
			while (secondsPassed < secondsToWait && 
				   getDecoderService(filter, context) == null &&
				   getEncoderService(filter, context) == null && 
				   getBuilderService(filter, context) == null) {
				Thread.sleep(++secondsPassed * 1000);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
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
