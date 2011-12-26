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

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.DeviceInstallation;
import com.tenline.pinecone.platform.web.service.restful.DeviceInstallationRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DeviceInstallationServiceTest extends AbstractServiceTest {

	private Device device;
	
	private User user;
	
	private DeviceInstallation installation;
	
	private List installations;
	
	private DeviceInstallationRestfulService installationService;
	
	@Before
	public void testSetup() {
		installationService = new DeviceInstallationRestfulService(persistenceManagerFactory);
		installationService.setJdoTemplate(jdoTemplate);
		installation = new DeviceInstallation();
		installation.setId("asa");
		installation.setDefault(false);
		installation.setStatus(DeviceInstallation.CLOSED);
		user = new User();
		user.setId("ddd");
		installation.setUser(user);
		device = new Device();
		device.setId("ccc");
		installation.setDevice(device);
		installations = new ArrayList();
		installations.add(installation);
	}
	
	@After
	public void testShutdown() {	
		installationService = null;
		installations.remove(installation);
		device = null;
		user = null;
		installation = null;
		installations = null;
	}
	
	@Test
	public void testCreate() {
		when(jdoTemplate.getObjectById(Device.class, device.getId())).thenReturn(device);
		when(jdoTemplate.getObjectById(User.class, user.getId())).thenReturn(user);
		when(jdoTemplate.makePersistent(installation)).thenReturn(installation);
		DeviceInstallation result = installationService.create(installation);
		verify(jdoTemplate).getObjectById(Device.class, device.getId());
		verify(jdoTemplate).getObjectById(User.class, user.getId());
		verify(jdoTemplate).makePersistent(installation);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(DeviceInstallation.class, installation.getId())).thenReturn(installation);
		Response result = installationService.delete(installation.getId());
		verify(jdoTemplate).getObjectById(DeviceInstallation.class, installation.getId());
		verify(jdoTemplate).deletePersistent(installation);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(DeviceInstallation.class, installation.getId())).thenReturn(installation);
		when(jdoTemplate.makePersistent(installation)).thenReturn(installation);
		DeviceInstallation result = installationService.update(installation);
		verify(jdoTemplate).getObjectById(DeviceInstallation.class, installation.getId());
		verify(jdoTemplate).makePersistent(installation);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + DeviceInstallation.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(installations);
		Collection<DeviceInstallation> result = installationService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
