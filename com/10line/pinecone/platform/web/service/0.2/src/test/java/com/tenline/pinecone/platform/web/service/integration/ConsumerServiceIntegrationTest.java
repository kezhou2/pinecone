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
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class ConsumerServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private Consumer consumer;
	
	private ConsumerAPI consumerAPI;
	
	@Before
	public void testSetup() {
		super.testSetup();
		consumer = new Consumer();
		consumer.setConnectURI("123");
		consumer.setDisplayName("fishshow");
		consumerAPI = new ConsumerAPI("localhost", "8080");
	}
	
	@After
	public void testShutdown() {
		consumer = null;
		consumerAPI = null;
		super.testShutdown();
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = consumerAPI.create(consumer);
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
		response = consumerAPI.show("id=='"+consumer.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(0, ((Collection<Consumer>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
