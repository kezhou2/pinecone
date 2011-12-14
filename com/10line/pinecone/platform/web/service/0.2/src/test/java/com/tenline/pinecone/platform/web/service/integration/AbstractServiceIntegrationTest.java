/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertNotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;

import com.google.api.client.auth.oauth.OAuthCallbackUrl;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.sdk.oauth.AuthorizationAPI;

/**
 * @author Bill
 *
 */
public abstract class AbstractServiceIntegrationTest {

	protected Logger logger = Logger.getLogger(getClass().getName());
	
	protected String consumerKey;
	
	protected String consumerSecret;
	
	protected String token;
	
	protected String tokenSecret;
	
	private String verifier;
	
	private Consumer consumer;
	
	private ConsumerAPI consumerAPI;
	
	private AuthorizationAPI authorizationAPI;
	
	@Before
	public void testSetup() {
		consumer = new Consumer();
		consumer.setConnectURI("123");
		consumer.setDisplayName("fishshow");
		consumerAPI = new ConsumerAPI("localhost", "8888", null);
		APIResponse response = null;
		try {
			response = consumerAPI.create(consumer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (response.isDone()) {
			consumerKey = ((Consumer) response.getMessage()).getKey();
			consumerSecret = ((Consumer) response.getMessage()).getSecret();
			assertNotNull(consumerKey);
			assertNotNull(consumerSecret);
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		authorizationAPI = new AuthorizationAPI("localhost", "8888", null);
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
	public void testShutdown() {
		consumerKey = null;
		consumerSecret = null;
		token = null;
		tokenSecret = null;
		verifier = null;
		consumer = null;
		consumerAPI = null;
		authorizationAPI = null;
	}

}
