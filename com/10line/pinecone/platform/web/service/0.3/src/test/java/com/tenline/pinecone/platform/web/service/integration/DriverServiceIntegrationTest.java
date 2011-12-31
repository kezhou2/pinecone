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
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.sdk.CategoryAPI;
import com.tenline.pinecone.platform.sdk.DriverAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class DriverServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private Category category;
	
	private Driver driver;
	
	private CategoryAPI categoryAPI;
	
	private DriverAPI driverAPI;
	
	@Before
	public void testSetup() throws Exception {
		category = new Category();
		category.setType(Category.COM);
		driver = new Driver();
		driver.setName("fishshow");
		driver.setIcon("123".getBytes());
		driver.setAlias("fishshow");
		driver.setVersion("0.1");
		driver.setCategory(category);
		categoryAPI = new CategoryAPI("localhost", "8888", "service");
		driverAPI = new DriverAPI("localhost", "8888", "service");
		APIResponse response = categoryAPI.create(category);
		if (response.isDone()) {
			category = (Category) response.getMessage();
			assertEquals(Category.COM, category.getType());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		driver.setCategory(category);
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
		categoryAPI = null;
		driverAPI = null;
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = driverAPI.create(driver);
		if (response.isDone()) {
			driver = (Driver) response.getMessage();
			assertEquals("fishshow", driver.getAlias());
			assertEquals("fishshow", driver.getName());
			assertEquals("123", new String(driver.getIcon()));
			assertEquals("0.1", driver.getVersion());
			assertEquals(Category.COM, driver.getCategory().getType());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		driver.setAlias("fishshow2");
		driver.setName("fishshow2");
		driver.setIcon("234".getBytes());
		driver.setVersion("0.2");
		response = driverAPI.update(driver);
		if (response.isDone()) {
			driver = (Driver) response.getMessage();
			assertEquals("fishshow2", driver.getAlias());
			assertEquals("fishshow2", driver.getName());
			assertEquals("234", new String(driver.getIcon()));
			assertEquals("0.2", driver.getVersion());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = driverAPI.show("id=='"+driver.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<Driver>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = driverAPI.delete(driver.getId());
		if (response.isDone()) {
			assertEquals("Driver Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = driverAPI.showByCategory("id=='"+category.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Driver>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
