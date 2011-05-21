/**
 * 
 */
package com.tenline.pinecone.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.util.GenericType;
import org.junit.Test;

import com.tenline.pinecone.model.Device;
import com.tenline.pinecone.model.User;

/**
 * @author Bill
 *
 */
public class DeviceServiceIntegrationTest extends AbstractServiceIntegrationTest {

	@Test
	public void testCRUD() throws Exception {
		request = new ClientRequest(url + "/user/create");
		request.body(MediaType.APPLICATION_JSON, "{\"user\":{\"snsId\":\"251417324\"}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.post();
		assertEquals(200, response.getStatus());
		User user = response.getEntity(User.class);
		assertEquals("251417324", user.getSnsId());
		response.releaseConnection();
		request = new ClientRequest(url + "/device/create");
		request.body(MediaType.APPLICATION_JSON, "{\"device\":{\"name\":\"LNB\",\"type\":\"serial\",\"user\":{\"id\":\""+user.getId()+"\"}}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.post();
		assertEquals(200, response.getStatus());
		Device device = response.getEntity(Device.class);
		assertEquals("LNB", device.getName());
		assertEquals("serial", device.getType());
		response.releaseConnection();
		request = new ClientRequest(url + "/device/update");
		request.body(MediaType.APPLICATION_JSON, "{\"device\":{\"id\":\""+device.getId()+"\",\"name\":\"ACU\",\"type\":\"tcp\"}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.put();
		assertEquals(200, response.getStatus());
		device = response.getEntity(Device.class);
		assertEquals("ACU", device.getName());
		assertEquals("tcp", device.getType());
		response.releaseConnection();
		request = new ClientRequest(url + "/device/show/{filter}");
		request.pathParameter("filter", "id=='"+device.getId()+"'")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals(1, response.getEntity(new GenericType<Collection<Device>>(){}).size());
		response.releaseConnection();
		request = new ClientRequest(url + "/device/delete/{id}");
		request.pathParameter("id", device.getId());
		response = request.delete();
		assertEquals(200, response.getStatus());
		response.releaseConnection();
		request = new ClientRequest(url + "/device/show/{filter}");
		request.pathParameter("filter", "id=='"+device.getId()+"'")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals(0, response.getEntity(new GenericType<Collection<Device>>(){}).size());
		response.releaseConnection();
	}

}
