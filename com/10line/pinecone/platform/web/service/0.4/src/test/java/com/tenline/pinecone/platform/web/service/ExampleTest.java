/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.ModelAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class ExampleTest extends AuthorizationServiceTest {
	
	private User user;
	
	private Category category;
	
	private Driver driver;
	
	private Device device;
	
	private Variable variable;
	
	private Item item;
	
	private ModelAPI modelAPI;
	
	@Before
	public void testSetup() throws Exception {
		super.testSetup();
		category = new Category();
		category.setType(Category.COM);
		driver = new Driver();
		driver.setName("智能鱼缸");
		user = new User();
		user.setName("bill");
		device = new Device();
		device.setDefault(false);
		device.setStatus(Device.OPENED);
		variable = new Variable();
		variable.setName("鱼缸水温");
		variable.setType("read_write_discrete");
		item = new Item();
		item.setText("12°C");
		item.setValue("23810".getBytes());
		modelAPI = new ModelAPI(HOST, PORT, CONTEXT);
		APIResponse response = modelAPI.create(category);
		if (response.isDone()) {
			category = (Category) response.getMessage();
			assertEquals(Category.COM, category.getType());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		driver.setCategory(category);
		response = modelAPI.create(driver);
		if (response.isDone()) {
			driver = (Driver) response.getMessage();
			assertEquals("智能鱼缸", driver.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = modelAPI.create(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("bill", user.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		device.setDriver(driver);
		device.setUser(user);
	}
	
	@After
	public void testShutdown() throws Exception {
		APIResponse response = modelAPI.delete(category);
		if (response.isDone()) {
			assertEquals("Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = modelAPI.delete(user);
		if (response.isDone()) {
			assertEquals("Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		user = null;
		category = null;
		driver = null;
		device = null;
		variable = null;
		item = null;
		modelAPI = null;
		super.testShutdown();
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void test() throws Exception {
		// TODO Auto-generated constructor stub
		APIResponse response = modelAPI.create(device);
		if (response.isDone()) {
			device = (Device) response.getMessage();
			assertEquals(false, device.isDefault());
			assertEquals(Device.OPENED, device.getStatus());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = modelAPI.show(Device.class, "id=='"+device.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(1, ((Collection<Device>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		device.setStatus(Device.CLOSED);
		response = modelAPI.update(device);
		if (response.isDone()) {
			assertEquals(Device.CLOSED, ((Device) response.getMessage()).getStatus());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		variable.setDevice(device);
		response = modelAPI.create(variable);
		if (response.isDone()) {
			variable = (Variable) response.getMessage();
			assertEquals("鱼缸水温", variable.getName());
			assertEquals(Device.CLOSED, variable.getDevice().getStatus());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		item.setVariable(variable);
		response = modelAPI.create(item);
		if (response.isDone()) {
			item = (Item) response.getMessage();
			assertEquals("12°C", item.getText());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		item = new Item();
		item.setText("13°C");
		item.setValue("21250".getBytes());
		item.setVariable(variable);
		response = modelAPI.create(item);
		if (response.isDone()) {
			item = (Item) response.getMessage();
			assertEquals("13°C", item.getText());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		variable = new Variable();
		variable.setName("制氧频率");
		variable.setType("write_discrete");
		variable.setDevice(device);
		response = modelAPI.create(variable);
		if (response.isDone()) {
			variable = (Variable) response.getMessage();
			assertEquals("制氧频率", variable.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		item = new Item();
		item.setText("1-5");
		item.setValue("281".getBytes());
		item.setVariable(variable);
		response = modelAPI.create(item);
		if (response.isDone()) {
			item = (Item) response.getMessage();
			assertEquals("1-5", item.getText());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		item = new Item();
		item.setText("1-6");
		item.setValue("286".getBytes());
		item.setVariable(variable);
		response = modelAPI.create(item);
		if (response.isDone()) {
			item = (Item) response.getMessage();
			assertEquals("1-6", item.getText());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		variable = new Variable();
		variable.setName("鱼缸状态");
		variable.setType("read_discrete");
		variable.setDevice(device);
		response = modelAPI.create(variable);
		if (response.isDone()) {
			variable = (Variable) response.getMessage();
			assertEquals("鱼缸状态", variable.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		item = new Item();
		item.setText("离线");
		item.setValue("0".getBytes());
		item.setVariable(variable);
		response = modelAPI.create(item);
		if (response.isDone()) {
			item = (Item) response.getMessage();
			assertEquals("离线", item.getText());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		item = new Item();
		item.setText("在线");
		item.setValue("1".getBytes());
		item.setVariable(variable);
		response = modelAPI.create(item);
		if (response.isDone()) {
			item = (Item) response.getMessage();
			assertEquals("在线", item.getText());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
