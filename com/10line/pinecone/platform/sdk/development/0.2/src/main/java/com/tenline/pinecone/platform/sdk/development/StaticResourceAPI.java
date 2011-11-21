/**
 * 
 */
package com.tenline.pinecone.platform.sdk.development;

import java.net.HttpURLConnection;
import java.net.URL;

import com.google.api.client.http.HttpMethod;

/**
 * @author Bill
 *
 */
public class StaticResourceAPI extends ResourceAPI {

	/**
	 * @param host
	 * @param port
	 * @param context
	 */
	public StaticResourceAPI(String host, String port, String context) {
		super(host, port, context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param id
	 * @param consumerKey
	 * @param consumerSecret
	 * @param token
	 * @param tokenSecret
	 * @return
	 * @throws Exception
	 */
	public APIResponse downloadIcon(String id, String consumerKey, String consumerSecret, 
			String token, String tokenSecret) throws Exception {
		APIResponse response = new APIResponse();
		String requestUrl = url + "/api/static/resource/download/" + id + "/@Icon";
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setRequestProperty("Authorization", getAuthorization(requestUrl, HttpMethod.GET.name(), 
				consumerKey, consumerSecret, token, tokenSecret));
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		byte[] bytes = new byte[connection.getInputStream().available()];
		connection.getInputStream().read(bytes);
		connection.getInputStream().close();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			response.setDone(true);
			response.setMessage(bytes);
		} else {
			response.setDone(false);
			response.setMessage("Download Icon Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}

}
