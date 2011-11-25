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

import com.tenline.pinecone.platform.model.ConsumerInstallation;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.service.restful.ConsumerInstallationRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ConsumerInstallationServiceTest extends AbstractServiceTest {
	
	private User user;
	
	private ConsumerInstallation installation;
	
	private List installations;
	
	private ConsumerInstallationRestfulService installationService;

	@Before
	public void testSetup() {
		installationService = new ConsumerInstallationRestfulService(persistenceManagerFactory);
		installationService.setJdoTemplate(jdoTemplate);
		installation = new ConsumerInstallation();
		installation.setId("asa");
		
		user = new User();
		user.setId("asa");
		installation.setUser(user);
		installations = new ArrayList();
		installations.add(installation);
	}
	
	@After
	public void testShutdown() {	
		installationService = null;
		installations.remove(installation);
		user = null;
		installation = null;
		installations = null;
	}
	
	@Test
	public void testCreate() { 
		when(jdoTemplate.getObjectById(User.class, user.getId())).thenReturn(user);
		when(jdoTemplate.makePersistent(installation)).thenReturn(installation);
		ConsumerInstallation result = installationService.create(installation);
		verify(jdoTemplate).getObjectById(User.class, user.getId()); 
		verify(jdoTemplate).makePersistent(installation);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(ConsumerInstallation.class, installation.getId())).thenReturn(installation);
		Response result = installationService.delete(installation.getId());
		verify(jdoTemplate).getObjectById(ConsumerInstallation.class, installation.getId());
		verify(jdoTemplate).deletePersistent(installation);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + ConsumerInstallation.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(installations);
		Collection<ConsumerInstallation> result = installationService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
