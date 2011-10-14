/**
 * 
 */
package com.tenline.pinecone.platform.sdk.oauth;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth.OAuthAuthorizeTemporaryTokenUrl;
import com.google.api.client.auth.oauth.OAuthCallbackUrl;
import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpMethod;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedParser;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.tenline.pinecone.platform.sdk.APIResponse;
import com.tenline.pinecone.platform.sdk.AbstractAPI;

/**
 * @author Bill
 *
 */
public class AuthorizationAPI extends AbstractAPI {
	
	private HttpTransport transport;
	
	private HttpRequestFactory requestFactory;
	
	private OAuthHmacSigner signer;

	/**
	 * @param host
	 * @param port
	 */
	public AuthorizationAPI(String host, String port) {
		super(host, port);
		// TODO Auto-generated constructor stub
		url += "/oauth";
		transport = new NetHttpTransport();
		requestFactory = transport.createRequestFactory();
		signer = new OAuthHmacSigner();
	}
	
	/**
	 * Register Consumer
	 * @param consumerKey
	 * @param displayName
	 * @param connectUrl
	 * @return
	 */
	public APIResponse registerConsumer(String consumerKey, String displayName, String connectUrl) {
		APIResponse response = new APIResponse();
	    try {
	    	OAuthConsumerUrl consumerUrl = new OAuthConsumerUrl(url + "/consumer/registration" +
	    			"?oauth_consumer_key=" + consumerKey + "&xoauth_consumer_display_name=" + displayName +
	    			"&xoauth_consumer_connect_uri=" + connectUrl);
			UrlEncodedParser.parse(requestFactory.buildRequest(HttpMethod.GET, consumerUrl, null)
					.execute().parseAsString(), consumerUrl);
			response.setDone(true);
			response.setMessage(consumerUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			response.setDone(false);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Request Temporary Token
	 * @param consumerKey
	 * @param consumerSecret
	 * @param callback
	 * @return
	 */
	public APIResponse requestToken(String consumerKey, String consumerSecret, String callback) {
		APIResponse response = new APIResponse();
		try {
			OAuthGetTemporaryToken temporaryToken = new OAuthGetTemporaryToken(url + "/requestToken");
			temporaryToken.transport = transport;
			temporaryToken.signer = signer;
			temporaryToken.consumerKey = consumerKey;
			signer.clientSharedSecret = consumerSecret;
			temporaryToken.callback = callback;
			response.setDone(true);
			response.setMessage(temporaryToken.execute());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			response.setDone(false);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Authorize Temporary Token
	 * @param token
	 * @return
	 */
	public APIResponse authorizeToken(String token) {	
		APIResponse response = new APIResponse();
	    try {
	    	OAuthAuthorizeTemporaryTokenUrl tokenUrl = new OAuthAuthorizeTemporaryTokenUrl(url + "/authorization" +
					"?oauth_token=" + token);
			response.setDone(true);
	    	response.setMessage(requestFactory.buildRequest(HttpMethod.GET, tokenUrl, null).execute().parseAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			response.setDone(false);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Confirm Authorization
	 * @param token
	 * @param decision
	 * @return
	 */
	public APIResponse confirmAuthorization(String token, String decision) {
		APIResponse response = new APIResponse();
	    try {
	    	OAuthCallbackUrl callbackUrl = new OAuthCallbackUrl(url + "/authorization/confirm?oauth_token=" + token + 
					"&xoauth_end_user_decision=" + decision);
			UrlEncodedParser.parse(requestFactory.buildRequest(HttpMethod.GET, callbackUrl, null)
					.execute().parseAsString(), callbackUrl);
			response.setDone(true);
			response.setMessage(callbackUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			response.setDone(false);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Access Long-Lived Token
	 * @param consumerKey
	 * @param token
	 * @param tokenSecret
	 * @param verifier
	 * @return
	 */
	public APIResponse accessToken(String consumerKey, String token, String tokenSecret, String verifier) {
		APIResponse response = new APIResponse();
		try {
			OAuthGetAccessToken accessToken = new OAuthGetAccessToken(url + "/accessToken");
			accessToken.transport = transport;
			accessToken.signer = signer;
			accessToken.consumerKey = consumerKey;
			accessToken.temporaryToken = token;
			signer.tokenSharedSecret = tokenSecret;
			accessToken.verifier = verifier;
			response.setDone(true);
			response.setMessage(accessToken.execute());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			response.setDone(false);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Get Authorization Header
	 * @param requestUrl
	 * @param requestMethod
	 * @param consumerKey
	 * @param token
	 * @param tokenSecret
	 * @return
	 * @throws GeneralSecurityException
	 */
	public String getAuthorizationHeader(String requestUrl, String requestMethod, String consumerKey, String token, String tokenSecret) 
		throws GeneralSecurityException {
		OAuthParameters result = new OAuthParameters();
		result.signer = signer;
		result.consumerKey = consumerKey;
		result.token = token;
		signer.tokenSharedSecret = tokenSecret;
		result.computeNonce();
		result.computeTimestamp();
		result.computeSignature(requestMethod, new GenericUrl(requestUrl));
		return result.getAuthorizationHeader();
	}

}
