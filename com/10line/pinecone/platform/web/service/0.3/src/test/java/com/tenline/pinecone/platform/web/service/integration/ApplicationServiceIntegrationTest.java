/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.ApplicationAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class ApplicationServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private User user;
	
	private Application application;
	
	private UserAPI userAPI;
	
	private ApplicationAPI applicationAPI;
	
	@Before
	public void testSetup() {
		super.testSetup();
		user = new User();
		user.setName("bill");
		application = new Application();
		application.setConsumerId("aaa");
		userAPI = new UserAPI("localhost", "8080", "service");
		applicationAPI = new ApplicationAPI("localhost", "8080", "service");
	}
	
	@After
	public void testShutdown() {
		user = null;
		application = null;
		userAPI = null;
		applicationAPI = null;
		super.testShutdown();
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = userAPI.create(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("bill", user.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		application.setUser(user);
		response = applicationAPI.create(application);
		if (response.isDone()) {
			application = (Application) response.getMessage();
			assertEquals("aaa", application.getConsumerId());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		application.setConsumerId("bbb");
		response = applicationAPI.update(application);
		if (response.isDone()) {
			application = (Application) response.getMessage();
			assertEquals("bbb", application.getConsumerId());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = applicationAPI.show("id=='"+application.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(1, ((Collection<Application>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = applicationAPI.delete(application.getId());
		if (response.isDone()) {
			assertEquals("Application Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = applicationAPI.showByUser("id=='"+user.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(0, ((Collection<Application>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
