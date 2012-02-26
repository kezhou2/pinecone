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
import com.tenline.pinecone.platform.model.Bank;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.service.restful.AccountRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class AccountServiceTest extends AbstractServiceTest {

	private User user;
	
	private Bank bank;
	
	private Account account;
	
	private List accounts;
	
	private AccountRestfulService accountService;

	@Before
	public void testSetup() {
		accountService = new AccountRestfulService(persistenceManagerFactory);
		accountService.setJdoTemplate(jdoTemplate);
		account = new Account();
		account.setId("asa");
		account.setNumber("3515151");
		user = new User();
		user.setId("asa");
		account.setUser(user);
		bank = new Bank();
		bank.setId("ddd");
		account.setBank(bank);
		accounts = new ArrayList();
		accounts.add(account);
	}
	
	@After
	public void testShutdown() {	
		accountService = null;
		accounts.remove(account);
		user = null;
		bank = null;
		account = null;
		accounts = null;
	}
	
	@Test
	public void testCreate() {
		when(jdoTemplate.getObjectById(User.class, user.getId())).thenReturn(user);
		when(jdoTemplate.getObjectById(Bank.class, bank.getId())).thenReturn(bank);
		when(jdoTemplate.makePersistent(account)).thenReturn(account);
		Account result = accountService.create(account);
		verify(jdoTemplate).getObjectById(User.class, user.getId()); 
		verify(jdoTemplate).getObjectById(Bank.class, bank.getId()); 
		verify(jdoTemplate).makePersistent(account);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Account.class, account.getId())).thenReturn(account);
		Response result = accountService.delete(account.getId());
		verify(jdoTemplate).getObjectById(Account.class, account.getId());
		verify(jdoTemplate).deletePersistent(account);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Account.class, account.getId())).thenReturn(account);
		when(jdoTemplate.makePersistent(account)).thenReturn(account);
		Account result = accountService.update(account);
		verify(jdoTemplate).getObjectById(Account.class, account.getId());
		verify(jdoTemplate).makePersistent(account);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + Account.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(accounts);
		Collection<Account> result = accountService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
