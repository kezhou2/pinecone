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
import com.tenline.pinecone.model.Item;
import com.tenline.pinecone.model.User;
import com.tenline.pinecone.model.Variable;

/**
 * @author Bill
 *
 */
public class ItemServiceIntegrationTest extends AbstractServiceIntegrationTest {

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
		request = new ClientRequest(url + "/item/create");
		request.body(MediaType.APPLICATION_JSON, "{\"item\":{\"text\":\"A\",\"value\":\"0\",\"variable\":{\"id\":\""+variable.getId()+"\"}}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.post();
		assertEquals(200, response.getStatus());
		Item item = response.getEntity(Item.class);
		assertEquals("A", item.getText());
		assertEquals("0", item.getValue());
		response.releaseConnection();
		request = new ClientRequest(url + "/item/update");
		request.body(MediaType.APPLICATION_JSON, "{\"item\":{\"id\":\""+item.getId()+"\",\"text\":\"B\",\"value\":\"1\"}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.put();
		assertEquals(200, response.getStatus());
		item = response.getEntity(Item.class);
		assertEquals("B", item.getText());
		assertEquals("1", item.getValue());
		response.releaseConnection();
		request = new ClientRequest(url + "/item/show/{filter}");
		request.pathParameter("filter", "id=='"+item.getId()+"'")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals(1, response.getEntity(new GenericType<Collection<Item>>(){}).size());
		response.releaseConnection();
		request = new ClientRequest(url + "/item/delete/{id}");
		request.pathParameter("id", item.getId());
		response = request.delete();
		assertEquals(200, response.getStatus());
		response.releaseConnection();
		request = new ClientRequest(url + "/item/show/{filter}");
		request.pathParameter("filter", "id=='"+item.getId()+"'")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals(0, response.getEntity(new GenericType<Collection<Item>>(){}).size());
		response.releaseConnection();
	}

}
