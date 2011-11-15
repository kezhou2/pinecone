/**
 * 
 */
package com.tenline.pinecone.platform.sdk.development;

import java.net.HttpURLConnection;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;

/**
 * @author Bill
 *
 */
public abstract class ResourceAPI extends AbstractAPI {
	
	protected final static int TIMEOUT = 10000;
	
	protected HttpURLConnection connection;
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @param context
	 */
	public ResourceAPI(String host, String port, String context) {
		super(host, port, context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get Authorization
	 * @param requestUrl
	 * @param requestMethod
	 * @param consumerKey
	 * @param consumerSecret
	 * @param token
	 * @param tokenSecret
	 * @return
	 * @throws GeneralSecurityException
	 */
	protected String getAuthorization(String requestUrl, String requestMethod, 
			String consumerKey, String consumerSecret, String token, String tokenSecret) 
		throws GeneralSecurityException {
		OAuthParameters result = new OAuthParameters();
		OAuthHmacSigner signer = new OAuthHmacSigner();
		result.signer = signer;
		result.consumerKey = consumerKey;
		result.token = token;
		signer.clientSharedSecret = consumerSecret;
		signer.tokenSharedSecret = tokenSecret;
		result.computeNonce();
		result.computeTimestamp();
		result.computeSignature(requestMethod, new GenericUrl(requestUrl));
		return result.getAuthorizationHeader();
	}

}
