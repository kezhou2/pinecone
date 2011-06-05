/**
 * 
 */
package com.tenline.pinecone;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.util.GenericType;

import com.tenline.pinecone.model.Item;

/**
 * @author Bill
 *
 */
public class PineconeItemAPI extends PineconeAPI {

	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public PineconeItemAPI(String host, String port,
			PineconeAPIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param item
	 * @throws Exception
	 */
	public void create(Item item) throws Exception {
		request = new ClientRequest(url + "/api/item/create");
		request.body(MediaType.APPLICATION_JSON, item).accept(MediaType.APPLICATION_JSON);
		response = request.post();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(Item.class));
		else listener.onError("Create Item Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
		request = new ClientRequest(url + "/api/item/delete/{id}");
		request.pathParameter("id", id);
		response = request.delete();
		if (response.getStatus() == 200) listener.onMessage("Item Deleted!");
		else listener.onError("Delete Item Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param item
	 * @throws Exception
	 */
	public void update(Item item) throws Exception {
		request = new ClientRequest(url + "/api/item/update");
		request.body(MediaType.APPLICATION_JSON, item).accept(MediaType.APPLICATION_JSON);
		response = request.put();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(Item.class));
		else listener.onError("Update Item Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void show(String filter) throws Exception {
		request = new ClientRequest(url + "/api/item/show/{filter}");
		request.pathParameter("filter", filter).accept(MediaType.APPLICATION_JSON);
		response = request.get();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(new GenericType<Collection<Item>>(){}));
		else listener.onError("Show Item Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}

}
