/**
 * 
 */
package com.tenline.pinecone.platform.sdk.development;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.api.client.http.HttpMethod;

/**
 * @author Bill
 *
 */
public class ChannelAPI extends ResourceAPI {
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @param context
	 */
	public ChannelAPI(String host, String port, String context) {
		super(host, port, context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param subject
	 * @param consumerKey
	 * @param consumerSecret
	 * @param token
	 * @param tokenSecret
	 * @return
	 * @throws Exception
	 */
	public APIResponse subscribe(String subject, String consumerKey, String consumerSecret, 
			String token, String tokenSecret) throws Exception {
		APIResponse response = new APIResponse();
		String requestUrl = url + "/api/channel/subscribe/" + subject;
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setRequestProperty("Authorization", APIHelper.getAuthorization(requestUrl, HttpMethod.GET.name(), 
				consumerKey, consumerSecret, token, tokenSecret));
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			response.setDone(true);
			if (connection.getContentType().indexOf("application/octet-stream") >= 0) {
				response.setMessage(new ObjectInputStream(connection.getInputStream()).readObject());
			} else {
				byte[] bytes = new byte[connection.getInputStream().available()];
				connection.getInputStream().read(bytes);
				response.setMessage(bytes);
			}
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Subscribe Channel Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}
	
	/**
	 * 
	 * @param subject
	 * @param contentType
	 * @param content
	 * @param consumerKey
	 * @param consumerSecret
	 * @param token
	 * @param tokenSecret
	 * @return
	 * @throws Exception
	 */
	public APIResponse publish(String subject, String contentType, Object content, 
			String consumerKey, String consumerSecret, String token, String tokenSecret) throws Exception {
		APIResponse response = new APIResponse();
		String requestUrl = url + "/api/channel/publish/" + subject;
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
		connection.setRequestProperty("Authorization", APIHelper.getAuthorization(requestUrl, HttpMethod.POST.name(), 
				consumerKey, consumerSecret, token, tokenSecret));
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (contentType.indexOf("application/octet-stream") >= 0) {
			new ObjectOutputStream(connection.getOutputStream()).writeObject(content);
		} else {
			connection.getOutputStream().write((byte[]) content);
		}
		connection.getOutputStream().flush();
		connection.getOutputStream().close();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			response.setDone(true);
			response.setMessage("Publish Successful!");
		} else {
			response.setDone(false);
			response.setMessage("Publish Channel Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}

}
