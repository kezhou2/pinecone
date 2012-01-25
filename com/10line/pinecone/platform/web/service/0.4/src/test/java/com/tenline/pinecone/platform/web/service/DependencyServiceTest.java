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
import com.tenline.pinecone.platform.model.Dependency;
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.web.service.restful.DependencyRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DependencyServiceTest extends AbstractServiceTest {

	private Driver driver;
	
	private Consumer consumer;
	
	private Dependency dependency;
	
	private List dependencies;
	
	private DependencyRestfulService dependencyService;
	
	@Before
	public void testSetup() {
		dependencyService = new DependencyRestfulService(persistenceManagerFactory);
		dependencyService.setJdoTemplate(jdoTemplate);
		dependency = new Dependency();
		dependency.setId("asa");
		dependency.setOptional(false);
		consumer = new Consumer();
		consumer.setId("ddd");
		dependency.setConsumer(consumer);
		driver = new Driver();
		driver.setId("ccc");
		dependency.setDriver(driver);
		dependencies = new ArrayList();
		dependencies.add(dependency);
	}
	
	@After
	public void testShutdown() {	
		dependencyService = null;
		dependencies.remove(dependency);
		driver = null;
		consumer = null;
		dependency = null;
		dependencies = null;
	}
	
	@Test
	public void testCreate() {
		when(jdoTemplate.getObjectById(Driver.class, driver.getId())).thenReturn(driver);
		when(jdoTemplate.getObjectById(Consumer.class, consumer.getId())).thenReturn(consumer);
		when(jdoTemplate.makePersistent(dependency)).thenReturn(dependency);
		Dependency result = dependencyService.create(dependency);
		verify(jdoTemplate).getObjectById(Driver.class, driver.getId());
		verify(jdoTemplate).getObjectById(Consumer.class, consumer.getId());
		verify(jdoTemplate).makePersistent(dependency);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Dependency.class, dependency.getId())).thenReturn(dependency);
		Response result = dependencyService.delete(dependency.getId());
		verify(jdoTemplate).getObjectById(Dependency.class, dependency.getId());
		verify(jdoTemplate).deletePersistent(dependency);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Dependency.class, dependency.getId())).thenReturn(dependency);
		when(jdoTemplate.makePersistent(dependency)).thenReturn(dependency);
		Dependency result = dependencyService.update(dependency);
		verify(jdoTemplate).getObjectById(Dependency.class, dependency.getId());
		verify(jdoTemplate).makePersistent(dependency);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + Dependency.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(dependencies);
		Collection<Dependency> result = dependencyService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
