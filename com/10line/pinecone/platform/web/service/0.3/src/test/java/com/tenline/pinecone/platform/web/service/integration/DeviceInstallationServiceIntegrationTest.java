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

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.DeviceInstallation;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.DeviceInstallationAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class DeviceInstallationServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private Device device;
	
	private User user;
	
	private DeviceInstallation installation;
	
	private DeviceAPI deviceAPI;
	
	private UserAPI userAPI;
	
	private DeviceInstallationAPI installationAPI;
	
	@Before
	public void testSetup() throws Exception {
		device = new Device();
		device.setName("LNB");
		user = new User();
		user.setName("AA");
		installation = new DeviceInstallation();
		installation.setDefault(false);
		deviceAPI = new DeviceAPI("localhost", "8888", "service");
		userAPI = new UserAPI("localhost", "8888", "service");
		installationAPI = new DeviceInstallationAPI("localhost", "8888", "service");
		APIResponse response = deviceAPI.create(device);
		if (response.isDone()) {
			device = (Device) response.getMessage();
			assertEquals("LNB", device.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = userAPI.create(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("AA", user.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		installation.setUser(user);
		installation.setDevice(device);
	}
	
	@After
	public void testShutdown() throws Exception {
		APIResponse response = deviceAPI.delete(device.getId());
		if (response.isDone()) {
			assertEquals("Device Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = userAPI.delete(user.getId());
		if (response.isDone()) {
			assertEquals("User Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		device = null;
		user = null;
		installation = null;
		deviceAPI = null;
		userAPI = null;
		installationAPI = null;
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = installationAPI.create(installation);
		if (response.isDone()) {
			installation = (DeviceInstallation) response.getMessage();
			assertEquals(false, installation.isDefault());
			assertEquals("LNB", installation.getDevice().getName());
			assertEquals("AA", installation.getUser().getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		installation.setDefault(true);
		response = installationAPI.update(installation);
		if (response.isDone()) {
			installation = (DeviceInstallation) response.getMessage();
			assertEquals(true, installation.isDefault());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = installationAPI.show("id=='"+installation.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<DeviceInstallation>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = installationAPI.delete(installation.getId());
		if (response.isDone()) {
			assertEquals("Device Installation Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = installationAPI.showByDevice("id=='"+device.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<DeviceInstallation>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = installationAPI.showByUser("id=='"+user.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<DeviceInstallation>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}
	
}
