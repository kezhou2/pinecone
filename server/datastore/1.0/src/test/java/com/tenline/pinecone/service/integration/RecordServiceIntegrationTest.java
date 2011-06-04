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
import com.tenline.pinecone.model.Record;
import com.tenline.pinecone.model.User;
import com.tenline.pinecone.model.Variable;

/**
 * @author Bill
 *
 */
public class RecordServiceIntegrationTest extends AbstractServiceIntegrationTest {

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
		request.body(MediaType.APPLICATION_JSON, "{\"device\":{\"name\":\"LNB\",\"version\":\"1.1\",\"user\":{\"id\":\""+user.getId()+"\"}}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.post();
		assertEquals(200, response.getStatus());
		Device device = response.getEntity(Device.class);
		assertEquals("LNB", device.getName());
		assertEquals("1.1", device.getVersion());
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
		request = new ClientRequest(url + "/record/create");
		request.body(MediaType.APPLICATION_JSON, "{\"record\":{\"value\":\"0\",\"variable\":{\"id\":\""+variable.getId()+"\"}}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.post();
		assertEquals(200, response.getStatus());
		Record record = response.getEntity(Record.class);
		assertEquals("0", record.getValue());
		response.releaseConnection();
		request = new ClientRequest(url + "/record/update");
		request.body(MediaType.APPLICATION_JSON, "{\"record\":{\"id\":\""+record.getId()+"\",\"value\":\"1\"}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.put();
		assertEquals(200, response.getStatus());
		record = response.getEntity(Record.class);
		assertEquals("1", record.getValue());
		response.releaseConnection();
		request = new ClientRequest(url + "/record/show/{filter}");
		request.pathParameter("filter", "id=='"+record.getId()+"'")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals(1, response.getEntity(new GenericType<Collection<Record>>(){}).size());
		response.releaseConnection();
		request = new ClientRequest(url + "/record/delete/{id}");
		request.pathParameter("id", record.getId());
		response = request.delete();
		assertEquals(200, response.getStatus());
		response.releaseConnection();
		request = new ClientRequest(url + "/record/show/{filter}");
		request.pathParameter("filter", "id=='"+record.getId()+"'")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals(0, response.getEntity(new GenericType<Collection<Record>>(){}).size());
		response.releaseConnection();
	}

}
