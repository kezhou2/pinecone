/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class ModelAPI extends com.tenline.pinecone.platform.sdk.development.ModelAPI {

	/**
	 * 
	 * @param host
	 * @param port
	 * @param context
	 */
	public ModelAPI(String host, String port, String context) {
		super(host, port, context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public APIResponse create(Entity entity) throws Exception {
		APIResponse response = new APIResponse();
		connection = (HttpURLConnection) new URL(url + "/create").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		new ObjectOutputStream(connection.getOutputStream()).writeObject(entity);
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	response.setDone(true);
        	response.setMessage(new ObjectInputStream(connection.getInputStream()).readObject());
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Create " + entity.getClass().getSimpleName() + " Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public APIResponse delete(Entity entity) throws Exception {
		APIResponse response = new APIResponse();
		connection = (HttpURLConnection) new URL(url + "/delete/" + entity.getClass().getName() + "/" + entity.getId()).openConnection();
		connection.setRequestMethod("DELETE");
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			response.setDone(true);
			response.setMessage("Deleted!");
		} else {
			response.setDone(false);
			response.setMessage("Delete " + entity.getClass().getSimpleName() + " Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public APIResponse update(Entity entity) throws Exception {
		APIResponse response = new APIResponse();
		connection = (HttpURLConnection) new URL(url + "/update").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		new ObjectOutputStream(connection.getOutputStream()).writeObject(entity);
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	response.setDone(true);
        	response.setMessage(new ObjectInputStream(connection.getInputStream()).readObject());
        	connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Update " + entity.getClass().getSimpleName() + " Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}

}
