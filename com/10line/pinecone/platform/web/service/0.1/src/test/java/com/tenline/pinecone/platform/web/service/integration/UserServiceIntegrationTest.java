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
		user.setSnsId("251417324");
	}
	
	@After
	public void testShutdown() {
		user = null;
		userAPI = null;
	}

	@Test
	public void testCRUD() throws Exception {
		userAPI = new UserAPI("localhost", "8888", new APIListener() {

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				user = (User) message;
				assertEquals("251417324", user.getSnsId());
			}
			
		});
		userAPI.create(user);
		userAPI = new UserAPI("localhost", "8888", new APIListener() {

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				user = (User) message;
				assertEquals("251417333", user.getSnsId());
			}
			
		});
		user.setSnsId("251417333");
		userAPI.update(user);
		userAPI = new UserAPI("localhost", "8888", new APIListener() {

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
		userAPI.show("snsId=='"+user.getSnsId()+"'");
		userAPI = new UserAPI("localhost", "8888", new APIListener() {

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
		userAPI = new UserAPI("localhost", "8888", new APIListener() {

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
