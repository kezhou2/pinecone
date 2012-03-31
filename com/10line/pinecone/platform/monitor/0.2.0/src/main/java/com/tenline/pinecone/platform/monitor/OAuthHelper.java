/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import org.apache.log4j.Logger;

import com.google.api.client.auth.oauth.OAuthCallbackUrl;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.sdk.oauth.AuthorizationAPI;

/**
 * @author Bill
 *
 */
public class OAuthHelper {
	
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Web Service API
	 */
	private static AuthorizationAPI authorizationAPI;
	
	/**
	 * OAuth Token and Secret
	 */
	private static String token;
	private static String tokenSecret;
	private static String verifier;
	
	/**
	 * Singleton
	 */
	private static OAuthHelper instance;
	
	/**
	 * 
	 */
	public OAuthHelper() {
		// TODO Auto-generated constructor stub
		authorizationAPI = new AuthorizationAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, IConstants.WEB_SERVICE_CONTEXT);
		APIResponse response = authorizationAPI.requestToken(IConstants.OAUTH_CONSUMER_KEY, IConstants.OAUTH_CONSUMER_SECRET, null);
		if (response.isDone()) {
			token = ((OAuthCredentialsResponse) response.getMessage()).token;
			tokenSecret = ((OAuthCredentialsResponse) response.getMessage()).tokenSecret;
		} else {
			logger.error(response.getMessage());
		}
		response = authorizationAPI.confirmAuthorization(token, "yes");
		if (response.isDone()) {
			token = ((OAuthCallbackUrl) response.getMessage()).token;
			verifier = ((OAuthCallbackUrl) response.getMessage()).verifier;
		} else {
			logger.error(response.getMessage());
		}
		response = authorizationAPI.accessToken(IConstants.OAUTH_CONSUMER_KEY, IConstants.OAUTH_CONSUMER_SECRET, 
				token, tokenSecret, verifier);
		if (response.isDone()) {
			token = ((OAuthCredentialsResponse) response.getMessage()).token;
			tokenSecret = ((OAuthCredentialsResponse) response.getMessage()).tokenSecret;
		} else {
			logger.error(response.getMessage());
		}
	}
	
	/**
	 * Get Instance
	 * @return
	 */
	public static OAuthHelper getInstance() {
		if (instance == null) {
			instance = new OAuthHelper();
		}
		return instance;
	}

	/**
	 * @param token the token to set
	 */
	public static void setToken(String token) {
		OAuthHelper.token = token;
	}

	/**
	 * @return the token
	 */
	public static String getToken() {
		return token;
	}

	/**
	 * @param tokenSecret the tokenSecret to set
	 */
	public static void setTokenSecret(String tokenSecret) {
		OAuthHelper.tokenSecret = tokenSecret;
	}

	/**
	 * @return the tokenSecret
	 */
	public static String getTokenSecret() {
		return tokenSecret;
	}

}
