/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.VariableAPI;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;

/**
 * @author Bill
 *
 */
public class VariableServiceIntegrationTest {

	private User user;
	
	private Device device;
	
	private Variable variable;
	
	private UserAPI userAPI;
	
	private DeviceAPI deviceAPI;
	
	private VariableAPI variableAPI;
	
	@Before
	public void testSetup() {
		user = new User();
		user.setSnsId("251417324");
		device = new Device();
		device.setName("LNB");
		device.setSymbolicName("com.10line.pinecone");
		device.setVersion("1.1");
		variable = new Variable();
		variable.setName("A");
		variable.setType("read_only");
	}
	
	@After
	public void testShutdown() {
		user = null;
		device = null;
		variable = null;
		userAPI = null;
		deviceAPI = null;
		variableAPI = null;
	}
	
	@Test
	public void testCRUD() throws Exception {
		userAPI = new UserAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				user = (User) message;
				assertEquals("251417324", user.getSnsId());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		userAPI.create(user);
		deviceAPI = new DeviceAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				device = (Device) message;
				assertEquals("LNB", device.getName());
				assertEquals("com.10line.pinecone", device.getSymbolicName());
				assertEquals("1.1", device.getVersion());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		device.setUser(user);
		deviceAPI.create(device);
		variableAPI = new VariableAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				variable = (Variable) message;
				assertEquals("A", variable.getName());
				assertEquals("read_only", variable.getType());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		variable.setDevice(device);
		variableAPI.create(variable);
		variableAPI = new VariableAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				variable = (Variable) message;
				assertEquals("B", variable.getName());
				assertEquals("write_only", variable.getType());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		variable.setName("B");
		variable.setType("write_only");
		variableAPI.update(variable);
		variableAPI = new VariableAPI("localhost", "8080", new APIListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(1, ((Collection<Variable>) message).size());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		variableAPI.show("id=='"+variable.getId()+"'");
		variableAPI = new VariableAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals("Variable Deleted!", message.toString());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		variableAPI.delete(variable.getId());	
		variableAPI = new VariableAPI("localhost", "8080", new APIListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(0, ((Collection<Variable>) message).size());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		variableAPI.showByDevice("id=='"+device.getId()+"'");
	}

}
