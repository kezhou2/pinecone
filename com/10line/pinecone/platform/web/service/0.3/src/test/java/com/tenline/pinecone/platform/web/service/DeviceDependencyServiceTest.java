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

import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.DeviceDependency;
import com.tenline.pinecone.platform.web.service.restful.DeviceDependencyRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DeviceDependencyServiceTest extends AbstractServiceTest {

	private Device device;
	
	private Consumer consumer;
	
	private DeviceDependency dependency;
	
	private List dependencies;
	
	private DeviceDependencyRestfulService dependencyService;
	
	@Before
	public void testSetup() {
		dependencyService = new DeviceDependencyRestfulService(persistenceManagerFactory);
		dependencyService.setJdoTemplate(jdoTemplate);
		dependency = new DeviceDependency();
		dependency.setId("asa");
		dependency.setOptional(false);
		consumer = new Consumer();
		consumer.setId("ddd");
		dependency.setConsumer(consumer);
		device = new Device();
		device.setId("ccc");
		dependency.setDevice(device);
		dependencies = new ArrayList();
		dependencies.add(dependency);
	}
	
	@After
	public void testShutdown() {	
		dependencyService = null;
		dependencies.remove(dependency);
		device = null;
		consumer = null;
		dependency = null;
		dependencies = null;
	}
	
	@Test
	public void testCreate() {
		when(jdoTemplate.getObjectById(Device.class, device.getId())).thenReturn(device);
		when(jdoTemplate.getObjectById(Consumer.class, consumer.getId())).thenReturn(consumer);
		when(jdoTemplate.makePersistent(dependency)).thenReturn(dependency);
		DeviceDependency result = dependencyService.create(dependency);
		verify(jdoTemplate).getObjectById(Device.class, device.getId());
		verify(jdoTemplate).getObjectById(Consumer.class, consumer.getId());
		verify(jdoTemplate).makePersistent(dependency);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(DeviceDependency.class, dependency.getId())).thenReturn(dependency);
		Response result = dependencyService.delete(dependency.getId());
		verify(jdoTemplate).getObjectById(DeviceDependency.class, dependency.getId());
		verify(jdoTemplate).deletePersistent(dependency);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(DeviceDependency.class, dependency.getId())).thenReturn(dependency);
		when(jdoTemplate.makePersistent(dependency)).thenReturn(dependency);
		DeviceDependency result = dependencyService.update(dependency);
		verify(jdoTemplate).getObjectById(DeviceDependency.class, dependency.getId());
		verify(jdoTemplate).makePersistent(dependency);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + DeviceDependency.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(dependencies);
		Collection<DeviceDependency> result = dependencyService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
