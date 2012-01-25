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
import com.tenline.pinecone.platform.model.Dependency;
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.sdk.CategoryAPI;
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.DriverAPI;
import com.tenline.pinecone.platform.sdk.DependencyAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class DependencyServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private Driver driver;
	
	private Category category;
	
	private Consumer consumer;
	
	private Dependency dependency;
	
	private DriverAPI driverAPI;
	
	private CategoryAPI categoryAPI;
	
	private ConsumerAPI consumerAPI;
	
	private DependencyAPI dependencyAPI;
	
	@Before
	public void testSetup() throws Exception {
		category = new Category();
		category.setType(Category.COM);
		driver = new Driver();
		driver.setName("LNB");
		consumer = new Consumer();
		consumer.setName("AA");
		dependency = new Dependency();
		dependency.setOptional(false);
		driverAPI = new DriverAPI("localhost", "8888", "service");
		categoryAPI = new CategoryAPI("localhost", "8888", "service");
		consumerAPI = new ConsumerAPI("localhost", "8888", "service");
		dependencyAPI = new DependencyAPI("localhost", "8888", "service");
		APIResponse response = categoryAPI.create(category);
		if (response.isDone()) {
			category = (Category) response.getMessage();
			assertEquals(Category.COM, category.getType());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		driver.setCategory(category);
		response = driverAPI.create(driver);
		if (response.isDone()) {
			driver = (Driver) response.getMessage();
			assertEquals("LNB", driver.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		consumer.setCategory(category);
		response = consumerAPI.create(consumer);
		if (response.isDone()) {
			consumer = (Consumer) response.getMessage();
			assertEquals("AA", consumer.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		dependency.setConsumer(consumer);
		dependency.setDriver(driver);
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
		driver = null;
		consumer = null;
		dependency = null;
		categoryAPI = null;
		driverAPI = null;
		consumerAPI = null;
		dependencyAPI = null;
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = dependencyAPI.create(dependency);
		if (response.isDone()) {
			dependency = (Dependency) response.getMessage();
			assertEquals(false, dependency.isOptional());
			assertEquals("LNB", dependency.getDriver().getName());
			assertEquals("AA", dependency.getConsumer().getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		dependency.setOptional(true);
		response = dependencyAPI.update(dependency);
		if (response.isDone()) {
			dependency = (Dependency) response.getMessage();
			assertEquals(true, dependency.isOptional());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = dependencyAPI.show("id=='"+dependency.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<Dependency>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = dependencyAPI.delete(dependency.getId());
		if (response.isDone()) {
			assertEquals("Dependency Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = dependencyAPI.showByDriver("id=='"+driver.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Dependency>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = dependencyAPI.showByConsumer("id=='"+consumer.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Dependency>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}
	
}
