/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;

/**
 * @author Bill
 *
 */
public abstract class AbstractProtocolCodecFactory {
	
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Protocol Builder
	 */
	private AbstractProtocolBuilder builder;

	/**
	 * 
	 */
	public AbstractProtocolCodecFactory() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Initialize Factory
	 * @param bundle
	 */
	public void initialize(Bundle bundle) {
		try {
			Class<?> builderClass = Class.forName(getPackageName(bundle) + "ProtocolBuilder");
			Constructor<?> builderConstructor = builderClass.getDeclaredConstructor(Bundle.class);
			builder = (AbstractProtocolBuilder) builderConstructor.newInstance(bundle);
			logger.info("Initialize Factory");
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
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
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
		builder = null;
		logger.info("Close Factory");
	}
	
	/**
	 * @return the builder
	 */
	public AbstractProtocolBuilder getBuilder() {
		return builder;
	}
	
	/**
	 * 
	 * @param bundle
	 * @return
	 */
	protected String getPackageName(Bundle bundle) {
		String symbolicName = bundle.getSymbolicName();
		String tempName = symbolicName.substring(symbolicName.lastIndexOf(".") + 1);
		String name = String.valueOf(tempName.charAt(0)).toUpperCase() + tempName.substring(1);
		return symbolicName + "." + name;
	}
	
}
