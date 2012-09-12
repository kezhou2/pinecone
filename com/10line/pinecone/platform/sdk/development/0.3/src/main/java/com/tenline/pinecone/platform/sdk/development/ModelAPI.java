/**
 * 
 */
package com.tenline.pinecone.platform.sdk.development;

import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.tenline.pinecone.platform.model.Entity;

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
		String requestUrl = url + "/show/" + entityClass.getName() + "/" + URLEncoder.encode(filter, "UTF-8");
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			try {
				response.setMessage(new ObjectInputStream(connection.getInputStream()).readObject());
			} catch(Exception ex) {
				response.setMessage(new ArrayList<Entity>());
			} finally {
				response.setDone(true);
				connection.getInputStream().close();	
			}
		} else {
			response.setDone(false);
			response.setMessage("Show " + entityClass.getSimpleName() + " Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}

}
