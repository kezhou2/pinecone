/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.ModelAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class APIHelper {
	
	/**
	 * Logger
	 */
	private static Logger logger = Logger.getLogger(APIHelper.class);
	
	/**
	 * Web Service API
	 */
	private static User user;
	private static ModelAPI modelAPI;
	
	/**
	 * Singleton
	 */
	private static APIHelper instance;
	
	/**
	 * 
	 */
	public APIHelper() {
		// TODO Auto-generated constructor stub
		modelAPI = new ModelAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, IConstants.WEB_SERVICE_CONTEXT);
	}
	
	/**
	 * Get Instance
	 * @return
	 */
	public static APIHelper getInstance() {
		if (instance == null) {
			instance = new APIHelper();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static User login(String email, String password) throws Exception {
		APIResponse response = modelAPI.show(User.class, "email=='"+email+"'&&password=='"+password+"'");
		if (response.isDone()) {
			user = (User) ((Collection<User>) response.getMessage()).toArray()[0]; 
			return user;
		} else return null;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Collection<Device> showDevices() throws Exception {
		APIResponse response = modelAPI.show(Device.class, "user.id=='"+user.getId()+"'");
		if (response.isDone()) return (Collection<Device>) response.getMessage();
		else return null;
	}
	
	/**
	 * 
	 * @param symbolicName
	 * @param version
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Device createDevice(String symbolicName, String version) throws Exception {
		Bundle bundle = BundleHelper.getBundle(symbolicName, version);
		Device metaData = ((AbstractProtocolBuilder) ServiceHelper.waitForService
				(AbstractProtocolBuilder.class, bundle.getSymbolicName(), 
				bundle.getVersion().toString())).getMetaData();
		Category category = metaData.getDriver().getCategory();
		APIResponse response = modelAPI.show(Category.class, "type=='"+category.getType()+"" +
				"'&&name=='"+category.getName()+"'&&domain=='"+category.getDomain()+
				"'&&subdomain=='"+category.getSubdomain()+"'");
		if (response.isDone()) {
			Collection<Category> categories = (Collection<Category>) response.getMessage();
			if (categories.size() == 1) metaData.getDriver().setCategory((Category) categories.toArray()[0]);
			else if (categories.size() == 0) {
				response = modelAPI.create(category);
				if (response.isDone()) metaData.getDriver().setCategory((Category) response.getMessage());
				else logger.error(response.getMessage());
			}
		} else logger.error(response.getMessage());
		Driver driver = metaData.getDriver();
		response = modelAPI.show(Driver.class, "alias=='"+driver.getAlias()+"'");
		if (response.isDone()) {
			Collection<Driver> drivers = (Collection<Driver>) response.getMessage();
			if (drivers.size() == 1) {
				driver = (Driver) drivers.toArray()[0];
				if (Version.parseVersion(version).compareTo(Version.parseVersion(driver.getVersion())) > 0) {
					driver.setVersion(version);
					response = modelAPI.update(driver);
					metaData.setDriver((Driver) response.getMessage());
				} else metaData.setDriver(driver);
			} else if (drivers.size() == 0) {
				response = modelAPI.create(driver);
				if (response.isDone()) metaData.setDriver((Driver) response.getMessage());
				else logger.error(response.getMessage());
			}
		} else logger.error(response.getMessage());
		metaData.setUser(user);
		response = modelAPI.create(metaData);
		if (response.isDone()) return (Device) response.getMessage();
		else return null;
	}

}
