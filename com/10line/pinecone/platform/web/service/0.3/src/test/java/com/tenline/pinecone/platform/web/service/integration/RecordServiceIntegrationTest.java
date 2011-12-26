/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.ItemAPI;
import com.tenline.pinecone.platform.sdk.RecordAPI;
import com.tenline.pinecone.platform.sdk.VariableAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Record;
import com.tenline.pinecone.platform.model.Variable;

/**
 * @author Bill
 *
 */
public class RecordServiceIntegrationTest extends AuthorizationServiceIntegrationTest {
	
	private Device device;
	
	private Variable variable;
	
	private Item item;
	
	private Record record;
	
	private DeviceAPI deviceAPI;
	
	private VariableAPI variableAPI;
	
	private ItemAPI itemAPI;
	
	private RecordAPI recordAPI;
	
	@Before
	public void testSetup() throws Exception {
		super.testSetup();
		device = new Device();
		device.setName("LNB");
		device.setSymbolicName("com.10line.pinecone");
		device.setVersion("1.1");
		variable = new Variable();
		variable.setName("A");
		variable.setType("read_only");
		item = new Item();
		item.setText("AA");
		record = new Record();
		record.setTimestamp(new Date());
		deviceAPI = new DeviceAPI("localhost", "8888", "service");
		variableAPI = new VariableAPI("localhost", "8888", "service");
		recordAPI = new RecordAPI("localhost", "8888", "service");
		itemAPI = new ItemAPI("localhost", "8888", "service");
		APIResponse response = deviceAPI.create(device);
		if (response.isDone()) {
			device = (Device) response.getMessage();
			assertEquals("LNB", device.getName());
			assertEquals("com.10line.pinecone", device.getSymbolicName());
			assertEquals("1.1", device.getVersion());
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
		response = itemAPI.create(item);
		if (response.isDone()) {
			item = (Item) response.getMessage();
			assertEquals("AA", item.getText());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		record.setItem(item);
		record.setVariable(variable);
		record.setDevice(device);
	}
	
	@After
	public void testShutdown() throws Exception {
		APIResponse response = deviceAPI.delete(device.getId());
		if (response.isDone()) {
			assertEquals("Device Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		device = null;
		variable = null;
		item = null;
		record = null;
		deviceAPI = null;
		variableAPI = null;
		itemAPI = null;
		recordAPI = null;
		super.testShutdown();
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = recordAPI.create(record);
		if (response.isDone()) {
			record = (Record) response.getMessage();
			assertEquals("LNB", record.getDevice().getName());
			assertEquals("A", record.getVariable().getName());
			assertEquals("AA", record.getItem().getText());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		Date timestamp = new Date();
		record.setTimestamp(timestamp);
		response = recordAPI.update(record);
		if (response.isDone()) {
			record = (Record) response.getMessage();
			assertEquals(timestamp, record.getTimestamp());
			assertEquals("LNB", record.getDevice().getName());
			assertEquals("A", record.getVariable().getName());
			assertEquals("AA", record.getItem().getText());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = recordAPI.show("id=='"+record.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<Record>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = recordAPI.delete(record.getId());
		if (response.isDone()) {
			assertEquals("Record Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = recordAPI.showByDevice("id=='"+device.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Record>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = recordAPI.showByVariable("id=='"+variable.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(0, ((Collection<Record>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = recordAPI.showByItem("id=='"+item.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Record>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
