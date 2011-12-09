/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.logging.Level;

import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.sdk.development.ChannelAPI;

/**
 * @author Bill
 *
 */
public class ChannelServiceIntegrationTest extends AuthorizationServiceIntegrationTest {
	
	private String subject;
	
	private ChannelAPI channelAPI;
	
	@Before
	public void testSetup() throws Exception {
		super.testSetup();
		subject = "test";
		channelAPI = new ChannelAPI("localhost", "8888", "service");
	}
	
	@After
	public void testShutdown() throws Exception {
		subject = null;
		channelAPI = null;
		super.testShutdown();
	}
	
	@Test
	public void testPublish() throws Exception {
		APIResponse response = channelAPI.publish(subject, MediaType.TEXT_PLAIN, "Hello World".getBytes(),
				consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals("Publish Successful!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}
	
	@Test
	public void testSubscribe() throws Exception {
		APIResponse response = channelAPI.subscribe(subject, consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertEquals("Hello World", new String((byte[]) response.getMessage()));
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
