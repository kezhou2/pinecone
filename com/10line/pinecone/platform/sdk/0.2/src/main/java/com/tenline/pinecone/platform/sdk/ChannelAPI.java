/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Bill
 *
 */
public class ChannelAPI extends AbstractAPI {

	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public ChannelAPI(String host, String port, APIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param subject
	 * @throws Exception
	 */
	public void subscribe(String subject) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/channel/subscribe/" + subject).openConnection();
		connection.connect();
		byte[] bytes = new byte[connection.getInputStream().available()];
		connection.getInputStream().read(bytes);
		connection.getInputStream().close();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) listener.onMessage(bytes);
		else listener.onError("Subscribe Channel Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param subject
	 * @param contentType
	 * @param contentBytes
	 * @throws Exception
	 */
	public void publish(String subject, String contentType, byte[] contentBytes) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/channel/publish/" + subject).openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", contentType);
		connection.setUseCaches(false);
		connection.connect();
		connection.getOutputStream().write(contentBytes);
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) listener.onMessage("Publish Successful!");
		else listener.onError("Publish Channel Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}

}
