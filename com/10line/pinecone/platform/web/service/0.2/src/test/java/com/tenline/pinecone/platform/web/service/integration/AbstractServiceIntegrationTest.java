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
import com.tenline.pinecone.platform.sdk.APIResponse;
import com.tenline.pinecone.platform.sdk.oauth.AuthorizationAPI;
import com.tenline.pinecone.platform.sdk.oauth.OAuthConsumerUrl;

/**
 * @author Bill
 *
 */
public abstract class AbstractServiceIntegrationTest {

	protected Logger logger = Logger.getLogger(getClass().getName());
	
	protected String consumerKey;
	
	protected String token;
	
	protected String tokenSecret;
	
	private String verifier;
	
	protected AuthorizationAPI authorizationAPI;
	
	@Before
	public void testSetup() {
		consumerKey = "12";
		authorizationAPI = new AuthorizationAPI("localhost", "8080");
		APIResponse response = authorizationAPI.registerConsumer(consumerKey, "fishshow", null);
		if (response.isDone()) {
			tokenSecret = ((OAuthConsumerUrl) response.getMessage()).consumerSecret;
			assertNotNull(tokenSecret);
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = authorizationAPI.requestToken(consumerKey, tokenSecret, null);
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
		response = authorizationAPI.accessToken(consumerKey, token, tokenSecret, verifier);
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
		token = null;
		tokenSecret = null;
		verifier = null;
		authorizationAPI = null;
	}

}
