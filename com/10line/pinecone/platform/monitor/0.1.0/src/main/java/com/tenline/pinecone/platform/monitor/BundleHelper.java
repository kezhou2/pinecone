/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Bill
 *
 */
public class BundleHelper {
	
	/**
	 * OSGI Bundle Context
	 */
	private static BundleContext bundleContext;
	
	/**
	 * Singleton
	 */
	private static BundleHelper instance;

	/**
	 * 
	 * @param context
	 */
	public BundleHelper(BundleContext context) {
		// TODO Auto-generated constructor stub
		bundleContext = context;
	}
	
	/**
	 * Get Instance
	 * @param context
	 * @return
	 */
	public static BundleHelper getInstance(BundleContext context) {
		if (instance == null) {
			instance = new BundleHelper(context);
		}
		return instance;
	}
	
	/**
	 * 
	 * @param symbolicName
	 * @return
	 */
	public static Bundle getBundle(String symbolicName) {
		for (int i = 0; i < bundleContext.getBundles().length; i++) {
			Bundle bundle = bundleContext.getBundles()[i];
			if (bundle.getSymbolicName().equals(symbolicName)) {
				return bundle;
			}
		}
		return null;
	}

}
