/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.model.Account;
import com.tenline.pinecone.platform.model.Exchange;
import com.tenline.pinecone.platform.web.service.restful.PaymentRestfulService;

/**
 * @author wangyq
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PaymentServiceTest extends AbstractServiceTest {

	private Account account;

	private Exchange exchange;

	private List exchanges;

	private PaymentRestfulService paymentService;

	private Calendar time;

	@Before
	public void testSetup() {
		paymentService = new PaymentRestfulService(persistenceManagerFactory);
		paymentService.setJdoTemplate(jdoTemplate);
		exchange = new Exchange();
		exchange.setId("asa");
		exchange.setNut(20);
		time = Calendar.getInstance();
		exchange.setTimestamp(time.getTime());
		account = new Account();
		account.setId("ccc");
		exchange.setAccount(account);
		exchanges = new ArrayList();
		exchanges.add(exchange);
	}

	@After
	public void testShutdown() {
		paymentService = null;
		exchanges.remove(exchange);
		account = null;
		exchange = null;
		exchanges = null;
	}

	@Test
	public void testCreate() {
		when(jdoTemplate.getObjectById(Account.class, account.getId()))
				.thenReturn(account);
		when(jdoTemplate.makePersistent(exchange)).thenReturn(exchange);
		Exchange result = paymentService.create(exchange);
		verify(jdoTemplate).getObjectById(Account.class, account.getId());
		verify(jdoTemplate).makePersistent(exchange);
		assertEquals("asa", result.getId());
	}

	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Exchange.class, exchange.getId()))
				.thenReturn(exchange);
		Response result = paymentService.delete(exchange.getId());
		verify(jdoTemplate).getObjectById(Exchange.class, exchange.getId());
		verify(jdoTemplate).deletePersistent(exchange);
		assertEquals(200, result.getStatus());
	}

	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Exchange.class, exchange.getId()))
				.thenReturn(exchange);
		when(jdoTemplate.makePersistent(exchange)).thenReturn(exchange);
		Exchange result = paymentService.update(exchange);
		verify(jdoTemplate).getObjectById(Exchange.class, exchange.getId());
		verify(jdoTemplate).makePersistent(exchange);
		assertEquals("asa", result.getId());
	}

	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + Exchange.class.getName()
				+ " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(exchanges);
		Collection<Exchange> result = paymentService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

	@Test
	public void testShowByDate() {
		Calendar from = (Calendar) time.clone();
		from.add(Calendar.MINUTE, -10);
		Calendar to = (Calendar) time.clone();
		to.add(Calendar.MINUTE, 10);
		String queryString = "select from " + Exchange.class.getName()
				+ " where timestamp >= " + from.getTime() + " and timestamp<= " + to.getTime();
		when(jdoTemplate.find(queryString)).thenReturn(exchanges);
		Collection<Exchange> result = paymentService.show(from.getTime(),
				to.getTime());
		assertEquals(1, result.size());
	}

	@Test
	public void testShowByAfter() {
		Calendar from = (Calendar) time.clone();
		from.add(Calendar.MINUTE, 1);
		Calendar to = (Calendar) time.clone();
		to.add(Calendar.MINUTE, 10);
		Collection<Exchange> result = paymentService.show(from.getTime(),
				to.getTime());
		assertEquals(0, result.size());
	}

	@Test
	public void testShowByBefore() {
		Calendar from = (Calendar) time.clone();
		from.add(Calendar.MINUTE, -10);
		Calendar to = (Calendar) time.clone();
		to.add(Calendar.MINUTE, -1);
		Collection<Exchange> result = paymentService.show(from.getTime(),
				to.getTime());
		assertEquals(0, result.size());
	}
}
