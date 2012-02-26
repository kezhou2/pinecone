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

import com.tenline.pinecone.platform.model.Bank;
import com.tenline.pinecone.platform.sdk.BankAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class BankServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private Bank bank;
	
	private BankAPI bankAPI;
	
	@Before
	public void testSetup() {
		bank = new Bank();
		bank.setName("ABC");
		bank.setAddress("beijing");
		bankAPI = new BankAPI("localhost", "8888", "service");
	}
	
	@After
	public void testShutdown() {
		bank = null;
		bankAPI = null;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = bankAPI.create(bank);
		if (response.isDone()) {
			bank = (Bank) response.getMessage();
			assertEquals("ABC", bank.getName());
			assertEquals("beijing", bank.getAddress());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		bank.setName("BCIC");
		bank.setAddress("shanghai");
		response = bankAPI.update(bank);
		if (response.isDone()) {
			bank = (Bank) response.getMessage();
			assertEquals("BCIC", bank.getName());
			assertEquals("shanghai", bank.getAddress());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = bankAPI.show("id=='"+bank.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<Bank>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = bankAPI.delete(bank.getId());
		if (response.isDone()) {
			assertEquals("Bank Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = bankAPI.show("id=='"+bank.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Bank>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
