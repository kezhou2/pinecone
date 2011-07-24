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

import com.tenline.pinecone.platform.model.Item;

/**
 * @author Bill
 *
 */
public class ItemAPI extends AbstractAPI {

	private JAXBContext context;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	
	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public ItemAPI(String host, String port, APIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
		try {
			context = JAXBContext.newInstance(Item.class);
			marshaller = context.createMarshaller();
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param item
	 * @throws Exception
	 */
	public void create(Item item) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/item/create").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setUseCaches(false);
		connection.connect();
		marshaller.marshal(item, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), 
				new OutputStreamWriter(connection.getOutputStream())));
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	JSONObject obj = new JSONObject(new String(new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine()));
			listener.onMessage(unmarshaller.unmarshal(new MappedXMLStreamReader(obj, new MappedNamespaceConvention(new Configuration()))));
			connection.getInputStream().close();
		}
		else listener.onError("Create Item Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/item/delete/" + id).openConnection();
		connection.setRequestMethod("DELETE");
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) listener.onMessage("Item Deleted!");
		else listener.onError("Delete Item Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param item
	 * @throws Exception
	 */
	public void update(Item item) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/item/update").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setUseCaches(false);
		connection.connect();
		marshaller.marshal(item, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), 
				new OutputStreamWriter(connection.getOutputStream())));
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	JSONObject obj = new JSONObject(new String(new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine()));
        	listener.onMessage(unmarshaller.unmarshal(new MappedXMLStreamReader(obj, new MappedNamespaceConvention(new Configuration()))));
			connection.getInputStream().close();
		}
		else listener.onError("Update Item Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void show(String filter) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/item/show/" + filter).openConnection();
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine()));
			Collection<Item> message = new ArrayList<Item>();
			for (int i=0; i<array.length(); i++) {
				message.add((Item) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			listener.onMessage(message);
			connection.getInputStream().close();
		}
		else listener.onError("Show Item Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void showByVariable(String filter) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/item/show/" + filter + "/@Variable").openConnection();
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine()));
			Collection<Item> message = new ArrayList<Item>();
			for (int i=0; i<array.length(); i++) {
				message.add((Item) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			listener.onMessage(message);
			connection.getInputStream().close();
		}
		else listener.onError("Show Item By Variable Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}

}
