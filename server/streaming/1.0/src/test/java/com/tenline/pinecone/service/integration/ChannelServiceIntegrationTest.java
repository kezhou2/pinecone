/**
 * 
 */
package com.tenline.pinecone.service.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.model.Event;

/**
 * @author Bill
 *
 */
public class ChannelServiceIntegrationTest {
	
	private String url;
	private Event event;
	private ClientRequest request;
	private ClientResponse<?> response;

	@Before
	public void testSetup() {
		url = "http://localhost:8080/api";
	}
	
	@After
	public void testShutdown() {
		url = null;
		event = null;
		request = null;
		response = null;
	}
	
	@Test
	public void testSubscribe() throws Exception {
		request = new ClientRequest(url + "/channel/subscribe");
		request.body(MediaType.APPLICATION_JSON, "{\"event\":{\"subject\":\"/test\"}}")
			   .accept(MediaType.APPLICATION_JSON);
		response = request.post();
		assertEquals(200, response.getStatus());
		event = response.getEntity(Event.class);
		System.out.println(event.getToken());
		assertNotNull(event.getToken());
		response.releaseConnection();
	}
	
	@Test
	public void testPublish() throws Exception {
		request = new ClientRequest(url + "/channel/publish");
		request.body(MediaType.APPLICATION_JSON, "{\"event\":{\"subject\":\"/test\",\"message\":\"hello!\"}}");
		response = request.post();
		assertEquals(200, response.getStatus());
		response.releaseConnection();
	}

}
