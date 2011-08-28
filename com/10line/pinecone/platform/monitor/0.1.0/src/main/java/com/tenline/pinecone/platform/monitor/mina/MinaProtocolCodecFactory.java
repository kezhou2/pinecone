/**
 * 
 */
package com.tenline.pinecone.platform.monitor.mina;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.BundleHelper;

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
			String packageName = BundleHelper.getPackageName(bundle.getSymbolicName());
			Class<?> decoderClass = Class.forName(packageName + "ProtocolDecoder");
			Constructor<?> decoderConstructor = decoderClass.getDeclaredConstructor(Bundle.class);
			decoder = (ProtocolDecoder) decoderConstructor.newInstance(bundle);
			Class<?> encoderClass = Class.forName(packageName + "ProtocolEncoder");
			Constructor<?> encoderConstructor = encoderClass.getDeclaredConstructor(Bundle.class);
			encoder = (ProtocolEncoder) encoderConstructor.newInstance(bundle);
			Class<?> builderClass = Class.forName(packageName + "ProtocolBuilder");
			Constructor<?> builderConstructor = builderClass.getDeclaredConstructor(Bundle.class);
			builder = (AbstractProtocolBuilder) builderConstructor.newInstance(bundle);
			logger.info("Initialize Factory");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
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
