/**
 * 
 */
package com.tenline.pinecone.rest;

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
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tenline.pinecone.model.Variable;
import com.tenline.pinecone.persistence.VariableDao;
import com.tenline.pinecone.rest.impl.VariableServiceImpl;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@RunWith(MockitoJUnitRunner.class)
public class VariableServiceTest {

	private Variable variable;
	
	private List variables;
	
	private VariableServiceImpl variableService;
	
	@Mock
	private VariableDao variableDao;
	
	@Before
	public void testSetup() {
		variableService = new VariableServiceImpl(variableDao);
		variable = new Variable();
		variable.setId("asa");
		variable.setName("IF Output");
		variables = new ArrayList();
		variables.add(variable);
	}
	
	@After
	public void testShutdown() {
		variableService = null;
		variableDao = null;
		variables.remove(variable);
		variable = null;
		variables = null;
	}
	
	@Test
	public void testCreate() {
		Response result = variableService.create(variable);
		ArgumentCaptor<Variable> argument = ArgumentCaptor.forClass(Variable.class);  
		verify(variableDao).save(argument.capture()); 
		assertEquals("IF Output", argument.getValue().getName());
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testDelete() {
		Response result = variableService.delete(variable.getId());
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);  
		verify(variableDao).delete(argument.capture());
		assertEquals("asa", argument.getValue());
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		Response result = variableService.update(variable);
		ArgumentCaptor<Variable> argument = ArgumentCaptor.forClass(Variable.class);  
		verify(variableDao).update(argument.capture());
		assertEquals("IF Output", argument.getValue().getName());
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testShow() {
		String filter = "name=='IF Output'";
		when(variableDao.find(filter)).thenReturn(variables);
		Collection<Variable> result = variableService.show(filter);
		verify(variableDao).find(filter);
		assertEquals(1, result.size());
	}

}
