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

import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.UserAPI;
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
		user.setAvatarUrl("http://avatar/1");
		user.setEmail("billmse@gmail.com");
		user.setPassword("19821027");
		user.setType("individual");
	}
	
	@After
	public void testShutdown() {
		user = null;
		userAPI = null;
	}

	@Test
	public void testCRUD() throws Exception {
		userAPI = new UserAPI("localhost", "8080", new APIListener() {

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				user = (User) message;
				assertEquals("bill", user.getName());
				assertEquals("individual", user.getType());
				assertEquals("http://avatar/1", user.getAvatarUrl());
				assertEquals("19821027", user.getPassword());
				assertEquals("billmse@gmail.com", user.getEmail());
			}
			
		});
		userAPI.create(user);
		userAPI = new UserAPI("localhost", "8080", new APIListener() {

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				user = (User) message;
				assertEquals("jack", user.getName());
				assertEquals("enterprise", user.getType());
				assertEquals("http://avatar/2", user.getAvatarUrl());
				assertEquals("666666", user.getPassword());
				assertEquals("jack@gmail.com", user.getEmail());
			}
			
		});
		user.setName("jack");
		user.setAvatarUrl("http://avatar/2");
		user.setEmail("jack@gmail.com");
		user.setPassword("666666");
		user.setType("enterprise");
		userAPI.update(user);
		userAPI = new UserAPI("localhost", "8080", new APIListener() {

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(1, ((Collection<User>) message).size());
			}
			
		});
		userAPI.show("id=='"+user.getId()+"'");
		userAPI = new UserAPI("localhost", "8080", new APIListener() {

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals("User Deleted!", message.toString());
			}
			
		});
		userAPI.delete(user.getId());
		userAPI = new UserAPI("localhost", "8080", new APIListener() {

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(0, ((Collection<User>) message).size());
			}
			
		});
		userAPI.show("id=='"+user.getId()+"'");
	}

}
