/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

import com.tenline.pinecone.platform.model.Variable;

/**
 * @author Bill
 *
 */
public class VariableAPI extends AbstractAPI {

	private JAXBContext context;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	
	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public VariableAPI(String host, String port, APIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
		try {
			context = JAXBContext.newInstance(Variable.class);
			marshaller = context.createMarshaller();
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param variable
	 * @throws Exception
	 */
	public void create(Variable variable) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/variable/create").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setUseCaches(false);
		connection.connect();
		variable.setName(URLEncoder.encode(variable.getName(), "utf-8"));
		marshaller.marshal(variable, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), 
				new OutputStreamWriter(connection.getOutputStream())));
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	JSONObject obj = new JSONObject(new String(new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine()));
			variable = (Variable) unmarshaller.unmarshal(new MappedXMLStreamReader(obj, new MappedNamespaceConvention(new Configuration())));
        	variable.setName(URLDecoder.decode(variable.getName(), "utf-8"));
			listener.onMessage(variable);
			connection.getInputStream().close();
		}
		else listener.onError("Create Variable Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/variable/delete/" + id).openConnection();
		connection.setRequestMethod("DELETE");
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) listener.onMessage("Variable Deleted!");
		else listener.onError("Delete Variable Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param variable
	 * @throws Exception
	 */
	public void update(Variable variable) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/variable/update").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setUseCaches(false);
		connection.connect();
		variable.setName(URLEncoder.encode(variable.getName(), "utf-8"));
		marshaller.marshal(variable, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), 
				new OutputStreamWriter(connection.getOutputStream())));
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	JSONObject obj = new JSONObject(new String(new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine()));
			variable = (Variable) unmarshaller.unmarshal(new MappedXMLStreamReader(obj, new MappedNamespaceConvention(new Configuration())));
        	variable.setName(URLDecoder.decode(variable.getName(), "utf-8"));
			listener.onMessage(variable);
			connection.getInputStream().close();
		}
		else listener.onError("Update Variable Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void show(String filter) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/variable/show/" + filter).openConnection();
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine()));
			Collection<Variable> message = new ArrayList<Variable>();
			for (int i=0; i<array.length(); i++) {
				Variable variable = (Variable) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration())));
				variable.setName(URLDecoder.decode(variable.getName(), "utf-8"));
				message.add(variable);
			}
			listener.onMessage(message);
			connection.getInputStream().close();
		}
		else listener.onError("Show Variable Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void showByDevice(String filter) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/variable/show/" + filter + "/@Device").openConnection();
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine()));
			Collection<Variable> message = new ArrayList<Variable>();
			for (int i=0; i<array.length(); i++) {
				Variable variable = (Variable) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration())));
				variable.setName(URLDecoder.decode(variable.getName(), "utf-8"));
				message.add(variable);
			}
			listener.onMessage(message);
			connection.getInputStream().close();
		}
		else listener.onError("Show Variable By Device Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}

}
