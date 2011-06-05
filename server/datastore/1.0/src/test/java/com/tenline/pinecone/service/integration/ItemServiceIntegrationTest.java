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
import com.tenline.pinecone.PineconeItemAPI;
import com.tenline.pinecone.PineconeUserAPI;
import com.tenline.pinecone.PineconeVariableAPI;
import com.tenline.pinecone.model.Device;
import com.tenline.pinecone.model.Item;
import com.tenline.pinecone.model.User;
import com.tenline.pinecone.model.Variable;

/**
 * @author Bill
 *
 */
public class ItemServiceIntegrationTest {

	private User user;
	
	private Device device;
	
	private Variable variable;
	
	private Item item;
	
	private PineconeUserAPI userAPI;
	
	private PineconeDeviceAPI deviceAPI;
	
	private PineconeVariableAPI variableAPI;
	
	private PineconeItemAPI itemAPI;
	
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
		item = new Item();
		item.setText("A");
		item.setValue("0");
	}
	
	@After
	public void testShutdown() {
		user = null;
		device = null;
		variable = null;
		item = null;
		userAPI = null;
		deviceAPI = null;
		variableAPI = null;
		itemAPI = null;
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
		itemAPI = new PineconeItemAPI("localhost", "8080", new PineconeAPIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				item = (Item) message;
				assertEquals("A", item.getText());
				assertEquals("0", item.getValue());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		item.setVariable(variable);
		itemAPI.create(item);
		itemAPI = new PineconeItemAPI("localhost", "8080", new PineconeAPIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				item = (Item) message;
				assertEquals("B", item.getText());
				assertEquals("1", item.getValue());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		item.setText("B");
		item.setValue("1");
		itemAPI.update(item);
		itemAPI = new PineconeItemAPI("localhost", "8080", new PineconeAPIListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(1, ((Collection<Item>) message).size());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		itemAPI.show("id=='"+item.getId()+"'");
		itemAPI = new PineconeItemAPI("localhost", "8080", new PineconeAPIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals("Item Deleted!", message.toString());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		itemAPI.delete(item.getId());
		itemAPI = new PineconeItemAPI("localhost", "8080", new PineconeAPIListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(0, ((Collection<Item>) message).size());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		itemAPI.show("id=='"+item.getId()+"'");
	}

}
