/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.google.gson.Gson;
import com.tenline.pinecone.platform.model.Device;

/**
 * @author Bill
 *
 */
public class ChannelAPI extends AbstractAPI {
	
	/**
	 * Google Json
	 */
	private Gson gson;
	
	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public ChannelAPI(String host, String port, APIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
		gson = new Gson();
	}
	
	/**
	 * 
	 * @param subject
	 * @throws Exception
	 */
	public void subscribe(String subject) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/channel/subscribe/" + subject).openConnection();
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		GZIPInputStream input = new GZIPInputStream(connection.getInputStream());
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int data; while ((data = input.read()) != -1) { output.write(data); }
		input.close(); output.flush(); output.close();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			if (connection.getContentType().indexOf("application/json") >= 0) {
				listener.onMessage(gson.fromJson(new String(output.toByteArray(), "utf-8"), Device.class));
			} else {
				listener.onMessage(output.toByteArray());
			}
		}else listener.onError("Subscribe Channel Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param subject
	 * @param contentType
	 * @param content
	 * @throws Exception
	 */
	public void publish(String subject, String contentType, Object content) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/channel/publish/" + subject).openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		GZIPOutputStream output = new GZIPOutputStream(connection.getOutputStream());
		if (contentType.indexOf("application/json") >= 0) {
			output.write(gson.toJson(content).getBytes("utf-8"));
		} else {
			output.write((byte[]) content);
		}
		output.flush();
        output.close();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) listener.onMessage("Publish Successful!");
		else listener.onError("Publish Channel Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}

}
