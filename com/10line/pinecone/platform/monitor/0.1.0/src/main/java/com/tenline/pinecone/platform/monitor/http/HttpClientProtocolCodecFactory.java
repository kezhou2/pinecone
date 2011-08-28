/**
 * 
 */
package com.tenline.pinecone.platform.monitor.http;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.monitor.AbstractProtocolCodecFactory;

/**
 * @author Bill
 *
 */
public class HttpClientProtocolCodecFactory extends
		AbstractProtocolCodecFactory {
	
	/**
	 * Protocol Decoder
	 */
	private AbstractHttpClientProtocolDecoder decoder;
	
	/**
	 * Protocol Encoder
	 */
	private AbstractHttpClientProtocolEncoder encoder;

	/**
	 * 
	 */
	public HttpClientProtocolCodecFactory() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initialize(Bundle bundle) {
		try {
			Class<?> decoderClass = Class.forName(getPackageName(bundle) + "ProtocolDecoder");
			Constructor<?> decoderConstructor = decoderClass.getDeclaredConstructor(Bundle.class);
			decoder = (AbstractHttpClientProtocolDecoder) decoderConstructor.newInstance(bundle);
			Class<?> encoderClass = Class.forName(getPackageName(bundle) + "ProtocolEncoder");
			Constructor<?> encoderConstructor = encoderClass.getDeclaredConstructor(Bundle.class);
			encoder = (AbstractHttpClientProtocolEncoder) encoderConstructor.newInstance(bundle);
			encoder.setDecoder(decoder);
			super.initialize(bundle);
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
	
	@Override
	public void close() {
		decoder = null;
		encoder = null;
		super.close();
	}

	/**
	 * @return the decoder
	 */
	public AbstractHttpClientProtocolDecoder getDecoder() {
		return decoder;
	}

	/**
	 * @return the encoder
	 */
	public AbstractHttpClientProtocolEncoder getEncoder() {
		return encoder;
	}

}
