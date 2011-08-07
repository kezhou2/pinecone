/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor;

import org.osgi.framework.BundleContext;

/**
 * @author xuel
 *
 */
public class ProtocolLocalization {

	/**
	 * OSGi Bundle Context
	 */
	private BundleContext bundleContext;
	
	/**
	 * 
	 */
	public ProtocolLocalization() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get Variable
	 * @param name
	 * @return
	 */
	public String getVariable(String name) {
		return bundleContext.getBundle().getHeaders().get(name).toString();
	}

	/**
	 * @param bundleContext the bundleContext to set
	 */
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	/**
	 * @return the bundleContext
	 */
	public BundleContext getBundleContext() {
		return bundleContext;
	}

}
