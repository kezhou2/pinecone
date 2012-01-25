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

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.web.service.restful.DriverRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"}) 
public class DriverServiceTest extends AbstractServiceTest {

	private Driver driver;
	
	private Category category;
	
	private List drivers;
	
	private DriverRestfulService driverService;
	
	@Before
	public void testSetup() {
		driverService = new DriverRestfulService(persistenceManagerFactory);
		driverService.setJdoTemplate(jdoTemplate);
		category = new Category();
		category.setId("bbb");
		driver = new Driver();
		driver.setId("asa");
		driver.setName("test");		
		driver.setCategory(category);
		drivers = new ArrayList();
		drivers.add(driver);
	}
	
	@After
	public void testShutdown() {	
		driverService = null;
		drivers.remove(driver);
		driver = null;
		category = null;
		drivers = null;
	}
	
	@Test
	public void testCreate() {
		when(jdoTemplate.getObjectById(Category.class, category.getId())).thenReturn(category);
		when(jdoTemplate.makePersistent(driver)).thenReturn(driver);
		Driver result = driverService.create(driver);
		verify(jdoTemplate).getObjectById(Category.class, category.getId());
		verify(jdoTemplate).makePersistent(driver);
		assertEquals("test", result.getName());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Driver.class, driver.getId())).thenReturn(driver);
		Response result = driverService.delete(driver.getId());
		verify(jdoTemplate).getObjectById(Driver.class, driver.getId());
		verify(jdoTemplate).deletePersistent(driver);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Driver.class, driver.getId())).thenReturn(driver);
		when(jdoTemplate.makePersistent(driver)).thenReturn(driver);
		Driver result = driverService.update(driver);
		verify(jdoTemplate).getObjectById(Driver.class, driver.getId());
		verify(jdoTemplate).makePersistent(driver);
		assertEquals("test", result.getName());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + Driver.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(drivers);
		Collection<Driver> result = driverService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
