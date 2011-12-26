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

import com.tenline.pinecone.platform.model.ConsumerInstallation;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.sdk.development.JaxbAPI;

/**
 * @author Bill
 *
 */
public class ConsumerInstallationAPI extends JaxbAPI {

	/**
	 * 
	 * @param host
	 * @param port
	 * @param context
	 */
	public ConsumerInstallationAPI(String host, String port, String context) {
		super(host, port, context);
		// TODO Auto-generated constructor stub
		try {
			jaxbContext = JAXBContext.newInstance(ConsumerInstallation.class);
			marshaller = jaxbContext.createMarshaller();
			unmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * 
	 * @param consumerInstallation
	 * @return
	 * @throws Exception
	 */
	public APIResponse create(ConsumerInstallation consumerInstallation) throws Exception {
		APIResponse response = new APIResponse();
		connection = (HttpURLConnection) new URL(url + "/api/consumer/installation/create").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		marshaller.marshal(consumerInstallation, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), 
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
			response.setMessage("Create Consumer Installation Error Code: Http (" + connection.getResponseCode() + ")");
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
		connection = (HttpURLConnection) new URL(url + "/api/consumer/installation/delete/" + id).openConnection();
		connection.setRequestMethod("DELETE");
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			response.setDone(true);
			response.setMessage("Consumer Installation Deleted!");
		} else {
			response.setDone(false);
			response.setMessage("Delete Consumer Installation Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}
	
	/**
	 * 
	 * @param consumerInstallation
	 * @return
	 * @throws Exception
	 */
	public APIResponse update(ConsumerInstallation consumerInstallation) throws Exception {
		APIResponse response = new APIResponse();
		connection = (HttpURLConnection) new URL(url + "/api/consumer/installation/update").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		marshaller.marshal(consumerInstallation, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), 
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
			response.setMessage("Update Consumer Installation Error Code: Http (" + connection.getResponseCode() + ")");
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
		String requestUrl = url + "/api/consumer/installation/show/" + filter;
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			Collection<ConsumerInstallation> message = new ArrayList<ConsumerInstallation>();
			for (int i=0; i<array.length(); i++) {
				message.add((ConsumerInstallation) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			response.setDone(true);
			response.setMessage(message);
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Show Consumer Installation Error Code: Http (" + connection.getResponseCode() + ")");
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
	public APIResponse showByUser(String filter) throws Exception {
		APIResponse response = new APIResponse();
		String requestUrl = url + "/api/consumer/installation/show/@User/" + filter;
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			Collection<ConsumerInstallation> message = new ArrayList<ConsumerInstallation>();
			for (int i=0; i<array.length(); i++) {
				message.add((ConsumerInstallation) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			response.setDone(true);
			response.setMessage(message);
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Show Consumer Installation By User Error Code: Http (" + connection.getResponseCode() + ")");
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
	public APIResponse showByConsumer(String filter) throws Exception {
		APIResponse response = new APIResponse();
		String requestUrl = url + "/api/consumer/installation/show/@Consumer/" + filter;
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			Collection<ConsumerInstallation> message = new ArrayList<ConsumerInstallation>();
			for (int i=0; i<array.length(); i++) {
				message.add((ConsumerInstallation) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			response.setDone(true);
			response.setMessage(message);
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Show Consumer Installation By Consumer Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}
	
}
