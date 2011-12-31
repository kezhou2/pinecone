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
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.service.restful.ApplicationRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ApplicationServiceTest extends AbstractServiceTest {
	
	private User user;
	
	private Consumer consumer;
	
	private Application application;
	
	private List applications;
	
	private ApplicationRestfulService applicationService;

	@Before
	public void testSetup() {
		applicationService = new ApplicationRestfulService(persistenceManagerFactory);
		applicationService.setJdoTemplate(jdoTemplate);
		application = new Application();
		application.setId("asa");
		application.setDefault(false);
		user = new User();
		user.setId("asa");
		application.setUser(user);
		consumer = new Consumer();
		consumer.setId("ddd");
		application.setConsumer(consumer);
		applications = new ArrayList();
		applications.add(application);
	}
	
	@After
	public void testShutdown() {	
		applicationService = null;
		applications.remove(application);
		user = null;
		consumer = null;
		application = null;
		applications = null;
	}
	
	@Test
	public void testCreate() {
		when(jdoTemplate.getObjectById(User.class, user.getId())).thenReturn(user);
		when(jdoTemplate.getObjectById(Consumer.class, consumer.getId())).thenReturn(consumer);
		when(jdoTemplate.makePersistent(application)).thenReturn(application);
		Application result = applicationService.create(application);
		verify(jdoTemplate).getObjectById(User.class, user.getId()); 
		verify(jdoTemplate).getObjectById(Consumer.class, consumer.getId()); 
		verify(jdoTemplate).makePersistent(application);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Application.class, application.getId())).thenReturn(application);
		Response result = applicationService.delete(application.getId());
		verify(jdoTemplate).getObjectById(Application.class, application.getId());
		verify(jdoTemplate).deletePersistent(application);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Application.class, application.getId())).thenReturn(application);
		when(jdoTemplate.makePersistent(application)).thenReturn(application);
		Application result = applicationService.update(application);
		verify(jdoTemplate).getObjectById(Application.class, application.getId());
		verify(jdoTemplate).makePersistent(application);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + Application.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(applications);
		Collection<Application> result = applicationService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
