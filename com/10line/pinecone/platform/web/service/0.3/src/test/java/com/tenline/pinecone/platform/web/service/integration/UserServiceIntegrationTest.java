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

import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.model.User;

/**
 * @author Bill
 *
 */
public class UserServiceIntegrationTest extends AbstractServiceIntegrationTest {
	
	private User user;
	
	private UserAPI userAPI;
	
	@Before
	public void testSetup() {
		super.testSetup();
		user = new User();
		user.setName("bill");
		user.setAvatarUrl("http://avatar/1");
		user.setEmail("billmse@gmail.com");
		user.setPassword("19821027");
		user.setType("individual");
		userAPI = new UserAPI("localhost", "8080");
	}
	
	@After
	public void testShutdown() {
		user = null;
		userAPI = null;
		super.testShutdown();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = userAPI.create(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("bill", user.getName());
			assertEquals("individual", user.getType());
			assertEquals("http://avatar/1", user.getAvatarUrl());
			assertEquals("19821027", user.getPassword());
			assertEquals("billmse@gmail.com", user.getEmail());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		user.setName("jack");
		user.setAvatarUrl("http://avatar/2");
		user.setEmail("jack@gmail.com");
		user.setPassword("666666");
		user.setType("enterprise");
		response = userAPI.update(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("jack", user.getName());
			assertEquals("enterprise", user.getType());
			assertEquals("http://avatar/2", user.getAvatarUrl());
			assertEquals("666666", user.getPassword());
			assertEquals("jack@gmail.com", user.getEmail());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = userAPI.show("id=='"+user.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(1, ((Collection<User>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = userAPI.delete(user.getId());
		if (response.isDone()) {
			assertEquals("User Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = userAPI.show("id=='"+user.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(0, ((Collection<User>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
