/**
 * 
 */
package com.tenline.pinecone.platform.sdk.development;

import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Bill
 *
 */
public class ModelAPI extends ResourceAPI {
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @param context
	 */
	public ModelAPI(String host, String port, String context) {
		super(host, port, context);
		// TODO Auto-generated constructor stub
		url += "/api/model";
	}
	
	/**
	 * 
	 * @param entityClass
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public APIResponse show(Class<?> entityClass, String filter) throws Exception {
		APIResponse response = new APIResponse();
		String requestUrl = url + "/show/" + entityClass.getName() + "/" + filter;
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			response.setDone(true);
			response.setMessage(new ObjectInputStream(connection.getInputStream()).readObject());
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Show " + entityClass.getSimpleName() + " Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}

}
