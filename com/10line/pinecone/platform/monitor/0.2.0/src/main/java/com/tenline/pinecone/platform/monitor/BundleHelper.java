/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.obr.RepositoryAdmin;
import org.osgi.service.obr.Resolver;
import org.osgi.service.obr.Resource;

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Driver;

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
	 * @param device
	 * @return
	 */
	public static Bundle getBundle(Device device) {
		String symbolicName = device.getDriver().getCategory().getType() +"."+ 
			device.getDriver().getCategory().getName() +"."+ 
			device.getDriver().getCategory().getDomain() +"."+ 
			device.getDriver().getCategory().getSubdomain() +"."+
			device.getDriver().getAlias();
		String version = device.getDriver().getVersion();
		for (int i = 0; i < bundleContext.getBundles().length; i++) {
			Bundle bundle = bundleContext.getBundles()[i];
			if (bundle.getSymbolicName().equals(symbolicName) &&
				bundle.getVersion().toString().equals(version)) {
				return bundle;
			}
		}
		Resolver resolver = repositoryAdmin.resolver();
		String filter = "(&(symbolicname="+symbolicName+")(version="+version+"))";
		resolver.add(repositoryAdmin.discoverResources(filter)[0]);
		resolver.deploy(true);
		return getBundle(device);
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
			if (bundle.getSymbolicName().equals(symbolicName) &&
				bundle.getVersion().toString().equals(version)) {
				return bundle;
			}
		}
		Resolver resolver = repositoryAdmin.resolver();
		String filter = "(&(symbolicname="+symbolicName+")(version="+version+"))";
		resolver.add(repositoryAdmin.discoverResources(filter)[0]);
		resolver.deploy(true);
		return getBundle(symbolicName, version);
	}
	
	/**
	 * 
	 * @param device
	 * @return
	 */
	public static String getBundleLatestVersion(Device device) {
		String symbolicName = device.getDriver().getCategory().getType() +"."+ 
			device.getDriver().getCategory().getName() +"."+ 
			device.getDriver().getCategory().getDomain() +"."+ 
			device.getDriver().getCategory().getSubdomain() +"."+
			device.getDriver().getAlias();
		return filterBundle(repositoryAdmin.discoverResources("(symbolicname="+symbolicName+")"))
		.get(symbolicName).getVersion().toString();
	}
	
	/**
	 * 
	 * @return
	 */
    public static ArrayList<Device> getRemoteBundles() {
            Hashtable<String, Resource> result = filterBundle(repositoryAdmin.discoverResources(null));
            ArrayList<Device> devices = new ArrayList<Device>();
            for (Enumeration<String> keys = result.keys();keys.hasMoreElements();) {
            	String symbolicName = keys.nextElement();
            	String[] temp = symbolicName.split(".");
                Category category = new Category();
                category.setType(temp[0]);
                category.setName(temp[1]);
                category.setDomain(temp[2]);
                category.setSubdomain(temp[3]);
                Driver driver = new Driver();
                driver.setAlias(temp[4]);
                driver.setName(result.get(symbolicName).getPresentationName());
                driver.setVersion(result.get(symbolicName).getVersion().toString());
                driver.setCategory(category);
                Device device = new Device();  
                device.setDriver(driver);
                devices.add(device);
            }
            return devices;
    }
    
    /**
     * 
     * @param resources
     * @return
     */
    private static Hashtable<String, Resource> filterBundle(Resource[] resources) {
    	Hashtable<String, Resource> result = new Hashtable<String, Resource>();
    	for (Resource resource : resources) {
    		if (!result.containsKey(resource.getSymbolicName())) {
    			result.put(resource.getSymbolicName(), resource);
    		} else {
    			if (resource.getVersion().compareTo(result.get(resource.getSymbolicName())) > 0) {
    				result.put(resource.getSymbolicName(), resource);
    			}
    		}
    	}
    	return result;
    }

}
