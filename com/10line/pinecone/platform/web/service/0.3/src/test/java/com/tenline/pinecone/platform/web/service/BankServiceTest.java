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

import com.tenline.pinecone.platform.model.Bank;
import com.tenline.pinecone.platform.web.service.restful.BankRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"}) 
public class BankServiceTest extends AbstractServiceTest {

	private Bank bank;
	
	private List banks;
	
	private BankRestfulService bankService; 	
	
	@Before
	public void testSetup() {
		bankService = new BankRestfulService(persistenceManagerFactory);
		bankService.setJdoTemplate(jdoTemplate);
		bank = new Bank();
		bank.setId("asa");
		bank.setName("ABC");		
		banks = new ArrayList();
		banks.add(bank);
	}
	
	@After
	public void testShutdown() {	
		bankService = null;
		banks.remove(bank);
		bank = null;
		banks = null;
	}

	@Test
	public void testCreate() {
		when(jdoTemplate.makePersistent(bank)).thenReturn(bank);
		Bank result = bankService.create(bank);
		verify(jdoTemplate).makePersistent(bank);
		assertEquals("ABC", result.getName());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Bank.class, bank.getId())).thenReturn(bank);
		Response result = bankService.delete(bank.getId());
		verify(jdoTemplate).getObjectById(Bank.class, bank.getId());
		verify(jdoTemplate).deletePersistent(bank);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Bank.class, bank.getId())).thenReturn(bank);
		when(jdoTemplate.makePersistent(bank)).thenReturn(bank);
		Bank result = bankService.update(bank);
		verify(jdoTemplate).getObjectById(Bank.class, bank.getId());
		verify(jdoTemplate).makePersistent(bank);
		assertEquals("ABC", result.getName());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + Bank.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(banks);
		Collection<Bank> result = bankService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
