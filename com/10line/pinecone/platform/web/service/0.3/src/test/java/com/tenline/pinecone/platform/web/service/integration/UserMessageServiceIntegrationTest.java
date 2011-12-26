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
import com.tenline.pinecone.platform.model.UserMessage;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.UserMessageAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class UserMessageServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private User sender;
	
	private User receiver;
	
	private UserMessage message;
	
	private UserAPI userAPI;
	
	private UserMessageAPI messageAPI;
	
	@Before
	public void testSetup() throws Exception {
		sender = new User();
		sender.setName("bill");
		receiver = new User();
		receiver.setName("jack");
		message = new UserMessage();
		message.setRead(false);
		message.setTitle("aa");
		message.setContent("bb");
		userAPI = new UserAPI("localhost", "8888", "service");
		messageAPI = new UserMessageAPI("localhost", "8888", "service");
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
		message.setSender(sender);
		message.setReceiver(receiver);
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
		message = null;
		userAPI = null;
		messageAPI = null;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = messageAPI.create(message);
		if (response.isDone()) {
			message = (UserMessage) response.getMessage();
			assertEquals(false, message.isRead());
			assertEquals("aa", message.getTitle());
			assertEquals("bb", message.getContent());
			assertEquals("jack", message.getReceiver().getName());
			assertEquals("bill", message.getSender().getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		message.setRead(true);
		message.setTitle("cc");
		message.setContent("dd");
		response = messageAPI.update(message);
		if (response.isDone()) {
			message = (UserMessage) response.getMessage();
			assertEquals(true, message.isRead());
			assertEquals("cc", message.getTitle());
			assertEquals("dd", message.getContent());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = messageAPI.show("id=='"+message.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<UserMessage>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = messageAPI.delete(message.getId());
		if (response.isDone()) {
			assertEquals("User Message Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = messageAPI.showByReceiver("id=='"+receiver.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<UserMessage>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = messageAPI.showBySender("id=='"+sender.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<UserMessage>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
