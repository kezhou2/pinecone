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

import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Transaction;
import com.tenline.pinecone.platform.web.service.restful.TransactionRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class TransactionServiceTest extends AbstractServiceTest {
	
	private Application application;
	
	private Transaction transaction;
	
	private List transactions;
	
	private TransactionRestfulService transactionService;
	
	@Before
	public void testSetup() {
		transactionService = new TransactionRestfulService(persistenceManagerFactory);
		transactionService.setJdoTemplate(jdoTemplate);
		transaction = new Transaction();
		transaction.setId("asa");
		transaction.setNut(20);
		application = new Application();
		application.setId("ccc");
		transaction.setApplication(application);
		transactions = new ArrayList();
		transactions.add(transaction);
	}
	
	@After
	public void testShutdown() {	
		transactionService = null;
		transactions.remove(transaction);
		application = null;
		transaction = null;
		transactions = null;
	}
	
	@Test
	public void testCreate() {
		when(jdoTemplate.getObjectById(Application.class, application.getId())).thenReturn(application);
		when(jdoTemplate.makePersistent(transaction)).thenReturn(transaction);
		Transaction result = transactionService.create(transaction);
		verify(jdoTemplate).getObjectById(Application.class, application.getId());
		verify(jdoTemplate).makePersistent(transaction);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Transaction.class, transaction.getId())).thenReturn(transaction);
		Response result = transactionService.delete(transaction.getId());
		verify(jdoTemplate).getObjectById(Transaction.class, transaction.getId());
		verify(jdoTemplate).deletePersistent(transaction);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Transaction.class, transaction.getId())).thenReturn(transaction);
		when(jdoTemplate.makePersistent(transaction)).thenReturn(transaction);
		Transaction result = transactionService.update(transaction);
		verify(jdoTemplate).getObjectById(Transaction.class, transaction.getId());
		verify(jdoTemplate).makePersistent(transaction);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + Transaction.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(transactions);
		Collection<Transaction> result = transactionService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
