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
import com.tenline.pinecone.platform.model.Transaction;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.service.restful.TransactionRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class TransactionServiceTest extends AbstractServiceTest {

	private User user;
	
	private Consumer consumer;
	
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
		consumer = new Consumer();
		consumer.setId("ddd");
		transaction.setConsumer(consumer);
		user = new User();
		user.setId("ccc");
		transaction.setUser(user);
		transactions = new ArrayList();
		transactions.add(transaction);
	}
	
	@After
	public void testShutdown() {	
		transactionService = null;
		transactions.remove(transaction);
		user = null;
		consumer = null;
		transaction = null;
		transactions = null;
	}
	
	@Test
	public void testCreate() {
		when(jdoTemplate.getObjectById(User.class, user.getId())).thenReturn(user);
		when(jdoTemplate.getObjectById(Consumer.class, consumer.getId())).thenReturn(consumer);
		when(jdoTemplate.makePersistent(transaction)).thenReturn(transaction);
		Transaction result = transactionService.create(transaction);
		verify(jdoTemplate).getObjectById(User.class, user.getId());
		verify(jdoTemplate).getObjectById(Consumer.class, consumer.getId());
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
