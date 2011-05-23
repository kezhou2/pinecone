/**
 * 
 */
package com.tenline.pinecone.service;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.model.Record;
import com.tenline.pinecone.model.Variable;
import com.tenline.pinecone.service.restful.RecordRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RecordServiceTest extends AbstractServiceTest {
	
	private Variable variable;
	
	private Record record;
	
	private List records;
	
	private RecordRestfulService recordService;
	
	@Before
	public void testSetup() {
		recordService = new RecordRestfulService(persistenceManagerFactory);
		recordService.setJdoTemplate(jdoTemplate);
		record = new Record();
		record.setId("asa");
		record.setValue("1");
		record.setTimestamp(new Date());
		variable = new Variable();
		variable.setId("asa");
		record.setVariable(variable);
		records = new ArrayList();
		records.add(record);
	}
	
	@After
	public void testShutdown() {	
		recordService = null;
		records.remove(record);
		variable = null;
		record = null;
		records = null;
	}

	@Test
	public void testCreate() {
		when(jdoTemplate.getObjectById(Variable.class, variable.getId())).thenReturn(variable);
		when(jdoTemplate.makePersistent(record)).thenReturn(record);
		Record result = recordService.create(record);
		verify(jdoTemplate).getObjectById(Variable.class, variable.getId());
		verify(jdoTemplate).makePersistent(record);
		assertEquals("1", result.getValue());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Record.class, record.getId())).thenReturn(record);
		Response result = recordService.delete(record.getId());
		verify(jdoTemplate).deletePersistent(record);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Record.class, record.getId())).thenReturn(record);
		when(jdoTemplate.makePersistent(record)).thenReturn(record);
		Record result = recordService.update(record);
		verify(jdoTemplate).getObjectById(Record.class, record.getId());
		verify(jdoTemplate).makePersistent(record);
		assertEquals("1", result.getValue());
	}
	
	@Test
	public void testShow() {
		String filter = "value=='1'";
		String queryString = "select from " + Record.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(records);
		Collection<Record> result = recordService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
