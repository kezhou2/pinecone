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
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.web.service.restful.VariableRestfulService;

/**
 * @author Bill
 *
 */

@SuppressWarnings({"rawtypes", "unchecked"})
public class VariableServiceTest extends AbstractServiceTest {

	private Device device;
	
	private Variable variable;
	
	private List variables;
	
	private VariableRestfulService variableService;
	
	@Before
	public void testSetup() {
		variableService = new VariableRestfulService(persistenceManagerFactory);
		variableService.setJdoTemplate(jdoTemplate);
		variable = new Variable();
		variable.setId("asa");
		variable.setName("IF Output");
		device = new Device();
		device.setId("asa");
		variable.setDevice(device);
		variables = new ArrayList();
		variables.add(variable);
	}
	
	@After
	public void testShutdown() {
		variableService = null;
		variables.remove(variable);
		device = null;
		variable = null;
		variables = null;
	}
	
	@Test
	public void testCreate() {
		when(jdoTemplate.getObjectById(Device.class, device.getId())).thenReturn(device);
		when(jdoTemplate.makePersistent(variable)).thenReturn(variable);
		Variable result = variableService.create(variable);
		verify(jdoTemplate).getObjectById(Device.class, device.getId());
		verify(jdoTemplate).makePersistent(variable);
		assertEquals("IF Output", result.getName());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Variable.class, variable.getId())).thenReturn(variable);
		Response result = variableService.delete(variable.getId());
		verify(jdoTemplate).deletePersistent(variable);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Variable.class, variable.getId())).thenReturn(variable);
		when(jdoTemplate.makePersistent(variable)).thenReturn(variable);
		Variable result = variableService.update(variable);
		verify(jdoTemplate).getObjectById(Variable.class, variable.getId());
		verify(jdoTemplate).makePersistent(variable);
		assertEquals("IF Output", result.getName());
	}
	
	@Test
	public void testShow() {
		String filter = "name=='IF Output'";
		String queryString = "select from " + Variable.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(variables);
		Collection<Variable> result = variableService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
