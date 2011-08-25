/**
 * 
 */
package com.tenline.pinecone.platform.monitor.mina;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;

/**
 * @author Bill
 *
 */
public class MinaProtocolCodecFactory implements ProtocolCodecFactory {
	
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
	 * Initialize Factory
	 * @param bundle
	 */
	public void initialize(Bundle bundle) {
		try {
			String symbolicName = bundle.getSymbolicName();
			String tempName = symbolicName.substring(symbolicName.lastIndexOf(".") + 1);
			String name = String.valueOf(tempName.charAt(0)).toUpperCase() + tempName.substring(1);
			String packageName = symbolicName + "." + name;
			Class<?> decoderClass = Class.forName(packageName + "ProtocolDecoder");
			Constructor<?> decoderConstructor = decoderClass.getDeclaredConstructor(Bundle.class);
			decoder = (ProtocolDecoder) decoderConstructor.newInstance(bundle);
			Class<?> encoderClass = Class.forName(packageName + "ProtocolEncoder");
			Constructor<?> encoderConstructor = encoderClass.getDeclaredConstructor(Bundle.class);
			encoder = (ProtocolEncoder) encoderConstructor.newInstance(bundle);
			Class<?> builderClass = Class.forName(packageName + "ProtocolBuilder");
			Constructor<?> builderConstructor = builderClass.getDeclaredConstructor(Bundle.class);
			builder = (AbstractProtocolBuilder) builderConstructor.newInstance(bundle);
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
	
	/**
	 * Close Factory
	 */
	public void close() {
		decoder = null;
		encoder = null;
		builder = null;
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
