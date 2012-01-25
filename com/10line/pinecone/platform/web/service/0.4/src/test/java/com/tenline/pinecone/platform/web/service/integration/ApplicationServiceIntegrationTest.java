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
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.CategoryAPI;
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.ApplicationAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class ApplicationServiceIntegrationTest extends AbstractServiceIntegrationTest {
	
	private Category category;

	private Consumer consumer;
	
	private User user;
	
	private Application application;
	
	private CategoryAPI categoryAPI;
	
	private ConsumerAPI consumerAPI;
	
	private UserAPI userAPI;
	
	private ApplicationAPI applicationAPI;
	
	@Before
	public void testSetup() throws Exception {
		user = new User();
		user.setName("bill");
		consumer = new Consumer();
		consumer.setName("App");
		category = new Category();
		category.setType(Category.COM);
		application = new Application();
		application.setDefault(false);
		userAPI = new UserAPI("localhost", "8888", "service");
		categoryAPI = new CategoryAPI("localhost", "8888", "service");
		consumerAPI = new ConsumerAPI("localhost", "8888", "service");
		applicationAPI = new ApplicationAPI("localhost", "8888", "service");
		APIResponse response = userAPI.create(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("bill", user.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = categoryAPI.create(category);
		if (response.isDone()) {
			category = (Category) response.getMessage();
			assertEquals(Category.COM, category.getType());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		consumer.setCategory(category);
		response = consumerAPI.create(consumer);
		if (response.isDone()) {
			consumer = (Consumer) response.getMessage();
			assertEquals("App", consumer.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		application.setUser(user);
		application.setConsumer(consumer);
	}
	
	@After
	public void testShutdown() throws Exception {
		APIResponse response = userAPI.delete(user.getId());
		if (response.isDone()) {
			assertEquals("User Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = categoryAPI.delete(category.getId());
		if (response.isDone()) {
			assertEquals("Category Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		user = null;
		category = null;
		consumer = null;
		application = null;
		userAPI = null;
		categoryAPI = null;
		consumerAPI = null;
		applicationAPI = null;
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = applicationAPI.create(application);
		if (response.isDone()) {
			application = (Application) response.getMessage();
			assertEquals(false, application.isDefault());
			assertEquals("bill", application.getUser().getName());
			assertEquals("App", application.getConsumer().getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		application.setDefault(true);
		response = applicationAPI.update(application);
		if (response.isDone()) {
			application = (Application) response.getMessage();
			assertEquals(true, application.isDefault());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = applicationAPI.show("id=='"+application.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<Application>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = applicationAPI.delete(application.getId());
		if (response.isDone()) {
			assertEquals("Application Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = applicationAPI.showByUser("id=='"+user.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Application>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = applicationAPI.showByConsumer("id=='"+consumer.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Application>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
