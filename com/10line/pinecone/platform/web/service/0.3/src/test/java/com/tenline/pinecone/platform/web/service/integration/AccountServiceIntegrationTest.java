/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.model.Account;
import com.tenline.pinecone.platform.model.Bank;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.AccountAPI;
import com.tenline.pinecone.platform.sdk.BankAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class AccountServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private Bank bank;
	
	private User user;
	
	private Account account;
	
	private BankAPI bankAPI;
	
	private UserAPI userAPI;
	
	private AccountAPI accountAPI;
	
	@Before
	public void testSetup() throws Exception {
		user = new User();
		user.setName("bill");
		bank = new Bank();
		bank.setName("ABC");
		account = new Account();
		account.setNumber("2323");
		userAPI = new UserAPI("localhost", "8888", "service");
		bankAPI = new BankAPI("localhost", "8888", "service");
		accountAPI = new AccountAPI("localhost", "8888", "service");
		APIResponse response = userAPI.create(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("bill", user.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = bankAPI.create(bank);
		if (response.isDone()) {
			bank = (Bank) response.getMessage();
			assertEquals("ABC", bank.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		account.setUser(user);
		account.setBank(bank);
	}
	
	@After
	public void testShutdown() throws Exception {
		APIResponse response = userAPI.delete(user.getId());
		if (response.isDone()) {
			assertEquals("User Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = bankAPI.delete(bank.getId());
		if (response.isDone()) {
			assertEquals("Bank Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		user = null;
		bank = null;
		account = null;
		userAPI = null;
		bankAPI = null;
		accountAPI = null;
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = accountAPI.create(account);
		if (response.isDone()) {
			account = (Account) response.getMessage();
			assertEquals("2323", account.getNumber());
			assertEquals("bill", account.getUser().getName());
			assertEquals("ABC", account.getBank().getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		account.setNumber("2424");
		response = accountAPI.update(account);
		if (response.isDone()) {
			account = (Account) response.getMessage();
			assertEquals("2424", account.getNumber());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = accountAPI.show("id=='"+account.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<Account>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = accountAPI.delete(account.getId());
		if (response.isDone()) {
			assertEquals("Account Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = accountAPI.showByUser("id=='"+user.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Account>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = accountAPI.showByBank("id=='"+bank.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Account>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
