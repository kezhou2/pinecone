/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Transaction;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.CategoryAPI;
import com.tenline.pinecone.platform.sdk.TransactionAPI;
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class TransactionServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private User user;
	
	private Category category;
	
	private Consumer consumer;
	
	private Transaction transaction;
	
	private UserAPI userAPI;
	
	private CategoryAPI categoryAPI;
	
	private ConsumerAPI consumerAPI;
	
	private TransactionAPI transactionAPI;
	
	private Date timestamp = new Date(System.currentTimeMillis());
	
	@Before
	public void testSetup() throws Exception {
		user = new User();
		user.setName("251417324");
		category = new Category();
		category.setType(Category.COM);
		consumer = new Consumer();
		consumer.setName("App");
		transaction = new Transaction();
		transaction.setType(Transaction.INCOME);
		transaction.setTimestamp(timestamp);
		transaction.setNut(20);
		userAPI = new UserAPI("localhost", "8888", "service");
		categoryAPI = new CategoryAPI("localhost", "8888", "service");
		consumerAPI = new ConsumerAPI("localhost", "8888", "service");
		transactionAPI = new TransactionAPI("localhost", "8888", "service");
		APIResponse response = userAPI.create(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("251417324", user.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = categoryAPI.create(category);
		if (response.isDone()) {
			category = (Category) response.getMessage();
			assertEquals(Category.COM, category.getType());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		consumer.setCategory(category);
		response = consumerAPI.create(consumer);
		if (response.isDone()) {
			consumer = (Consumer) response.getMessage();
			assertEquals("App", consumer.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		transaction.setConsumer(consumer);
		transaction.setUser(user);
	}
	
	@After
	public void testShutdown() throws Exception {
		APIResponse response = userAPI.delete(user.getId());
		if (response.isDone()) {
			assertEquals("User Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = categoryAPI.delete(category.getId());
		if (response.isDone()) {
			assertEquals("Category Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		user = null;
		category = null;
		consumer = null;
		transaction = null;
		userAPI = null;
		categoryAPI = null;
		consumerAPI = null;
		transactionAPI = null;
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = transactionAPI.create(transaction);
		if (response.isDone()) {
			transaction = (Transaction) response.getMessage();
			assertEquals(Transaction.INCOME, transaction.getType());
			assertEquals(timestamp, transaction.getTimestamp());
			assertEquals(Integer.valueOf(20), transaction.getNut());
			assertEquals("251417324", transaction.getUser().getName());
			assertEquals("App", transaction.getConsumer().getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		transaction.setType(Transaction.PAYOUT);
		timestamp = new Date(System.currentTimeMillis());
		transaction.setTimestamp(timestamp);
		transaction.setNut(30);
		response = transactionAPI.update(transaction);
		if (response.isDone()) {
			transaction = (Transaction) response.getMessage();
			assertEquals(Transaction.PAYOUT, transaction.getType());
			assertEquals(timestamp, transaction.getTimestamp());
			assertEquals(Integer.valueOf(30), transaction.getNut());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = transactionAPI.show("id=='"+transaction.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<Transaction>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = transactionAPI.delete(transaction.getId());
		if (response.isDone()) {
			assertEquals("Transaction Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = transactionAPI.showByUser("id=='"+user.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Transaction>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = transactionAPI.showByConsumer("id=='"+consumer.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Transaction>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}
	
}
