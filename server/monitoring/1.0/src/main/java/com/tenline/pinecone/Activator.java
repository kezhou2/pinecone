/**
 * 
 */
package com.tenline.pinecone;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Bill
 *
 */
public class Activator implements BundleActivator {

	/**
	 * 
	 */
	public Activator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Bundle - " + context.getBundle().getSymbolicName() + " - Started!");
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Bundle - " + context.getBundle().getSymbolicName() + " - Stopped!");
	}

}
