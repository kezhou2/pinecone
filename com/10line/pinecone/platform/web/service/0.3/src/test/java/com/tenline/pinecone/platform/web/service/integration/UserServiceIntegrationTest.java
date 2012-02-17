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
		user = new User();
		user.setName("bill");
		user.setAvatar("http://avatar/1".getBytes());
		user.setEmail("billmse@gmail.com");
		user.setPassword("19821027");
		user.setNut(20);
		userAPI = new UserAPI("localhost", "8888", "service");
	}
	
	@After
	public void testShutdown() {
		user = null;
		userAPI = null;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = userAPI.create(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("bill", user.getName());
			assertEquals("http://avatar/1", new String(user.getAvatar()));
			assertEquals("19821027", user.getPassword());
			assertEquals("billmse@gmail.com", user.getEmail());
			assertEquals(Integer.valueOf(20), user.getNut());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		user.setName("jack");
		user.setAvatar("http://avatar/2".getBytes());
		user.setEmail("jack@gmail.com");
		user.setPassword("666666");
		user.setNut(30);
		response = userAPI.update(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("jack", user.getName());
			assertEquals("http://avatar/2", new String(user.getAvatar()));
			assertEquals("666666", user.getPassword());
			assertEquals("jack@gmail.com", user.getEmail());
			assertEquals(Integer.valueOf(30), user.getNut());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = userAPI.show("id=='"+user.getId()+"'");
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
		response = userAPI.show("id=='"+user.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<User>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
