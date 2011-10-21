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
		application.setName("fishshow");
		application.setIconUrl("http://icon/1");
		application.setTargetUrl("http://fishshow");
		application.setSymbolicName("com.10line.life.pet.fishshow");
		application.setVersion("1.1");
		userAPI = new UserAPI("localhost", "8080");
		applicationAPI = new ApplicationAPI("localhost", "8080");
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
			assertEquals("fishshow", application.getName());
			assertEquals("http://icon/1", application.getIconUrl());
			assertEquals("http://fishshow", application.getTargetUrl());
			assertEquals("com.10line.life.pet.fishshow", application.getSymbolicName());
			assertEquals("1.1", application.getVersion());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		application.setName("fishshow2");
		application.setIconUrl("http://icon/2");
		application.setTargetUrl("http://fishshow/2");
		application.setSymbolicName("com.10line.life.pet.fishshow.2");
		application.setVersion("2.1");
		response = applicationAPI.update(application);
		if (response.isDone()) {
			application = (Application) response.getMessage();
			assertEquals("fishshow2", application.getName());
			assertEquals("http://icon/2", application.getIconUrl());
			assertEquals("http://fishshow/2", application.getTargetUrl());
			assertEquals("com.10line.life.pet.fishshow.2", application.getSymbolicName());
			assertEquals("2.1", application.getVersion());
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
