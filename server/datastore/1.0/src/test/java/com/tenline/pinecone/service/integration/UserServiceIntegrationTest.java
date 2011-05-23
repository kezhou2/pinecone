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

import com.tenline.pinecone.model.User;

/**
 * @author Bill
 *
 */
public class UserServiceIntegrationTest extends AbstractServiceIntegrationTest {

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
		request = new ClientRequest(url + "/user/update");
		request.body(MediaType.APPLICATION_JSON, "{\"user\":{\"id\":\""+user.getId()+"\",\"snsId\":\"251417333\"}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.put();
		assertEquals(200, response.getStatus());
		user = response.getEntity(User.class);
		assertEquals("251417333", user.getSnsId());
		response.releaseConnection();
		request = new ClientRequest(url + "/user/show/{filter}");
		request.pathParameter("filter", "snsId=='"+user.getSnsId()+"'")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals(1, response.getEntity(new GenericType<Collection<User>>(){}).size());
		response.releaseConnection();
		request = new ClientRequest(url + "/user/delete/{id}");
		request.pathParameter("id", user.getId());
		response = request.delete();
		assertEquals(200, response.getStatus());
		response.releaseConnection();
		request = new ClientRequest(url + "/user/show/{filter}");
		request.pathParameter("filter", "id=='"+user.getId()+"'")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals(0, response.getEntity(new GenericType<Collection<User>>(){}).size());
		response.releaseConnection();
	}

}
