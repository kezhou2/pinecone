/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.User;

/**
 * @author Bill
 *
 */
public class DeviceServiceIntegrationTest {
	
	private User user;
	
	private Device device;
	
	private UserAPI userAPI;
	
	private DeviceAPI deviceAPI;
	
	@Before
	public void testSetup() {
		user = new User();
		user.setSnsId("251417324");
		device = new Device();
		device.setName("LNB");
		device.setSymbolicName("com.10line.pinecone");
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
		deviceAPI = new DeviceAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				device = (Device) message;
				assertEquals("ACU", device.getName());
				assertEquals("com.sun", device.getSymbolicName());
				assertEquals("2.1", device.getVersion());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		device.setName("ACU");
		device.setSymbolicName("com.sun");
		device.setVersion("2.1");
		deviceAPI.update(device);
		deviceAPI = new DeviceAPI("localhost", "8080", new APIListener() {

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
		deviceAPI = new DeviceAPI("localhost", "8080", new APIListener() {

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
		deviceAPI = new DeviceAPI("localhost", "8080", new APIListener() {

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
		deviceAPI.showByUser("id=='"+user.getId()+"'");
		deviceAPI = new DeviceAPI("pinecone.googlecode.com", "80", new APIListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertNotNull(((Collection<Device>) message).size());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		deviceAPI.get();
	}

}
