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

	private User sender;
	
	private User receiver;
	
	private UserRelation relation;
	
	private UserAPI userAPI;
	
	private UserRelationAPI relationAPI;
	
	@Before
	public void testSetup() throws Exception {
		sender = new User();
		sender.setName("bill");
		receiver = new User();
		receiver.setName("jack");
		relation = new UserRelation();
		relation.setDecided(false);
		relation.setType(UserRelation.CLASSMATE);
		userAPI = new UserAPI("localhost", "8888", "service");
		relationAPI = new UserRelationAPI("localhost", "8888", "service");
		APIResponse response = userAPI.create(sender);
		if (response.isDone()) {
			sender = (User) response.getMessage();
			assertEquals("bill", sender.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = userAPI.create(receiver);
		if (response.isDone()) {
			receiver = (User) response.getMessage();
			assertEquals("jack", receiver.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		relation.setSender(sender);
		relation.setReceiver(receiver);
	}
	
	@After
	@SuppressWarnings("unchecked")
	public void testShutdown() throws Exception {
		APIResponse response = userAPI.show("all");
		if (response.isDone()) {
			Collection<User> users = (Collection<User>) response.getMessage();
			for (User user : users) {
				response = userAPI.delete(user.getId());
				if (response.isDone()) {
					assertEquals("User Deleted!", response.getMessage().toString());
				} else {
					logger.log(Level.SEVERE, response.getMessage().toString());
				}
			}
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		sender = null;
		receiver = null;
		relation = null;
		userAPI = null;
		relationAPI = null;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = relationAPI.create(relation);
		if (response.isDone()) {
			relation = (UserRelation) response.getMessage();
			assertEquals("jack", relation.getReceiver().getName());
			assertEquals("bill", relation.getSender().getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		relation.setDecided(true);
		relation.setType(UserRelation.COLLEAGUE);
		response = relationAPI.update(relation);
		if (response.isDone()) {
			relation = (UserRelation) response.getMessage();
			assertEquals(true, relation.isDecided());
			assertEquals(UserRelation.COLLEAGUE, relation.getType());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = relationAPI.show("id=='"+relation.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<UserRelation>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = relationAPI.delete(relation.getId());
		if (response.isDone()) {
			assertEquals("User Relation Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = relationAPI.showByReceiver("id=='"+receiver.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<UserRelation>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = relationAPI.showBySender("id=='"+sender.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<UserRelation>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
