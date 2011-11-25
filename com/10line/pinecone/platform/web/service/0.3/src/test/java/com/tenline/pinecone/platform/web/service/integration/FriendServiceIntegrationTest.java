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
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.FriendAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class FriendServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private User user;
	
	private User person;
	
	private Friend friend;
	
	private UserAPI userAPI;
	
	private FriendAPI friendAPI;
	
	@Before
	public void testSetup() throws Exception {
		user = new User();
		user.setName("bill");
		person = new User();
		person.setName("jack");
		friend = new Friend();
		userAPI = new UserAPI("localhost", "8080", "service");
		friendAPI = new FriendAPI("localhost", "8080", "service");
		APIResponse response = userAPI.create(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("bill", user.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = userAPI.create(person);
		if (response.isDone()) {
			person = (User) response.getMessage();
			assertEquals("jack", person.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		friend.setPerson(person);
		friend.setUser(user);
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
		user = null;
		person = null;
		friend = null;
		userAPI = null;
		friendAPI = null;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = friendAPI.create(friend);
		if (response.isDone()) {
			friend = (Friend) response.getMessage();
			assertEquals("jack", friend.getPerson().getName());
			assertEquals("bill", friend.getUser().getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = friendAPI.show("id=='"+friend.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<Friend>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = friendAPI.delete(friend.getId());
		if (response.isDone()) {
			assertEquals("Friend Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = friendAPI.showByUser("id=='"+user.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Friend>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
