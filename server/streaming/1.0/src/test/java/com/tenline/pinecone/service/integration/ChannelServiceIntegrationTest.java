/**
 * 
 */
package com.tenline.pinecone.service.integration;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Bill
 *
 */
public class ChannelServiceIntegrationTest {
	
	private String url;
	private String subject;
	private String message;
	private ClientRequest request;
	private ClientResponse<?> response;
	
	@Before
	public void testSetup() {
		url = "http://localhost:8080";
		subject = "test";
		message = "12";
	}
	
	@After
	public void testShutdown() {
		url = null;
		subject = null;
		message = null;
		request = null;
		response = null;
	}
	
	@Test
	public void testSubscribeAndPublish() throws Exception {
		request = new ClientRequest(url + "/api/channel/publish/{subject}");
		request.pathParameter("subject", subject).body(MediaType.TEXT_PLAIN, message);
		response = request.post();
		assertEquals(200, response.getStatus());
		response.releaseConnection();
		request = new ClientRequest(url + "/api/channel/subscribe/{subject}");
		request.pathParameter("subject", subject);
		response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals(message, response.getEntity(String.class));
		response.releaseConnection();
	}

}
