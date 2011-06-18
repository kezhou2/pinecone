/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
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
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void showByUser(String filter) throws Exception {
		request = new ClientRequest(url + "/api/device/show/{filter}/@User");
		request.pathParameter("filter", filter).accept(MediaType.APPLICATION_JSON);
		response = request.get();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(new GenericType<Collection<Device>>(){}));
		else listener.onError("Show Device By User Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void get() throws Exception {
		request = new ClientRequest(url + "/svn/repository/releases.xml");
		request.accept(MediaType.APPLICATION_XML);
		response = request.get();
		if (response.getStatus() == 200) {
			Document doc = DocumentHelper.parseText(response.getEntity(String.class));
			List<Node> deviceNodes = doc.selectNodes("/repository/resource");
			Collection<Device> devices = new ArrayList<Device>();
			for (int i=0; i<deviceNodes.size(); i++) {
				Node deviceNode = deviceNodes.get(i);
				Device device = new Device();
				device.setName(deviceNode.selectSingleNode("./@presentationname").getText());
				device.setSymbolicName(deviceNode.selectSingleNode("./@symbolicname").getText());
				device.setVersion(deviceNode.selectSingleNode("./@version").getText());
				devices.add(device);
			}
			listener.onMessage(devices);
		} else {
			listener.onError("Get Device Error Code: Http (" + response.getStatus() + ")");
		}
		response.releaseConnection();
	}

}
