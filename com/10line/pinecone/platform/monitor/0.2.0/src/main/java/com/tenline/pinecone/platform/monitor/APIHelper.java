/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;

import com.google.api.client.auth.oauth.OAuthCallbackUrl;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.CategoryAPI;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.DriverAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.sdk.oauth.AuthorizationAPI;

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
	private static AuthorizationAPI authorizationAPI;
	private static User user;
	private static UserAPI userAPI;
	private static DeviceAPI deviceAPI;
	private static CategoryAPI categoryAPI;
	private static DriverAPI driverAPI;
	
	/**
	 * OAuth Token and Secret
	 */
	private static String token;
	private static String tokenSecret;
	private static String verifier;
	
	/**
	 * Singleton
	 */
	private static APIHelper instance;
	
	/**
	 * 
	 */
	public APIHelper() {
		// TODO Auto-generated constructor stub
		authorizationAPI = new AuthorizationAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, IConstants.WEB_SERVICE_CONTEXT);
		userAPI = new UserAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, IConstants.WEB_SERVICE_CONTEXT);
		deviceAPI = new DeviceAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, IConstants.WEB_SERVICE_CONTEXT);
		categoryAPI = new CategoryAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, IConstants.WEB_SERVICE_CONTEXT);
		driverAPI = new DriverAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, IConstants.WEB_SERVICE_CONTEXT);
		APIResponse response = authorizationAPI.requestToken(IConstants.OAUTH_CONSUMER_KEY, IConstants.OAUTH_CONSUMER_SECRET, null);
		if (response.isDone()) {
			token = ((OAuthCredentialsResponse) response.getMessage()).token;
			tokenSecret = ((OAuthCredentialsResponse) response.getMessage()).tokenSecret;
		} else {
			logger.error(response.getMessage());
		}
		response = authorizationAPI.confirmAuthorization(token, "yes");
		if (response.isDone()) {
			token = ((OAuthCallbackUrl) response.getMessage()).token;
			verifier = ((OAuthCallbackUrl) response.getMessage()).verifier;
		} else {
			logger.error(response.getMessage());
		}
		response = authorizationAPI.accessToken(IConstants.OAUTH_CONSUMER_KEY, IConstants.OAUTH_CONSUMER_SECRET, 
				token, tokenSecret, verifier);
		if (response.isDone()) {
			token = ((OAuthCredentialsResponse) response.getMessage()).token;
			tokenSecret = ((OAuthCredentialsResponse) response.getMessage()).tokenSecret;
		} else {
			logger.error(response.getMessage());
		}
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
		APIResponse response = userAPI.show("email=='"+email+"'&&password=='"+password+"'");
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
		APIResponse response = deviceAPI.showByUser("id=='"+user.getId()+"'", 
				IConstants.OAUTH_CONSUMER_KEY, IConstants.OAUTH_CONSUMER_SECRET, 
				token, tokenSecret);
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
		APIResponse response = categoryAPI.show("type=='"+category.getType()+"" +
				"'&&name=='"+category.getName()+"'&&domain=='"+category.getDomain()+
				"'&&subdomain=='"+category.getSubdomain()+"'");
		if (response.isDone()) {
			Collection<Category> categories = (Collection<Category>) response.getMessage();
			if (categories.size() == 1) metaData.getDriver().setCategory((Category) categories.toArray()[0]);
			else if (categories.size() == 0) {
				response = categoryAPI.create(category);
				if (response.isDone()) 
					metaData.getDriver().setCategory((Category) ((Collection<Category>) response.getMessage()).toArray()[0]);
				else logger.error(response.getMessage());
			}
		} else logger.error(response.getMessage());
		Driver driver = metaData.getDriver();
		response = driverAPI.show("alias=='"+driver.getAlias()+"'&&version=='"+driver.getVersion()+"'");
		if (response.isDone()) {
			Collection<Driver> drivers = (Collection<Driver>) response.getMessage();
			if (drivers.size() == 1) metaData.setDriver((Driver) drivers.toArray()[0]);
			else if (drivers.size() == 0) {
				response = driverAPI.create(driver);
				if (response.isDone())
					metaData.setDriver((Driver) ((Collection<Driver>) response.getMessage()).toArray()[0]);
				else logger.error(response.getMessage());
			}
		} else logger.error(response.getMessage());
		metaData.setUser(user);
		response = deviceAPI.create(metaData);
		if (response.isDone()) return (Device) ((Collection<Device>) response.getMessage()).toArray()[0];
		else return null;
	}

	/**
	 * @param token the token to set
	 */
	public static void setToken(String token) {
		APIHelper.token = token;
	}

	/**
	 * @return the token
	 */
	public static String getToken() {
		return token;
	}

	/**
	 * @param tokenSecret the tokenSecret to set
	 */
	public static void setTokenSecret(String tokenSecret) {
		APIHelper.tokenSecret = tokenSecret;
	}

	/**
	 * @return the tokenSecret
	 */
	public static String getTokenSecret() {
		return tokenSecret;
	}

}
