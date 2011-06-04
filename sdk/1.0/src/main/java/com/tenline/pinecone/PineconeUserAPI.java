/**
 * 
 */
package com.tenline.pinecone;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.util.GenericType;

import com.tenline.pinecone.model.User;

/**
 * @author Bill
 *
 */
public class PineconeUserAPI extends PineconeAPI {

	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public PineconeUserAPI(String host, String port,
			PineconeAPIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void create(User user) throws Exception {
		request = new ClientRequest(url + "/api/user/create");
		request.body(MediaType.APPLICATION_JSON, user).accept(MediaType.APPLICATION_JSON);
		response = request.post();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(User.class));
		else listener.onError("Create User Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
		request = new ClientRequest(url + "/api/user/delete/{id}");
		request.pathParameter("id", id);
		response = request.delete();
		if (response.getStatus() == 200) listener.onMessage("User Deleted!");
		else listener.onError("Delete User Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void update(User user) throws Exception {
		request = new ClientRequest(url + "/api/user/update");
		request.body(MediaType.APPLICATION_JSON, user).accept(MediaType.APPLICATION_JSON);
		response = request.put();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(User.class));
		else listener.onError("Update User Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void show(String filter) throws Exception {
		request = new ClientRequest(url + "/api/user/show/{filter}");
		request.pathParameter("filter", filter).accept(MediaType.APPLICATION_JSON);
		response = request.get();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(new GenericType<Collection<User>>(){}));
		else listener.onError("Show User Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}

}
