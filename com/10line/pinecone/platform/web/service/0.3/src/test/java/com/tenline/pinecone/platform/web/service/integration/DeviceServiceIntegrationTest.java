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
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.model.Device;

/**
 * @author Bill
 *
 */
public class DeviceServiceIntegrationTest extends AbstractServiceIntegrationTest {
	
	private Device device;
	
	private DeviceAPI deviceAPI;
	
	@Before
	public void testSetup() throws Exception {
		device = new Device();
		device.setName("LNB");
		device.setSymbolicName("com.10line.pinecone");
		device.setVersion("1.1");
		deviceAPI = new DeviceAPI("localhost", "8888", "service");
	}
	
	@After
	public void testShutdown() throws Exception {
		device = null;
		deviceAPI = null;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = deviceAPI.create(device);
		if (response.isDone()) {
			device = (Device) response.getMessage();
			assertEquals("LNB", device.getName());
			assertEquals("com.10line.pinecone", device.getSymbolicName());
			assertEquals("1.1", device.getVersion());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		device.setName("ACU");
		device.setSymbolicName("com.sun");
		device.setVersion("2.1");
		response = deviceAPI.update(device);
		if (response.isDone()) {
			device = (Device) response.getMessage();
			assertEquals("ACU", device.getName());
			assertEquals("com.sun", device.getSymbolicName());
			assertEquals("2.1", device.getVersion());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = deviceAPI.show("id=='"+device.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<Device>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = deviceAPI.delete(device.getId());
		if (response.isDone()) {
			assertEquals("Device Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = deviceAPI.show("id=='"+device.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Device>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
