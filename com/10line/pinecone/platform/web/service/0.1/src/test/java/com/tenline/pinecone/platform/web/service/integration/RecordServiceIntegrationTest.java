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
import com.tenline.pinecone.platform.sdk.RecordAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.VariableAPI;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Record;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;

/**
 * @author Bill
 *
 */
public class RecordServiceIntegrationTest {

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
		user = new User();
		user.setSnsId("251417324");
		device = new Device();
		device.setName("LNB");
		device.setSymbolicName("com.10line.pinecone");
		device.setVersion("1.1");
		variable = new Variable();
		variable.setName("A");
		variable.setType("read_only");
		record = new Record();
		record.setValue("0");
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
		recordAPI = new RecordAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				record = (Record) message;
				assertEquals("0", record.getValue());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		record.setVariable(variable);
		recordAPI.create(record);
		recordAPI = new RecordAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				record = (Record) message;
				assertEquals("1", record.getValue());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		record.setValue("1");
		recordAPI.update(record);
		recordAPI = new RecordAPI("localhost", "8080", new APIListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(1, ((Collection<Record>) message).size());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		recordAPI.show("id=='"+record.getId()+"'");
		recordAPI = new RecordAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals("Record Deleted!", message.toString());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		recordAPI.delete(record.getId());
		recordAPI = new RecordAPI("localhost", "8080", new APIListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(0, ((Collection<Record>) message).size());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		recordAPI.show("id=='"+record.getId()+"'");
	}

}
