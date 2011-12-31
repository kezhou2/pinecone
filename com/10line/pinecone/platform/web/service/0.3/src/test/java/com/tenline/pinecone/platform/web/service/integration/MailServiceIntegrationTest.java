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
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.MailAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class MailServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private User sender;
	
	private User receiver;
	
	private Mail mail;
	
	private UserAPI userAPI;
	
	private MailAPI mailAPI;
	
	@Before
	public void testSetup() throws Exception {
		sender = new User();
		sender.setName("bill");
		receiver = new User();
		receiver.setName("jack");
		mail = new Mail();
		mail.setRead(false);
		mail.setTitle("aa");
		mail.setContent("bb");
		userAPI = new UserAPI("localhost", "8888", "service");
		mailAPI = new MailAPI("localhost", "8888", "service");
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
		mail.setSender(sender);
		mail.setReceiver(receiver);
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
		mail = null;
		userAPI = null;
		mailAPI = null;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = mailAPI.create(mail);
		if (response.isDone()) {
			mail = (Mail) response.getMessage();
			assertEquals(false, mail.isRead());
			assertEquals("aa", mail.getTitle());
			assertEquals("bb", mail.getContent());
			assertEquals("jack", mail.getReceiver().getName());
			assertEquals("bill", mail.getSender().getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		mail.setRead(true);
		mail.setTitle("cc");
		mail.setContent("dd");
		response = mailAPI.update(mail);
		if (response.isDone()) {
			mail = (Mail) response.getMessage();
			assertEquals(true, mail.isRead());
			assertEquals("cc", mail.getTitle());
			assertEquals("dd", mail.getContent());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = mailAPI.show("id=='"+mail.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<Mail>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = mailAPI.delete(mail.getId());
		if (response.isDone()) {
			assertEquals("Mail Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = mailAPI.showByReceiver("id=='"+receiver.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Mail>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = mailAPI.showBySender("id=='"+sender.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Mail>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
