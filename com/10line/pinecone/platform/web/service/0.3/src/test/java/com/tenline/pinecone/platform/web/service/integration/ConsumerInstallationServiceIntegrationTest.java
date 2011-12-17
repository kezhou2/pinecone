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

import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.ConsumerInstallation;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.ConsumerInstallationAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class ConsumerInstallationServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private Consumer consumer;
	
	private User user;
	
	private ConsumerInstallation installation;
	
	private ConsumerAPI consumerAPI;
	
	private UserAPI userAPI;
	
	private ConsumerInstallationAPI installationAPI;
	
	@Before
	public void testSetup() throws Exception {
		user = new User();
		user.setName("bill");
		consumer = new Consumer();
		consumer.setDisplayName("App");
		installation = new ConsumerInstallation();
		installation.setDefault(false);
		userAPI = new UserAPI("localhost", "8888", "service");
		consumerAPI = new ConsumerAPI("localhost", "8888", "service");
		installationAPI = new ConsumerInstallationAPI("localhost", "8888", "service");
		APIResponse response = userAPI.create(user);
		if (response.isDone()) {
			user = (User) response.getMessage();
			assertEquals("bill", user.getName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = consumerAPI.create(consumer);
		if (response.isDone()) {
			consumer = (Consumer) response.getMessage();
			assertEquals("App", consumer.getDisplayName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		installation.setUser(user);
		installation.setConsumer(consumer);
	}
	
	@After
	@SuppressWarnings("unchecked")
	public void testShutdown() throws Exception {
		APIResponse response = userAPI.delete(user.getId());
		if (response.isDone()) {
			assertEquals("User Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = consumerAPI.show("all");
		if (response.isDone()) {
			Collection<Consumer> consumers = (Collection<Consumer>) response.getMessage();
			for (Consumer consumer : consumers) {
				response = consumerAPI.delete(consumer.getId());
				if (response.isDone()) {
					assertEquals("Consumer Deleted!", response.getMessage().toString());
				} else {
					logger.log(Level.SEVERE, response.getMessage().toString());
				}
			}
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		user = null;
		consumer = null;
		installation = null;
		userAPI = null;
		consumerAPI = null;
		installationAPI = null;
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = installationAPI.create(installation);
		if (response.isDone()) {
			installation = (ConsumerInstallation) response.getMessage();
			assertEquals(false, installation.isDefault());
			assertEquals("bill", installation.getUser().getName());
			assertEquals("App", installation.getConsumer().getDisplayName());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		installation.setDefault(true);
		response = installationAPI.update(installation);
		if (response.isDone()) {
			installation = (ConsumerInstallation) response.getMessage();
			assertEquals(true, installation.isDefault());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = installationAPI.show("id=='"+installation.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<ConsumerInstallation>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = installationAPI.delete(installation.getId());
		if (response.isDone()) {
			assertEquals("Consumer Installation Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = installationAPI.showByUser("id=='"+user.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<ConsumerInstallation>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
