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

import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.ChannelAPI;

/**
 * @author Bill
 *
 */
public class ChannelServiceIntegrationTest extends AbstractServiceIntegrationTest {
	
	private String subject;
	
	private ChannelAPI channelAPI;
	
	@Before
	public void testSetup() {
		subject = "test";
	}
	
	@After
	public void testShutdown() {
		subject = null;
		channelAPI = null;
	}
	
	@Test
	public void testPublish() throws Exception {
		channelAPI = new ChannelAPI("localhost", "8888", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals("Publish Successful!", message.toString());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		channelAPI.publish(subject, MediaType.TEXT_PLAIN, "Hello World".getBytes());
	}
	
	@Test
	public void testSubscribe() throws Exception {
		channelAPI = new ChannelAPI("localhost", "8888", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals("Hello World", new String((byte[]) message));
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		channelAPI.subscribe(subject);
	}

}
