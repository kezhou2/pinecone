/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

import com.tenline.pinecone.platform.model.UserMessage;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.sdk.development.JaxbAPI;

/**
 * @author Bill
 *
 */
public class UserMessageAPI extends JaxbAPI {

	/**
	 * @param host
	 * @param port
	 * @param context
	 */
	public UserMessageAPI(String host, String port, String context) {
		super(host, port, context);
		// TODO Auto-generated constructor stub
		try {
			jaxbContext = JAXBContext.newInstance(UserMessage.class);
			marshaller = jaxbContext.createMarshaller();
			unmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param userMessage
	 * @return
	 * @throws Exception
	 */
	public APIResponse create(UserMessage userMessage) throws Exception {
		APIResponse response = new APIResponse();
		connection = (HttpURLConnection) new URL(url + "/api/user/message/create").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		marshaller.marshal(userMessage, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), 
				new OutputStreamWriter(connection.getOutputStream(), "utf-8")));
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	JSONObject obj = new JSONObject(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
        	response.setDone(true);
        	response.setMessage(unmarshaller.unmarshal(new MappedXMLStreamReader(obj, new MappedNamespaceConvention(new Configuration()))));
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Create User Message Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public APIResponse delete(String id) throws Exception {
		APIResponse response = new APIResponse();
		connection = (HttpURLConnection) new URL(url + "/api/user/message/delete/" + id).openConnection();
		connection.setRequestMethod("DELETE");
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			response.setDone(true);
			response.setMessage("User Message Deleted!");
		} else {
			response.setDone(false);
			response.setMessage("Delete User Message Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}
	
	/**
	 * 
	 * @param userMessage
	 * @return
	 * @throws Exception
	 */
	public APIResponse update(UserMessage userMessage) throws Exception {
		APIResponse response = new APIResponse();
		connection = (HttpURLConnection) new URL(url + "/api/user/message/update").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		marshaller.marshal(userMessage, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), 
				new OutputStreamWriter(connection.getOutputStream(), "utf-8")));
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	JSONObject obj = new JSONObject(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
        	response.setDone(true);
        	response.setMessage(unmarshaller.unmarshal(new MappedXMLStreamReader(obj, new MappedNamespaceConvention(new Configuration()))));
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Update User Message Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public APIResponse show(String filter) throws Exception {
		APIResponse response = new APIResponse();
		String requestUrl = url + "/api/user/message/show/" + filter;
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			Collection<UserMessage> message = new ArrayList<UserMessage>();
			for (int i=0; i<array.length(); i++) {
				message.add((UserMessage) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			response.setDone(true);
			response.setMessage(message);
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Show User Message Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public APIResponse showBySender(String filter) throws Exception {
		APIResponse response = new APIResponse();
		String requestUrl = url + "/api/user/message/show/@Sender/" + filter;
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			Collection<UserMessage> message = new ArrayList<UserMessage>();
			for (int i=0; i<array.length(); i++) {
				message.add((UserMessage) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			response.setDone(true);
			response.setMessage(message);
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Show User Message By Sender Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public APIResponse showByReceiver(String filter) throws Exception {
		APIResponse response = new APIResponse();
		String requestUrl = url + "/api/user/message/show/@Receiver/" + filter;
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			Collection<UserMessage> message = new ArrayList<UserMessage>();
			for (int i=0; i<array.length(); i++) {
				message.add((UserMessage) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			response.setDone(true);
			response.setMessage(message);
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Show User Message By Receiver Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}

}
