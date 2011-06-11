/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.util.GenericType;

import com.tenline.pinecone.platform.model.Device;

/**
 * @author Bill
 *
 */
public class DeviceAPI extends AbstractAPI {

	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public DeviceAPI(String host, String port,
			APIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param device
	 * @throws Exception 
	 */
	public void create(Device device) throws Exception {
		request = new ClientRequest(url + "/api/device/create");
		request.body(MediaType.APPLICATION_JSON, device).accept(MediaType.APPLICATION_JSON);
		response = request.post();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(Device.class));
		else listener.onError("Create Device Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
		request = new ClientRequest(url + "/api/device/delete/{id}");
		request.pathParameter("id", id);
		response = request.delete();
		if (response.getStatus() == 200) listener.onMessage("Device Deleted!");
		else listener.onError("Delete Device Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param device
	 * @throws Exception
	 */
	public void update(Device device) throws Exception {
		request = new ClientRequest(url + "/api/device/update");
		request.body(MediaType.APPLICATION_JSON, device).accept(MediaType.APPLICATION_JSON);
		response = request.put();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(Device.class));
		else listener.onError("Update Device Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void show(String filter) throws Exception {
		request = new ClientRequest(url + "/api/device/show/{filter}");
		request.pathParameter("filter", filter).accept(MediaType.APPLICATION_JSON);
		response = request.get();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(new GenericType<Collection<Device>>(){}));
		else listener.onError("Show Device Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}

}
