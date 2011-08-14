/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor;

import java.util.Hashtable;

import org.osgi.framework.BundleContext;

/**
 * @author Bill
 *
 */
public interface IEndpoint {

	/**
	 * Close Endpoint
	 */
	void close();
	
	/**
	 * Initialize Endpoint
	 * @param bundleContext
	 * @param params
	 */
	void initialize(BundleContext bundleContext, Hashtable<String, String> params);

}
