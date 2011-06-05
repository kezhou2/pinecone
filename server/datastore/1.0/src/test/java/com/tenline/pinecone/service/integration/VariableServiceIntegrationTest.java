/**
 * 
 */
package com.tenline.pinecone.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.PineconeAPIListener;
import com.tenline.pinecone.PineconeDeviceAPI;
import com.tenline.pinecone.PineconeUserAPI;
import com.tenline.pinecone.PineconeVariableAPI;
import com.tenline.pinecone.model.Device;
import com.tenline.pinecone.model.User;
import com.tenline.pinecone.model.Variable;

/**
 * @author Bill
 *
 */
public class VariableServiceIntegrationTest {

	private User user;
	
	private Device device;
	
	private Variable variable;
	
	private PineconeUserAPI userAPI;
	
	private PineconeDeviceAPI deviceAPI;
	
	private PineconeVariableAPI variableAPI;
	
	@Before
	public void testSetup() {
		user = new User();
		user.setSnsId("251417324");
		device = new Device();
		device.setName("LNB");
		device.setGroupId("com.10line.pinecone");
		device.setArtifactId("efish");
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
		userAPI = new PineconeUserAPI("localhost", "8080", new PineconeAPIListener() {

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
		deviceAPI = new PineconeDeviceAPI("localhost", "8080", new PineconeAPIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				device = (Device) message;
				assertEquals("LNB", device.getName());
				assertEquals("com.10line.pinecone", device.getGroupId());
				assertEquals("efish", device.getArtifactId());
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
		variableAPI = new PineconeVariableAPI("localhost", "8080", new PineconeAPIListener() {

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
		variableAPI = new PineconeVariableAPI("localhost", "8080", new PineconeAPIListener() {

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
		variableAPI = new PineconeVariableAPI("localhost", "8080", new PineconeAPIListener() {

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
		variableAPI = new PineconeVariableAPI("localhost", "8080", new PineconeAPIListener() {

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
		variableAPI = new PineconeVariableAPI("localhost", "8080", new PineconeAPIListener() {

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
		variableAPI.show("id=='"+variable.getId()+"'");
	}

}
