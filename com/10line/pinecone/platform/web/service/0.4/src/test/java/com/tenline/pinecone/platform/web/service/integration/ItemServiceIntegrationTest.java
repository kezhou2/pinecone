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

import com.tenline.pinecone.platform.sdk.CategoryAPI;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.DriverAPI;
import com.tenline.pinecone.platform.sdk.ItemAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.VariableAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;

/**
 * @author Bill
 *
 */
public class ItemServiceIntegrationTest extends AuthorizationServiceIntegrationTest {
	
	private User user;
	
	private Category category;
	
	private Driver driver;
	
	private Device device;
	
	private Variable variable;
	
	private Item item;
	
	private UserAPI userAPI;
	
	private CategoryAPI categoryAPI;
	
	private DriverAPI driverAPI;
	
	private DeviceAPI deviceAPI;
	
	private VariableAPI variableAPI;
	
	private ItemAPI itemAPI;
	
	@Before
	public void testSetup() throws Exception {
		super.testSetup();
		category = new Category();
		category.setType(Category.COM);
		driver = new Driver();
		driver.setName("LNB");
		user = new User();
		user.setName("bill");
		device = new Device();
		device.setDefault(false);
		device.setStatus(Device.CLOSED);
		variable = new Variable();
		variable.setName("A");
		variable.setType("read_only");
		item = new Item();
		item.setText("A");
		item.setValue("0".getBytes());
		deviceAPI = new DeviceAPI("localhost", "8888", "service");
		variableAPI = new VariableAPI("localhost", "8888", "service");
		itemAPI = new ItemAPI("localhost", "8888", "service");
		categoryAPI = new CategoryAPI("localhost", "8888", "service");
		driverAPI = new DriverAPI("localhost", "8888", "service");
		userAPI = new UserAPI("localhost", "8888", "service");
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
		response = userAPI.create(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("bill", user.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		device.setDriver(driver);
		device.setUser(user);
		response = deviceAPI.create(device);
		if (response.isDone()) {
			device = (Device) response.getMessage();
			assertEquals(false, device.isDefault());
			assertEquals(Device.CLOSED, device.getStatus());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		variable.setDevice(device);
		response = variableAPI.create(variable);
		if (response.isDone()) {
			variable = (Variable) response.getMessage();
			assertEquals("A", variable.getName());
			assertEquals("read_only", variable.getType());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		item.setVariable(variable);
	}
	
	@After
	public void testShutdown() throws Exception {
		APIResponse response = categoryAPI.delete(category.getId());
		if (response.isDone()) {
			assertEquals("Category Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = userAPI.delete(user.getId());
		if (response.isDone()) {
			assertEquals("User Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		user = null;
		category = null;
		driver = null;
		device = null;
		variable = null;
		item = null;
		userAPI = null;
		categoryAPI = null;
		driverAPI = null;
		deviceAPI = null;
		variableAPI = null;
		itemAPI = null;
		super.testShutdown();
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = itemAPI.create(item);
		if (response.isDone()) {
			item = (Item) response.getMessage();
			assertEquals("A", item.getText());
			assertEquals("0", new String(item.getValue()));
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		item.setText("B");
		item.setValue("1".getBytes());
		response = itemAPI.update(item);
		if (response.isDone()) {
			item = (Item) response.getMessage();
			assertEquals("B", item.getText());
			assertEquals("1", new String(item.getValue()));
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = itemAPI.show("id=='"+item.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<Item>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = itemAPI.delete(item.getId());
		if (response.isDone()) {
			assertEquals("Item Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = itemAPI.showByVariable("id=='"+variable.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(0, ((Collection<Item>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
