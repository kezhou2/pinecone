/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;

/**
 * @author Bill
 *
 */
public abstract class AbstractMinaHandler extends IoHandlerAdapter implements EventHandler {
	
	/**
	 * Event Administration
	 */
	protected EventAdmin admin;
	
	/**
	 * 
	 * @param bundleContext
	 */
	public AbstractMinaHandler(BundleContext bundleContext) {
		// TODO Auto-generated constructor stub
		admin = (EventAdmin) bundleContext.getService(bundleContext.getServiceReference(EventAdmin.class.getName()));
	}

}
