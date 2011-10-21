/**
 * 
 */
package com.tenline.pinecone.platform.sdk.development;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;

import com.tenline.pinecone.platform.model.Item;

/**
 * @author Bill
 *
 */
public class ItemAPI extends JaxbAPI {

	/**
	 * 
	 * @param host
	 * @param port
	 */
	public ItemAPI(String host, String port) {
		super(host, port);
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
	 * @param filter
	 * @param authorization
	 * @return
	 * @throws Exception
	 */
	public APIResponse show(String filter, String authorization) throws Exception {
		APIResponse response = new APIResponse();
		String requestUrl = url + "/api/item/show/" + filter;
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setRequestProperty("Authorization", authorization);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			Collection<Item> message = new ArrayList<Item>();
			for (int i=0; i<array.length(); i++) {
				message.add((Item) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			response.setDone(true);
			response.setMessage(message);
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Show Item Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}
	
	/**
	 * 
	 * @param filter
	 * @param authorization
	 * @return
	 * @throws Exception
	 */
	public APIResponse showByVariable(String filter, String authorization) throws Exception {
		APIResponse response = new APIResponse();
		String requestUrl = url + "/api/item/show/" + filter + "/@Variable";
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setRequestProperty("Authorization", authorization);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JSONArray array = new JSONArray(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			Collection<Item> message = new ArrayList<Item>();
			for (int i=0; i<array.length(); i++) {
				message.add((Item) unmarshaller.unmarshal(new MappedXMLStreamReader(array.getJSONObject(i), 
						new MappedNamespaceConvention(new Configuration()))));
			}
			response.setDone(true);
			response.setMessage(message);
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Show Item By Variable Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}

}
