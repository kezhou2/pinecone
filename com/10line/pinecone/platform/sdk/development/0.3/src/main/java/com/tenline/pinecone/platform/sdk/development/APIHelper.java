/**
 * 
 */
package com.tenline.pinecone.platform.sdk.development;

import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;

/**
 * @author Bill
 *
 */
public class APIHelper {

	/**
	 * 
	 */
	public APIHelper() {
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
	public static String getAuthorization(String requestUrl, String requestMethod, 
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
