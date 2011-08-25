/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.obr.RepositoryAdmin;
import org.osgi.service.obr.Resolver;
import org.osgi.service.obr.Resource;

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
public class ProtocolAdministrator {
	
	/**
	 * Singleton
	 */
	private static ProtocolAdministrator instance;
	
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(ProtocolAdministrator.class);
	
	/**
	 * Repository Admin
	 */
	private RepositoryAdmin admin;
	
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
	public ProtocolAdministrator(BundleContext context) {
		// TODO Auto-generated constructor stub
		try {
			String serviceClass = RepositoryAdmin.class.getName();
			ServiceReference reference = context.getServiceReference(serviceClass);
			admin = (RepositoryAdmin) context.getService(reference);
			admin.addRepository(new URL(IConstants.REPOSITORY_URL));
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
	 * Get Instance
	 * @return
	 */
	public static ProtocolAdministrator getInstance(BundleContext context) {
		if (instance == null) {
			instance = new ProtocolAdministrator(context);
		}
		return instance;
	}
	
	/**
	 * Get Protocols from Remote Repository
	 * @return
	 */
	public Device[] getProtocols() {
		Resource[] resources = admin.discoverResources(null);
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
	 * Install Protocol to Local OSGI Framework
	 * @param metaData
	 * @return
	 */
	public boolean installProtocol(Device metaData) {
		Resolver resolver = admin.resolver();
		resolver.add(admin.discoverResources("(symbolicname="+metaData.getSymbolicName()+")")[0]);
		if (resolver.resolve()) {
			resolver.deploy(true);
			return true;
		} else {
			logger.error("Unable to resolve: " + metaData.getSymbolicName());
			return false;
		}
	}
	
	/**
	 * Merge Protocol to Remote DB
	 * @param user
	 * @param metaData
	 */
	public void mergeProtocol(User user, Device metaData) {
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
