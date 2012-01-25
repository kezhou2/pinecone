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

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.sdk.CategoryAPI;
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class ConsumerServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private Category category;
	
	private Consumer consumer;
	
	private CategoryAPI categoryAPI;
	
	private ConsumerAPI consumerAPI;
	
	@Before
	public void testSetup() throws Exception {
		category = new Category();
		category.setType(Category.COM);
		consumer = new Consumer();
		consumer.setConnectURI("123");
		consumer.setName("fishshow");
		consumer.setIcon("123".getBytes());
		consumer.setCategory(category);
		categoryAPI = new CategoryAPI("localhost", "8888", "service");
		consumerAPI = new ConsumerAPI("localhost", "8888", "service");
		APIResponse response = categoryAPI.create(category);
		if (response.isDone()) {
			category = (Category) response.getMessage();
			assertEquals(Category.COM, category.getType());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		consumer.setCategory(category);
	}
	
	@After
	public void testShutdown() throws Exception {
		APIResponse response = categoryAPI.delete(category.getId());
		if (response.isDone()) {
			assertEquals("Category Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		category = null;
		consumer = null;
		categoryAPI = null;
		consumerAPI = null;
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = consumerAPI.create(consumer);
		if (response.isDone()) {
			consumer = (Consumer) response.getMessage();
			assertEquals("123", consumer.getConnectURI());
			assertEquals("fishshow", consumer.getName());
			assertEquals("123", new String(consumer.getIcon()));
			assertEquals(Category.COM, consumer.getCategory().getType());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		consumer.setConnectURI("234");
		consumer.setName("fishshow2");
		consumer.setIcon("234".getBytes());
		response = consumerAPI.update(consumer);
		if (response.isDone()) {
			consumer = (Consumer) response.getMessage();
			assertEquals("234", consumer.getConnectURI());
			assertEquals("fishshow2", consumer.getName());
			assertEquals("234", new String(consumer.getIcon()));
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = consumerAPI.show("id=='"+consumer.getId()+"'");
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
		response = consumerAPI.showByCategory("id=='"+category.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Consumer>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
