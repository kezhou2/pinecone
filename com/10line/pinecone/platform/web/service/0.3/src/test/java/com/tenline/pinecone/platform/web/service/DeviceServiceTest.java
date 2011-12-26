/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.web.service.restful.DeviceRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DeviceServiceTest extends AbstractServiceTest {

	private Device device;
	
	private List devices;
	
	private DeviceRestfulService deviceService;
	
	@Before
	public void testSetup() {
		deviceService = new DeviceRestfulService(persistenceManagerFactory);
		deviceService.setJdoTemplate(jdoTemplate);
		device = new Device();
		device.setId("asa");
		device.setName("ACU");
		devices = new ArrayList();
		devices.add(device);
	}
	
	@After
	public void testShutdown() {	
		deviceService = null;
		devices.remove(device);
		device = null;
		devices = null;
	}

	@Test
	public void testCreate() {
		when(jdoTemplate.makePersistent(device)).thenReturn(device);
		Device result = deviceService.create(device);
		verify(jdoTemplate).makePersistent(device);
		assertEquals("ACU", result.getName());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Device.class, device.getId())).thenReturn(device);
		Response result = deviceService.delete(device.getId());
		verify(jdoTemplate).getObjectById(Device.class, device.getId());
		verify(jdoTemplate).deletePersistent(device);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Device.class, device.getId())).thenReturn(device);
		when(jdoTemplate.makePersistent(device)).thenReturn(device);
		Device result = deviceService.update(device);
		verify(jdoTemplate).getObjectById(Device.class, device.getId());
		verify(jdoTemplate).makePersistent(device);
		assertEquals("ACU", result.getName());
	}
	
	@Test
	public void testShow() {
		String filter = "name=='ACU'";
		String queryString = "select from " + Device.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(devices);
		Collection<Device> result = deviceService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
