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

import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class ConsumerServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private User user;
	
	private Consumer consumer;
	
	private UserAPI userAPI;
	
	private ConsumerAPI consumerAPI;
	
	@Before
	public void testSetup() {
		super.testSetup();
		user = new User();
		user.setName("bill");
		consumer = new Consumer();
		consumer.setConnectURI("123");
		consumer.setDisplayName("fishshow");
		userAPI = new UserAPI("localhost", "8080", "service");
		consumerAPI = new ConsumerAPI("localhost", "8080", "service");
	}
	
	@After
	public void testShutdown() {
		user = null;
		consumer = null;
		userAPI = null;
		consumerAPI = null;
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
		consumer.setUser(user);
		response = consumerAPI.create(consumer);
		if (response.isDone()) {
			consumer = (Consumer) response.getMessage();
			assertEquals("fishshow", consumer.getDisplayName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		consumer.setDisplayName("fishshow2");
		response = consumerAPI.update(consumer);
		if (response.isDone()) {
			consumer = (Consumer) response.getMessage();
			assertEquals("fishshow2", consumer.getDisplayName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = consumerAPI.show("id=='"+consumer.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(1, ((Collection<Consumer>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = consumerAPI.delete(consumer.getId());
		if (response.isDone()) {
			assertEquals("Consumer Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = consumerAPI.showByUser("id=='"+user.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(0, ((Collection<Consumer>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
