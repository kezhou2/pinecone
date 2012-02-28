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

import com.tenline.pinecone.platform.model.Account;
import com.tenline.pinecone.platform.model.Exchange;
import com.tenline.pinecone.platform.web.service.restful.ExchangeRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ExchangeServiceTest extends AbstractServiceTest {

	private Account account;
	
	private Exchange exchange;
	
	private List exchanges;
	
	private ExchangeRestfulService exchangeService;
	
	@Before
	public void testSetup() {
		exchangeService = new ExchangeRestfulService(persistenceManagerFactory);
		exchangeService.setJdoTemplate(jdoTemplate);
		exchange = new Exchange();
		exchange.setId("asa");
		exchange.setNut(20);
		account = new Account();
		account.setId("ccc");
		exchange.setAccount(account);
		exchanges = new ArrayList();
		exchanges.add(exchange);
	}
	
	@After
	public void testShutdown() {	
		exchangeService = null;
		exchanges.remove(exchange);
		account = null;
		exchange = null;
		exchanges = null;
	}
	
	@Test
	public void testCreate() {
		when(jdoTemplate.getObjectById(Account.class, account.getId())).thenReturn(account);
		when(jdoTemplate.makePersistent(exchange)).thenReturn(exchange);
		Exchange result = exchangeService.create(exchange);
		verify(jdoTemplate).getObjectById(Account.class, account.getId());
		verify(jdoTemplate).makePersistent(exchange);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Exchange.class, exchange.getId())).thenReturn(exchange);
		Response result = exchangeService.delete(exchange.getId());
		verify(jdoTemplate).getObjectById(Exchange.class, exchange.getId());
		verify(jdoTemplate).deletePersistent(exchange);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Exchange.class, exchange.getId())).thenReturn(exchange);
		when(jdoTemplate.makePersistent(exchange)).thenReturn(exchange);
		Exchange result = exchangeService.update(exchange);
		verify(jdoTemplate).getObjectById(Exchange.class, exchange.getId());
		verify(jdoTemplate).makePersistent(exchange);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + Exchange.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(exchanges);
		Collection<Exchange> result = exchangeService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
