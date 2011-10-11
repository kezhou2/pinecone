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
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

import com.tenline.pinecone.platform.model.Application;

/**
 * @author Bill
 *
 */
public class ApplicationAPI extends ResourceAPI {

	private JAXBContext context;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	
	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public ApplicationAPI(String host, String port, APIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
		try {
			context = JAXBContext.newInstance(Application.class);
			marshaller = context.createMarshaller();
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param application
	 * @throws Exception
	 */
	public void create(Application application) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/application/create").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		marshaller.marshal(application, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), 
				new OutputStreamWriter(connection.getOutputStream(), "utf-8")));
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	JSONObject obj = new JSONObject(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
        	listener.onMessage(unmarshaller.unmarshal(new MappedXMLStreamReader(obj, new MappedNamespaceConvention(new Configuration()))));
			connection.getInputStream().close();
		}
		else listener.onError("Create Application Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/application/delete/" + id).openConnection();
		connection.setRequestMethod("DELETE");
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) listener.onMessage("Application Deleted!");
		else listener.onError("Delete Application Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param application
	 * @throws Exception
	 */
	public void update(Application application) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/application/update").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		marshaller.marshal(application, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), 
				new OutputStreamWriter(connection.getOutputStream(), "utf-8")));
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	JSONObject obj = new JSONObject(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
        	listener.onMessage(unmarshaller.unmarshal(new MappedXMLStreamReader(obj, new MappedNamespaceConvention(new Configuration()))));
			connection.getInputStream().close();
		}
		else listener.onError("Update Application Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void show(String filter) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/application/show/" + filter).openConnection();
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			Collection<Application> message = new ArrayList<Application>();
			for (int i=0; i<array.length(); i++) {
				message.add((Application) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			listener.onMessage(message);
			connection.getInputStream().close();
		}
		else listener.onError("Show Application Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void showByUser(String filter) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/application/show/" + filter + "/@User").openConnection();
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			Collection<Application> message = new ArrayList<Application>();
			for (int i=0; i<array.length(); i++) {
				message.add((Application) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			listener.onMessage(message);
			connection.getInputStream().close();
		}
		else listener.onError("Show Application By User Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}

}
