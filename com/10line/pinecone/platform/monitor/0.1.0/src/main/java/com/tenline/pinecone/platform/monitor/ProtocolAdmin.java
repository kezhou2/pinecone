/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.obr.RepositoryAdmin;
import org.osgi.service.obr.Resolver;
import org.osgi.service.obr.Resource;
import org.osgi.service.packageadmin.PackageAdmin;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.ItemAPI;
import com.tenline.pinecone.platform.sdk.VariableAPI;

/**
 * @author Bill
 *
 */
public class ProtocolAdmin {
	
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Repository Admin
	 */
	private RepositoryAdmin repositoryAdmin;
	
	/**
	 * Package Admin
	 */
	private PackageAdmin packageAdmin;
	
	/**
	 * Web Service API
	 */
	private Device device;
	private DeviceAPI deviceAPI;
	
	private Variable variable;
	private VariableAPI variableAPI;
	
	private Item item;
	private ItemAPI itemAPI;
	
	/**
	 * 
	 * @param context
	 */
	public ProtocolAdmin(BundleContext context) {
		// TODO Auto-generated constructor stub
		try {
			String repositoryAdminClass = RepositoryAdmin.class.getName();
			ServiceReference repositoryAdminreference = context.getServiceReference(repositoryAdminClass);
			repositoryAdmin = (RepositoryAdmin) context.getService(repositoryAdminreference);
			repositoryAdmin.addRepository(new URL(IConstants.REPOSITORY_URL));
			String packageAdminClass = PackageAdmin.class.getName();
			ServiceReference packageAdminReference = context.getServiceReference(packageAdminClass);
			packageAdmin = (PackageAdmin) context.getService(packageAdminReference);
			deviceAPI = new DeviceAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

				@Override
				public void onMessage(Object message) {
					// TODO Auto-generated method stub
					device = (Device) message;
					logger.info("Device: " + device.getId());
				}

				@Override
				public void onError(String error) {
					// TODO Auto-generated method stub
					logger.error(error);
				}
				
			});
			variableAPI = new VariableAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

				@Override
				public void onMessage(Object message) {
					// TODO Auto-generated method stub
					variable = (Variable) message;
					logger.info("Variable: " + variable.getId());
				}

				@Override
				public void onError(String error) {
					// TODO Auto-generated method stub
					logger.error(error);
				}
				
			});
			itemAPI = new ItemAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

				@Override
				public void onMessage(Object message) {
					// TODO Auto-generated method stub
					item = (Item) message;
					logger.info("Item: " + item.getId());
				}

				@Override
				public void onError(String error) {
					// TODO Auto-generated method stub
					logger.error(error);
				}
				
			});
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieve Protocols from Remote Repository
	 * @return
	 */
	public Device[] retrieveProtocols() {
		Resource[] resources = repositoryAdmin.discoverResources(null);
		Device[] devices = new Device[resources.length];
		for (int i=0; i<resources.length; i++) {
			Resource resource = resources[i];
			Device device = new Device();
			device.setName(resource.getPresentationName());
			device.setSymbolicName(resource.getSymbolicName());
			devices[i] = device;
		}
		return devices;
	}
	
	/**
	 * Install Protocol to Local Runtime
	 * @param metaData
	 * @return
	 */
	public boolean installProtocol(Device metaData) {
		Resolver resolver = repositoryAdmin.resolver();
		resolver.add(repositoryAdmin.discoverResources("(symbolicname="+metaData.getSymbolicName()+")")[0]);
		if (resolver.resolve()) {
			resolver.deploy(true);
			packageAdmin.refreshPackages(new Bundle[]{BundleHelper.getBundle(metaData.getSymbolicName())});
			return true;
		} else {
			logger.error("Unable to resolve: " + metaData.getSymbolicName());
			return false;
		}
	}
	
	/**
	 * Deploy Protocol to Remote Database
	 * @param user
	 * @param metaData
	 */
	public void deployProtocol(User user, Device metaData) {
		try {
			device = new Device();
			device.setName(metaData.getName());
			device.setSymbolicName(metaData.getSymbolicName());
			device.setVersion(metaData.getVersion());
			device.setUser(user);
			deviceAPI.create(device);
			for (Variable vMetaData : metaData.getVariables()) {
				variable = new Variable();
				variable.setName(vMetaData.getName());
				variable.setType(vMetaData.getType());
				variable.setDevice(device);
				variableAPI.create(variable);
				for (Item iMetaData : vMetaData.getItems()) {
					item = new Item();
					item.setText(iMetaData.getText());
					item.setValue(iMetaData.getValue());
					item.setVariable(variable);
					itemAPI.create(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
