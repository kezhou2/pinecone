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

	private User sender;
	
	private User receiver;
	
	private Friend friend;
	
	private UserAPI userAPI;
	
	private FriendAPI friendAPI;
	
	@Before
	public void testSetup() throws Exception {
		sender = new User();
		sender.setName("bill");
		receiver = new User();
		receiver.setName("jack");
		friend = new Friend();
		friend.setDecided(false);
		friend.setType(Friend.CLASSMATE);
		userAPI = new UserAPI("localhost", "8888", "service");
		friendAPI = new FriendAPI("localhost", "8888", "service");
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
		friend.setSender(sender);
		friend.setReceiver(receiver);
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
			assertEquals("jack", friend.getReceiver().getName());
			assertEquals("bill", friend.getSender().getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		friend.setDecided(true);
		friend.setType(Friend.COLLEAGUE);
		response = friendAPI.update(friend);
		if (response.isDone()) {
			friend = (Friend) response.getMessage();
			assertEquals(true, friend.isDecided());
			assertEquals(Friend.COLLEAGUE, friend.getType());
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
		response = friendAPI.showByReceiver("id=='"+receiver.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Friend>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = friendAPI.showBySender("id=='"+sender.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Friend>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
