/**
 * 
 */
package com.tenline.pinecone.platform.sdk.oauth;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

/**
 * @author Bill
 *
 */
public class OAuthConsumerUrl extends GenericUrl {

	@Key("oauth_consumer_key")
	public String consumerKey;
	
	@Key("xoauth_consumer_secret")
	public String consumerSecret;

	/**
	 * @param encodedUrl
	 */
	public OAuthConsumerUrl(String encodedUrl) {
		super(encodedUrl);
		// TODO Auto-generated constructor stub
	}

}
