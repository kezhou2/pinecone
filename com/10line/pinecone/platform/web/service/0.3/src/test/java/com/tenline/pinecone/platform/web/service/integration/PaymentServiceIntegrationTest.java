/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.model.Account;
import com.tenline.pinecone.platform.model.Bank;
import com.tenline.pinecone.platform.model.Exchange;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.AccountAPI;
import com.tenline.pinecone.platform.sdk.BankAPI;
import com.tenline.pinecone.platform.sdk.ExchangeAPI;
import com.tenline.pinecone.platform.sdk.PaymentAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class PaymentServiceIntegrationTest extends AbstractServiceIntegrationTest {
	
	private User user;
	
	private Bank bank;
	
	private Account account;
	
	private Exchange exchange;
	
	private UserAPI userAPI;
	
	private AccountAPI accountAPI;
	
	private BankAPI bankAPI;
	
	private ExchangeAPI exchangeAPI;
	
	private PaymentAPI paymentAPI;
	
	private Date timestamp = new Date(System.currentTimeMillis());
	
	private static final long A_DAY = 1000 * 60 * 60 * 24;

	@Before
	public void testSetup() throws Exception {
		user = new User();
		user.setName("251417324");
		bank = new Bank();
		bank.setName("App");
		account = new Account();
		account.setNumber("2323");
		exchange = new Exchange();
		exchange.setType(Exchange.PAYOUT);
		exchange.setTimestamp(timestamp);
		exchange.setNut(20);
		userAPI = new UserAPI("localhost", "8888", "service");
		accountAPI = new AccountAPI("localhost", "8888", "service");
		bankAPI = new BankAPI("localhost", "8888", "service");
		exchangeAPI = new ExchangeAPI("localhost", "8888", "service");
		paymentAPI = new PaymentAPI("localhost", "8888", "service");
		APIResponse response = userAPI.create(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("251417324", user.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = bankAPI.create(bank);
		if (response.isDone()) {
			bank = (Bank) response.getMessage();
			assertEquals("App", bank.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		account.setBank(bank);
		account.setUser(user);
		response = accountAPI.create(account);
		if (response.isDone()) {
			account = (Account) response.getMessage();
			assertEquals("2323", account.getNumber());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		exchange.setAccount(account);
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
		account = null;
		bank = null;
		exchange = null;
		userAPI = null;
		accountAPI = null;
		bankAPI = null;
		exchangeAPI = null;
		paymentAPI = null;
	}
	
	@Test
	public void testGenerateBatch() throws Exception {
		APIResponse response = exchangeAPI.create(exchange);
		if (response.isDone()) {
			exchange = (Exchange) response.getMessage();
			assertEquals(Exchange.PAYOUT, exchange.getType());
			assertEquals(timestamp, exchange.getTimestamp());
			assertEquals(Integer.valueOf(20), exchange.getNut());
			assertEquals("251417324", exchange.getAccount().getUser().getName());
			assertEquals("App", exchange.getAccount().getBank().getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = paymentAPI.generateBatch(new Date(timestamp.getTime() - A_DAY), 
				new Date(timestamp.getTime() + A_DAY));
		if (response.isDone()) {
			assertNotNull(response.getMessage());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
