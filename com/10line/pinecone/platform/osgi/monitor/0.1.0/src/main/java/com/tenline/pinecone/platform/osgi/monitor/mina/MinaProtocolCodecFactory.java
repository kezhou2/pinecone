/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.mina;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.osgi.framework.BundleContext;

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
	 * 
	 * @param bundleContext
	 * @param packageName
	 */
	public MinaProtocolCodecFactory(BundleContext bundleContext, String packageName) {
		// TODO Auto-generated constructor stub
		try {
			Class<?> decoderClass = Class.forName(packageName + "ProtocolDecoder");
			Constructor<?> decoderConstructor = decoderClass.getDeclaredConstructor(BundleContext.class);
			decoder = (ProtocolDecoder) decoderConstructor.newInstance(bundleContext);
			encoder = (ProtocolEncoder) Class.forName(packageName + "ProtocolEncoder").newInstance();
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
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
