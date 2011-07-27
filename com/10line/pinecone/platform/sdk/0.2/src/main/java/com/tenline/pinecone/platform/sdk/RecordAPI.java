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

import com.tenline.pinecone.platform.model.Record;

/**
 * @author Bill
 *
 */
public class RecordAPI extends AbstractAPI {

	private JAXBContext context;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	
	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public RecordAPI(String host, String port, APIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
		try {
			context = JAXBContext.newInstance(Record.class);
			marshaller = context.createMarshaller();
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param record
	 * @throws Exception
	 */
	public void create(Record record) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/record/create").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setUseCaches(false);
		connection.connect();
		marshaller.marshal(record, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), 
				new OutputStreamWriter(connection.getOutputStream(), "utf-8")));
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	JSONObject obj = new JSONObject(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			listener.onMessage(unmarshaller.unmarshal(new MappedXMLStreamReader(obj, new MappedNamespaceConvention(new Configuration()))));
			connection.getInputStream().close();
		}
		else listener.onError("Create Record Error Code: Http (" + connection.getResponseCode() + ")");
        connection.disconnect();
	}
	
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/record/delete/" + id).openConnection();
		connection.setRequestMethod("DELETE");
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) listener.onMessage("Record Deleted!");
		else listener.onError("Delete Record Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param record
	 * @throws Exception
	 */
	public void update(Record record) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/record/update").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setUseCaches(false);
		connection.connect();
		marshaller.marshal(record, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), 
				new OutputStreamWriter(connection.getOutputStream(), "utf-8")));
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	JSONObject obj = new JSONObject(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			listener.onMessage(unmarshaller.unmarshal(new MappedXMLStreamReader(obj, new MappedNamespaceConvention(new Configuration()))));
			connection.getInputStream().close();
		}
		else listener.onError("Update Record Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void show(String filter) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/record/show/" + filter).openConnection();
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			Collection<Record> message = new ArrayList<Record>();
			for (int i=0; i<array.length(); i++) {
				message.add((Record) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			listener.onMessage(message);
			connection.getInputStream().close();
		}
		else listener.onError("Show Record Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void showByVariable(String filter) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/record/show/" + filter + "/@Variable").openConnection();
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			Collection<Record> message = new ArrayList<Record>();
			for (int i=0; i<array.length(); i++) {
				message.add((Record) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			listener.onMessage(message);
			connection.getInputStream().close();
		}
		else listener.onError("Show Record By Variable Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}

}
