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

import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.DeviceDependency;
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.DeviceDependencyAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class DeviceDependencyServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private Device device;
	
	private Consumer consumer;
	
	private DeviceDependency dependency;
	
	private DeviceAPI deviceAPI;
	
	private ConsumerAPI consumerAPI;
	
	private DeviceDependencyAPI dependencyAPI;
	
	@Before
	public void testSetup() throws Exception {
		device = new Device();
		device.setName("LNB");
		consumer = new Consumer();
		consumer.setDisplayName("AA");
		dependency = new DeviceDependency();
		dependency.setOptional(false);
		deviceAPI = new DeviceAPI("localhost", "8888", "service");
		consumerAPI = new ConsumerAPI("localhost", "8888", "service");
		dependencyAPI = new DeviceDependencyAPI("localhost", "8888", "service");
		APIResponse response = deviceAPI.create(device);
		if (response.isDone()) {
			device = (Device) response.getMessage();
			assertEquals("LNB", device.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = consumerAPI.create(consumer);
		if (response.isDone()) {
			consumer = (Consumer) response.getMessage();
			assertEquals("AA", consumer.getDisplayName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		dependency.setConsumer(consumer);
		dependency.setDevice(device);
	}
	
	@After
	public void testShutdown() throws Exception {
		APIResponse response = deviceAPI.delete(device.getId());
		if (response.isDone()) {
			assertEquals("Device Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = consumerAPI.delete(consumer.getId());
		if (response.isDone()) {
			assertEquals("Consumer Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		device = null;
		consumer = null;
		dependency = null;
		deviceAPI = null;
		consumerAPI = null;
		dependencyAPI = null;
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = dependencyAPI.create(dependency);
		if (response.isDone()) {
			dependency = (DeviceDependency) response.getMessage();
			assertEquals(false, dependency.isOptional());
			assertEquals("LNB", dependency.getDevice().getName());
			assertEquals("AA", dependency.getConsumer().getDisplayName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		dependency.setOptional(true);
		response = dependencyAPI.update(dependency);
		if (response.isDone()) {
			dependency = (DeviceDependency) response.getMessage();
			assertEquals(true, dependency.isOptional());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = dependencyAPI.show("id=='"+dependency.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<DeviceDependency>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = dependencyAPI.delete(dependency.getId());
		if (response.isDone()) {
			assertEquals("Device Dependency Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = dependencyAPI.showByDevice("id=='"+device.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<DeviceDependency>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = dependencyAPI.showByConsumer("id=='"+consumer.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<DeviceDependency>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}
	
}
