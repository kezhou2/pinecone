/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;

import com.google.api.client.auth.oauth.OAuthCallbackUrl;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.sdk.CategoryAPI;
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.sdk.oauth.AuthorizationAPI;

/**
 * @author Bill
 *
 */
public abstract class AuthorizationServiceIntegrationTest extends AbstractServiceIntegrationTest {
	
	protected String consumerKey;
	
	protected String consumerSecret;
	
	protected String token;
	
	protected String tokenSecret;
	
	private String verifier;
	
	private Category category;
	
	private Consumer consumer;
	
	private CategoryAPI categoryAPI;
	
	private ConsumerAPI consumerAPI;
	
	private AuthorizationAPI authorizationAPI;
	
	@Before
	public void testSetup() throws Exception {
		category = new Category();
		category.setType(Category.COM);
		consumer = new Consumer();
		consumer.setConnectURI("123");
		consumer.setName("fishshow");
		categoryAPI = new CategoryAPI("localhost", "8888", "service");
		consumerAPI = new ConsumerAPI("localhost", "8888", "service");
		APIResponse response = categoryAPI.create(category);
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
			consumerKey = ((Consumer) response.getMessage()).getKey();
			consumerSecret = ((Consumer) response.getMessage()).getSecret();
			assertNotNull(consumerKey);
			assertNotNull(consumerSecret);
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		authorizationAPI = new AuthorizationAPI("localhost", "8888", "service");
		response = authorizationAPI.requestToken(consumerKey, consumerSecret, null);
		if (response.isDone()) {
			token = ((OAuthCredentialsResponse) response.getMessage()).token;
			tokenSecret = ((OAuthCredentialsResponse) response.getMessage()).tokenSecret;
			assertNotNull(token);
			assertNotNull(tokenSecret);
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = authorizationAPI.authorizeToken(token);
		if (response.isDone()) {
			assertNotNull(response.getMessage());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = authorizationAPI.confirmAuthorization(token, "yes");
		if (response.isDone()) {
			token = ((OAuthCallbackUrl) response.getMessage()).token;
			verifier = ((OAuthCallbackUrl) response.getMessage()).verifier;
			assertNotNull(verifier);
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = authorizationAPI.accessToken(consumerKey, consumerSecret, token, tokenSecret, verifier);
		if (response.isDone()) {
			token = ((OAuthCredentialsResponse) response.getMessage()).token;
			tokenSecret = ((OAuthCredentialsResponse) response.getMessage()).tokenSecret;
			assertNotNull(token);
			assertNotNull(tokenSecret);
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}
	
	@After
	public void testShutdown() throws Exception {
		APIResponse response = categoryAPI.delete(category.getId());
		if (response.isDone()) {
			assertEquals("Category Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		consumerKey = null;
		consumerSecret = null;
		token = null;
		tokenSecret = null;
		verifier = null;
		category = null;
		consumer = null;
		categoryAPI = null;
		consumerAPI = null;
		authorizationAPI = null;
	}

}
