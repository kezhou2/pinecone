/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.net.MalformedURLException;
import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.obr.RepositoryAdmin;
import org.osgi.service.obr.Resolver;

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
	 * Repository Admin
	 */
	private static RepositoryAdmin repositoryAdmin;
	
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
		try {
			bundleContext = context;
			String repositoryAdminClass = RepositoryAdmin.class.getName();
			ServiceReference repositoryAdminreference = context.getServiceReference(repositoryAdminClass);
			repositoryAdmin = (RepositoryAdmin) context.getService(repositoryAdminreference);
			repositoryAdmin.addRepository(new URL(IConstants.REPOSITORY_URL));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * @param version
	 * @return
	 */
	public static Bundle getBundle(String symbolicName, String version) {
		for (int i = 0; i < bundleContext.getBundles().length; i++) {
			Bundle bundle = bundleContext.getBundles()[i];
			if (bundle.getSymbolicName().equals(symbolicName)) {
				return bundle;
			}
		}
		Resolver resolver = repositoryAdmin.resolver();
		String filter = "(&(symbolicname="+symbolicName+")(version="+version+"))";
		resolver.add(repositoryAdmin.discoverResources(filter)[0]);
		resolver.deploy(true);
		return getBundle(symbolicName, version);
	}

}
