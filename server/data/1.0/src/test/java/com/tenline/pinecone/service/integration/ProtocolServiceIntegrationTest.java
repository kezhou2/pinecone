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
import com.tenline.pinecone.model.Protocol;
import com.tenline.pinecone.model.User;

/**
 * @author Bill
 *
 */
public class ProtocolServiceIntegrationTest extends AbstractServiceIntegrationTest {

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
		request = new ClientRequest(url + "/protocol/create");
		request.body(MediaType.APPLICATION_JSON, "{\"protocol\":{\"name\":\"modbus\",\"version\":\"1.1\",\"device\":{\"id\":\""+device.getId()+"\"}}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.post();
		assertEquals(200, response.getStatus());
		Protocol protocol = response.getEntity(Protocol.class);
		assertEquals("modbus", protocol.getName());
		assertEquals("1.1", protocol.getVersion());
		response.releaseConnection();
		request = new ClientRequest(url + "/protocol/update");
		request.body(MediaType.APPLICATION_JSON, "{\"protocol\":{\"id\":\""+protocol.getId()+"\",\"name\":\"SNMP\",\"version\":\"1.2\"}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.put();
		assertEquals(200, response.getStatus());
		protocol = response.getEntity(Protocol.class);
		assertEquals("SNMP", protocol.getName());
		assertEquals("1.2", protocol.getVersion());
		response.releaseConnection();
		request = new ClientRequest(url + "/protocol/show/{filter}");
		request.pathParameter("filter", "id=='"+protocol.getId()+"'")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals(1, response.getEntity(new GenericType<Collection<Protocol>>(){}).size());
		response.releaseConnection();
		request = new ClientRequest(url + "/protocol/delete/{id}");
		request.pathParameter("id", protocol.getId());
		response = request.delete();
		assertEquals(200, response.getStatus());
		response.releaseConnection();
		request = new ClientRequest(url + "/protocol/show/{filter}");
		request.pathParameter("filter", "id=='"+protocol.getId()+"'")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals(0, response.getEntity(new GenericType<Collection<Protocol>>(){}).size());
		response.releaseConnection();
	}

}
