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
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.VariableAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;

/**
 * @author Bill
 *
 */
public class VariableServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private User user;
	
	private Device device;
	
	private Variable variable;
	
	private UserAPI userAPI;
	
	private DeviceAPI deviceAPI;
	
	private VariableAPI variableAPI;
	
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
		userAPI = new UserAPI("localhost", "8080", "service");
		deviceAPI = new DeviceAPI("localhost", "8080", "service");
		variableAPI = new VariableAPI("localhost", "8080", "service");
	}
	
	@After
	public void testShutdown() {
		user = null;
		device = null;
		variable = null;
		userAPI = null;
		deviceAPI = null;
		variableAPI = null;
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
		variable.setName("B");
		variable.setType("write_only");
		response = variableAPI.update(variable);
		if (response.isDone()) {
			variable = (Variable) response.getMessage();
			assertEquals("B", variable.getName());
			assertEquals("write_only", variable.getType());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = variableAPI.show("id=='"+variable.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(1, ((Collection<Variable>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = variableAPI.delete(variable.getId());
		if (response.isDone()) {
			assertEquals("Variable Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = variableAPI.showByDevice("id=='"+device.getId()+"'", consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals(0, ((Collection<Variable>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
