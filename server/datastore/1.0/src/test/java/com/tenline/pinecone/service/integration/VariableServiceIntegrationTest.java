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
import com.tenline.pinecone.model.Variable;

/**
 * @author Bill
 *
 */
public class VariableServiceIntegrationTest extends AbstractServiceIntegrationTest {

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
		request = new ClientRequest(url + "/variable/create");
		request.body(MediaType.APPLICATION_JSON, "{\"variable\":{\"name\":\"A\",\"type\":\"read_only\",\"device\":{\"id\":\""+device.getId()+"\"}}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.post();
		assertEquals(200, response.getStatus());
		Variable variable = response.getEntity(Variable.class);
		assertEquals("A", variable.getName());
		assertEquals("read_only", variable.getType());
		response.releaseConnection();
		request = new ClientRequest(url + "/variable/update");
		request.body(MediaType.APPLICATION_JSON, "{\"variable\":{\"id\":\""+variable.getId()+"\",\"name\":\"B\",\"type\":\"write_only\"}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.put();
		assertEquals(200, response.getStatus());
		variable = response.getEntity(Variable.class);
		assertEquals("B", variable.getName());
		assertEquals("write_only", variable.getType());
		response.releaseConnection();
		request = new ClientRequest(url + "/variable/show/{filter}");
		request.pathParameter("filter", "id=='"+variable.getId()+"'")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals(1, response.getEntity(new GenericType<Collection<Variable>>(){}).size());
		response.releaseConnection();
		request = new ClientRequest(url + "/variable/delete/{id}");
		request.pathParameter("id", variable.getId());
		response = request.delete();
		assertEquals(200, response.getStatus());
		response.releaseConnection();
		request = new ClientRequest(url + "/variable/show/{filter}");
		request.pathParameter("filter", "id=='"+variable.getId()+"'")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals(0, response.getEntity(new GenericType<Collection<Variable>>(){}).size());
		response.releaseConnection();
	}

}
