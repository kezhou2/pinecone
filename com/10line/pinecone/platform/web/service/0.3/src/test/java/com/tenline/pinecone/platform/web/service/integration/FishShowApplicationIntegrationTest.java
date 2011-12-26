/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.ItemAPI;
import com.tenline.pinecone.platform.sdk.VariableAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class FishShowApplicationIntegrationTest extends AbstractServiceIntegrationTest {
	
	private Device device;
	
	private Variable variable;
	
	private Item item;
	
	private DeviceAPI deviceAPI;
	
	private VariableAPI variableAPI;
	
	private ItemAPI itemAPI;
	
	@Before
	public void testSetup() {
		device = new Device();
		device.setName("e-fish智能鱼缸");
		device.setSymbolicName("com.tenline.pinecone.platform.osgi.device.efish");
		device.setVersion("0.1.0");
		variable = new Variable();
		variable.setName("鱼缸水温");
		variable.setType("read_write_discrete");
		item = new Item();
		item.setText("12°C");
		item.setValue("23810".getBytes());
		deviceAPI = new DeviceAPI("localhost", "8888", "service");
		variableAPI = new VariableAPI("localhost", "8888", "service");
		itemAPI = new ItemAPI("localhost", "8888", "service");
	}
	
	@After
	public void testShutdown() {
		device = null;
		variable = null;
		item = null;
		deviceAPI = null;
		variableAPI = null;
		itemAPI = null;
	}
	
	@Test
	public void test() throws Exception {
		// TODO Auto-generated constructor stub
		APIResponse response = deviceAPI.create(device);
		if (response.isDone()) {
			device = (Device) response.getMessage();
			assertEquals("e-fish智能鱼缸", device.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		variable.setDevice(device);
		response = variableAPI.create(variable);
		if (response.isDone()) {
			variable = (Variable) response.getMessage();
			assertEquals("鱼缸水温", variable.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		item.setVariable(variable);
		response = itemAPI.create(item);
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
		response = itemAPI.create(item);
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
		response = variableAPI.create(variable);
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
		response = itemAPI.create(item);
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
		response = itemAPI.create(item);
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
		response = variableAPI.create(variable);
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
		response = itemAPI.create(item);
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
		response = itemAPI.create(item);
		if (response.isDone()) {
			item = (Item) response.getMessage();
			assertEquals("在线", item.getText());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = deviceAPI.delete(device.getId());
		if (response.isDone()) {
			assertEquals("Device Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
