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
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.UserRelationAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

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
		super.testSetup();
		user = new User();
		user.setName("bill");
		owner = new User();
		owner.setName("jack");
		userRelation = new UserRelation();
		userRelation.setType("classmate");
		userAPI = new UserAPI("localhost", "8080");
		userRelationAPI = new UserRelationAPI("localhost", "8080");
	}
	
	@After
	public void testShutdown() {
		user = null;
		owner = null;
		userRelation = null;
		userAPI = null;
		userRelationAPI = null;
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
		response = userAPI.create(owner);
		if (response.isDone()) {
			owner = (User) response.getMessage();
			assertEquals("jack", owner.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		userRelation.setOwner(owner);
		userRelation.setUserId(user.getId());
		response = userRelationAPI.create(userRelation);
		if (response.isDone()) {
			userRelation = (UserRelation) response.getMessage();
			assertEquals("classmate", userRelation.getType());
			assertEquals(user.getId(), userRelation.getUserId());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		user.setName("jone");
		response = userAPI.create(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("jone", user.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		userRelation.setType("mother");
		userRelation.setUserId(user.getId());
		response = userRelationAPI.update(userRelation);
		if (response.isDone()) {
			userRelation = (UserRelation) response.getMessage();
			assertEquals("mother", userRelation.getType());
			assertEquals(user.getId(), userRelation.getUserId());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = userRelationAPI.show("id=='"+userRelation.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(1, ((Collection<UserRelation>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = userRelationAPI.delete(userRelation.getId());
		if (response.isDone()) {
			assertEquals("User Relation Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = userRelationAPI.showByUser("id=='"+owner.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(0, ((Collection<UserRelation>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
