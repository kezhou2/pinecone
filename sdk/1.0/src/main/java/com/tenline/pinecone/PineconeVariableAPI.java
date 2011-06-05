/**
 * 
 */
package com.tenline.pinecone;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.util.GenericType;

import com.tenline.pinecone.model.Variable;

/**
 * @author Bill
 *
 */
public class PineconeVariableAPI extends PineconeAPI {

	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public PineconeVariableAPI(String host, String port,
			PineconeAPIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param variable
	 * @throws Exception
	 */
	public void create(Variable variable) throws Exception {
		request = new ClientRequest(url + "/api/variable/create");
		request.body(MediaType.APPLICATION_JSON, variable).accept(MediaType.APPLICATION_JSON);
		response = request.post();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(Variable.class));
		else listener.onError("Create Variable Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
		request = new ClientRequest(url + "/api/variable/delete/{id}");
		request.pathParameter("id", id);
		response = request.delete();
		if (response.getStatus() == 200) listener.onMessage("Variable Deleted!");
		else listener.onError("Delete Variable Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param variable
	 * @throws Exception
	 */
	public void update(Variable variable) throws Exception {
		request = new ClientRequest(url + "/api/variable/update");
		request.body(MediaType.APPLICATION_JSON, variable).accept(MediaType.APPLICATION_JSON);
		response = request.put();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(Variable.class));
		else listener.onError("Update Variable Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void show(String filter) throws Exception {
		request = new ClientRequest(url + "/api/variable/show/{filter}");
		request.pathParameter("filter", filter).accept(MediaType.APPLICATION_JSON);
		response = request.get();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(new GenericType<Collection<Variable>>(){}));
		else listener.onError("Show Variable Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}

}
