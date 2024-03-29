/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

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
public class ChannelServiceTest extends AbstractServiceTest {
	
	private String subject;
	
	private ChannelAPI channelAPI;
	
	@Before
	public void testSetup() throws Exception {
		subject = "test";
		channelAPI = new ChannelAPI(HOST, PORT, CONTEXT);
	}
	
	@After
	public void testShutdown() throws Exception {
		subject = null;
		channelAPI = null;
	}
	
	@Test
	public void test() throws Exception {
		APIResponse response = channelAPI.publish(subject, MediaType.TEXT_PLAIN, "Hello World".getBytes());
		if (response.isDone()) {
			assertEquals("Publish Successful!", response.getMessage().toString());
			response = channelAPI.subscribe(subject);
			if (response.isDone()) {
				assertEquals("Hello World", new String((byte[]) response.getMessage()));
			} else {
				logger.log(Level.SEVERE, response.getMessage().toString());
			}
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
