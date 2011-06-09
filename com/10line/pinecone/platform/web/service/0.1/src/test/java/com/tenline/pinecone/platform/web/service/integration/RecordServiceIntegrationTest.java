/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.sdk.PineconeAPIListener;
import com.tenline.pinecone.platform.sdk.PineconeDeviceAPI;
import com.tenline.pinecone.platform.sdk.PineconeRecordAPI;
import com.tenline.pinecone.platform.sdk.PineconeUserAPI;
import com.tenline.pinecone.platform.sdk.PineconeVariableAPI;
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
	
	private PineconeUserAPI userAPI;
	
	private PineconeDeviceAPI deviceAPI;
	
	private PineconeVariableAPI variableAPI;
	
	private PineconeRecordAPI recordAPI;
	
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
		recordAPI = new PineconeRecordAPI("localhost", "8080", new PineconeAPIListener() {

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
		recordAPI = new PineconeRecordAPI("localhost", "8080", new PineconeAPIListener() {

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
		recordAPI = new PineconeRecordAPI("localhost", "8080", new PineconeAPIListener() {

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
		recordAPI = new PineconeRecordAPI("localhost", "8080", new PineconeAPIListener() {

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
		recordAPI = new PineconeRecordAPI("localhost", "8080", new PineconeAPIListener() {

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
