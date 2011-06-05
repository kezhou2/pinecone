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
import com.tenline.pinecone.model.Device;
import com.tenline.pinecone.model.User;

/**
 * @author Bill
 *
 */
public class DeviceServiceIntegrationTest {
	
	private User user;
	
	private Device device;
	
	private PineconeUserAPI userAPI;
	
	private PineconeDeviceAPI deviceAPI;
	
	@Before
	public void testSetup() {
		user = new User();
		user.setSnsId("251417324");
		device = new Device();
		device.setName("LNB");
		device.setGroupId("com.10line.pinecone");
		device.setArtifactId("efish");
		device.setVersion("1.1");
	}
	
	@After
	public void testShutdown() {
		user = null;
		device = null;
		userAPI = null;
		deviceAPI = null;
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
		deviceAPI = new PineconeDeviceAPI("localhost", "8080", new PineconeAPIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				device = (Device) message;
				assertEquals("ACU", device.getName());
				assertEquals("com.sun", device.getGroupId());
				assertEquals("e-fish", device.getArtifactId());
				assertEquals("2.1", device.getVersion());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		device.setName("ACU");
		device.setGroupId("com.sun");
		device.setArtifactId("e-fish");
		device.setVersion("2.1");
		deviceAPI.update(device);
		deviceAPI = new PineconeDeviceAPI("localhost", "8080", new PineconeAPIListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(1, ((Collection<Device>) message).size());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		deviceAPI.show("id=='"+device.getId()+"'");
		deviceAPI = new PineconeDeviceAPI("localhost", "8080", new PineconeAPIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals("Device Deleted!", message.toString());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		deviceAPI.delete(device.getId());
		deviceAPI = new PineconeDeviceAPI("localhost", "8080", new PineconeAPIListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(0, ((Collection<Device>) message).size());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		deviceAPI.show("id=='"+device.getId()+"'");
	}

}
