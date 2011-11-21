/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.net.HttpURLConnection;
import java.net.URL;

import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class StaticResourceAPI extends com.tenline.pinecone.platform.sdk.development.StaticResourceAPI {

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
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public APIResponse uploadIcon(String id, byte[] content) throws Exception {
		APIResponse response = new APIResponse();
		String requestUrl = url + "/api/static/resource/upload/" + id + "/@Icon";
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		connection.getOutputStream().write(content);
		connection.getOutputStream().flush();
		connection.getOutputStream().close();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			response.setDone(true);
			response.setMessage("Upload Icon Successful!");
		} else {
			response.setDone(false);
			response.setMessage("Upload Icon Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}

}
