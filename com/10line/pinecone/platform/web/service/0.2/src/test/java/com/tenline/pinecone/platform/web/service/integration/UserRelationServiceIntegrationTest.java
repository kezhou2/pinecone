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

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.UserRelation;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.UserRelationAPI;

/**
 * @author Bill
 *
 */
public class UserRelationServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private User user;
	
	private User owner;
	
	private UserRelation userRelation;
	
	private UserAPI userAPI;
	
	private UserRelationAPI userRelationAPI;
	
	@Before
	public void testSetup() {
		user = new User();
		user.setName("bill");
		owner = new User();
		owner.setName("jack");
		userRelation = new UserRelation();
		userRelation.setType("classmate");
	}
	
	@After
	public void testShutdown() {
		user = null;
		owner = null;
		userRelation = null;
		userAPI = null;
		userRelationAPI = null;
	}

	@Test
	public void testCRUD() throws Exception {
		userAPI = new UserAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				user = (User) message;
				assertEquals("bill", user.getName());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		userAPI.create(user);
		userAPI = new UserAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				owner = (User) message;
				assertEquals("jack", owner.getName());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		userAPI.create(owner);
		userRelationAPI = new UserRelationAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				userRelation = (UserRelation) message;
				assertEquals("classmate", userRelation.getType());
				assertEquals(user.getId(), userRelation.getUserId());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		userRelation.setOwner(owner);
		userRelation.setUserId(user.getId());
		userRelationAPI.create(userRelation);
		userAPI = new UserAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				user = (User) message;
				assertEquals("jone", user.getName());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		user.setName("jone");
		userAPI.create(user);
		userRelationAPI = new UserRelationAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				userRelation = (UserRelation) message;
				assertEquals("mother", userRelation.getType());
				assertEquals(user.getId(), userRelation.getUserId());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		userRelation.setType("mother");
		userRelation.setUserId(user.getId());
		userRelationAPI.update(userRelation);
		userRelationAPI = new UserRelationAPI("localhost", "8080", new APIListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(1, ((Collection<UserRelation>) message).size());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		userRelationAPI.show("id=='"+userRelation.getId()+"'");
		userRelationAPI = new UserRelationAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals("User Relation Deleted!", message.toString());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		userRelationAPI.delete(userRelation.getId());
		userRelationAPI = new UserRelationAPI("localhost", "8080", new APIListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(0, ((Collection<UserRelation>) message).size());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		userRelationAPI.showByUser("id=='"+owner.getId()+"'");
	}

}
