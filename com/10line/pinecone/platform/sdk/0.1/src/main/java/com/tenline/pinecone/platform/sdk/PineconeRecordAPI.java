/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.util.GenericType;

import com.tenline.pinecone.platform.model.Record;

/**
 * @author Bill
 *
 */
public class PineconeRecordAPI extends PineconeAPI {

	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public PineconeRecordAPI(String host, String port,
			PineconeAPIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param record
	 * @throws Exception
	 */
	public void create(Record record) throws Exception {
		request = new ClientRequest(url + "/api/record/create");
		request.body(MediaType.APPLICATION_JSON, record).accept(MediaType.APPLICATION_JSON);
		response = request.post();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(Record.class));
		else listener.onError("Create Record Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
		request = new ClientRequest(url + "/api/record/delete/{id}");
		request.pathParameter("id", id);
		response = request.delete();
		if (response.getStatus() == 200) listener.onMessage("Record Deleted!");
		else listener.onError("Delete Record Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param record
	 * @throws Exception
	 */
	public void update(Record record) throws Exception {
		request = new ClientRequest(url + "/api/record/update");
		request.body(MediaType.APPLICATION_JSON, record).accept(MediaType.APPLICATION_JSON);
		response = request.put();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(Record.class));
		else listener.onError("Update Record Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void show(String filter) throws Exception {
		request = new ClientRequest(url + "/api/record/show/{filter}");
		request.pathParameter("filter", filter).accept(MediaType.APPLICATION_JSON);
		response = request.get();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(new GenericType<Collection<Record>>(){}));
		else listener.onError("Show Record Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}

}
