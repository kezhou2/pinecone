/**
 * 
 */
package com.tenline.pinecone.platform.sdk.oauth;

import java.io.IOException;

import com.google.api.client.auth.oauth.OAuthAuthorizeTemporaryTokenUrl;
import com.google.api.client.auth.oauth.OAuthCallbackUrl;
import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.http.HttpMethod;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedParser;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.AbstractAPI;

/**
 * @author Bill
 *
 */
public class AuthorizationAPI extends AbstractAPI {
	
	private HttpTransport transport;
	
	private HttpRequestFactory requestFactory;
	
	private OAuthHmacSigner signer;
	
	private OAuthGetTemporaryToken temporaryToken;
	
	private OAuthGetAccessToken accessToken;

	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public AuthorizationAPI(String host, String port, APIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
		url += "/oauth";
		transport = new NetHttpTransport();
		requestFactory = transport.createRequestFactory();
		signer = new OAuthHmacSigner();
		temporaryToken = new OAuthGetTemporaryToken(url + "/requestToken");
		temporaryToken.transport = transport;
		temporaryToken.signer = signer;
		accessToken = new OAuthGetAccessToken(url + "/accessToken");
		accessToken.transport = transport;
		accessToken.signer = signer;
	}
	
	/**
	 * Register Consumer
	 * @param consumerKey
	 */
	public void registerConsumer(String consumerKey) {
	    try {
	    	OAuthConsumerUrl consumerUrl = new OAuthConsumerUrl(url + "/consumer/registration" +
	    			"?oauth_consumer_key=" + consumerKey);
			UrlEncodedParser.parse(requestFactory.buildRequest(HttpMethod.GET, consumerUrl, null)
					.execute().parseAsString(), consumerUrl);
			listener.onMessage(consumerUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			listener.onError(e.getMessage());
		}
	}
	
	/**
	 * Request Temporary Token
	 * @param consumerKey
	 * @param consumerSecret
	 * @param callback
	 */
	public void requestToken(String consumerKey, String consumerSecret, String callback) {
		try {
			temporaryToken.consumerKey = consumerKey;
			signer.clientSharedSecret = consumerSecret;
			temporaryToken.callback = callback;
			listener.onMessage(temporaryToken.execute());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			listener.onError(e.getMessage());
		}
	}
	
	/**
	 * Authorize Temporary Token
	 * @param token
	 */
	public void authorizeToken(String token) {			
	    try {
	    	OAuthAuthorizeTemporaryTokenUrl tokenUrl = new OAuthAuthorizeTemporaryTokenUrl(url + "/authorization" +
					"?oauth_token=" + token);
			listener.onMessage(requestFactory.buildRequest(HttpMethod.GET, tokenUrl, null).execute().parseAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			listener.onError(e.getMessage());
		}
	}
	
	/**
	 * Confirm Authorization
	 * @param token
	 * @param decision
	 */
	public void confirmAuthorization(String token, String decision) {
	    try {
	    	OAuthCallbackUrl callbackUrl = new OAuthCallbackUrl(url + "/authorization/confirm?oauth_token=" + token + 
					"&xoauth_end_user_decision=" + decision);
			UrlEncodedParser.parse(requestFactory.buildRequest(HttpMethod.GET, callbackUrl, null)
					.execute().parseAsString(), callbackUrl);
			listener.onMessage(callbackUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			listener.onError(e.getMessage());
		}
	}
	
	/**
	 * Access Long-Lived Token
	 * @param consumerKey
	 * @param token
	 * @param tokenSecret
	 * @param verifier
	 */
	public void accessToken(String consumerKey, String token, String tokenSecret, String verifier) {
		try {
			accessToken.consumerKey = consumerKey;
			accessToken.temporaryToken = token;
			signer.tokenSharedSecret = tokenSecret;
			accessToken.verifier = verifier;
			listener.onMessage(accessToken.execute());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			listener.onError(e.getMessage());
		}
	}

}
