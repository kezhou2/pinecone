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

import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.RecordAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.VariableAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Record;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;

/**
 * @author Bill
 *
 */
public class RecordServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private User user;
	
	private Device device;
	
	private Variable variable;
	
	private Record record;
	
	private UserAPI userAPI;
	
	private DeviceAPI deviceAPI;
	
	private VariableAPI variableAPI;
	
	private RecordAPI recordAPI;
	
	@Before
	public void testSetup() {
		super.testSetup();
		user = new User();
		user.setName("bill");
		device = new Device();
		device.setName("LNB");
		device.setSymbolicName("com.10line.pinecone");
		device.setVersion("1.1");
		variable = new Variable();
		variable.setName("A");
		variable.setType("read_only");
		record = new Record();
		record.setValue("0");
		userAPI = new UserAPI("localhost", "8080", "service");
		deviceAPI = new DeviceAPI("localhost", "8080", "service");
		variableAPI = new VariableAPI("localhost", "8080", "service");
		recordAPI = new RecordAPI("localhost", "8080", "service");
	}
	
	@After
	public void testShutdown() {
		user = null;
		device = null;
		variable = null;
		record = null;
		userAPI = null;
		deviceAPI = null;
		variableAPI = null;
		recordAPI = null;
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
		device.setUser(user);
		response = deviceAPI.create(device);
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
		record.setVariable(variable);
		response = recordAPI.create(record);
		if (response.isDone()) {
			record = (Record) response.getMessage();
			assertEquals("0", record.getValue());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		record.setValue("1");
		response = recordAPI.update(record);
		if (response.isDone()) {
			record = (Record) response.getMessage();
			assertEquals("1", record.getValue());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = recordAPI.show("id=='"+record.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
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
		response = recordAPI.showByVariable("id=='"+variable.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(0, ((Collection<Record>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
