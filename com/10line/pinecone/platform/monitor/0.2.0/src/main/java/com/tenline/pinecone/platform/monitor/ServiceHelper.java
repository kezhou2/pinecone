/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * @author Bill
 *
 */
public class ServiceHelper {
	
	/**
	 * OSGI Bundle Context
	 */
	private static BundleContext bundleContext;
	
	/**
	 * Singleton
	 */
	private static ServiceHelper instance;
	
	/**
	 * 
	 * @param context
	 */
	public ServiceHelper(BundleContext context) {
		// TODO Auto-generated constructor stub
		bundleContext = context;
	}
	
	/**
	 * Get Instance
	 * @param context
	 * @return
	 */
	public static ServiceHelper getInstance(BundleContext context) {
		if (instance == null) {
			instance = new ServiceHelper(context);
		}
		return instance;
	}
	
	/**
	 * 
	 * @param cls
	 * @param symbolicName
	 * @param version
	 * @return
	 */
	public static Object waitForService(Class<?> cls, String symbolicName, String version) {
		int secondsToWait = 10;
		int secondsPassed = 0;
		Object object = null;
		while (secondsPassed < secondsToWait && (object = getService(cls, symbolicName, version)) == null) {
			try {
				Thread.sleep(++secondsPassed * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return object;
	}
	
	/**
	 * 
	 * @param cls
	 * @param symbolicName
	 * @param version
	 * @return
	 */
	private static Object getService(Class<?> cls, String symbolicName, String version) {
		Object object = null;
		try {
			String filter = "(&(symbolicName=" + symbolicName + ")(version=" + version + "))";
			ServiceReference[] references = bundleContext.getServiceReferences(cls.getName(), filter);
			if(references != null) object = bundleContext.getService(references[0]);
		} catch (InvalidSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

}
