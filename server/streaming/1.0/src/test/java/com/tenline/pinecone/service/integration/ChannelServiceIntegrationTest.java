/**
 * 
 */
package com.tenline.pinecone.service.integration;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.PineconeAPIListener;
import com.tenline.pinecone.PineconeChannelAPI;

/**
 * @author Bill
 *
 */
public class ChannelServiceIntegrationTest {
	
	private String subject;
	
	private PineconeChannelAPI channelAPI;
	
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
		channelAPI = new PineconeChannelAPI("localhost", "8080", new PineconeAPIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals("Publish Successful!", message.toString());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		channelAPI.publish(subject, MediaType.TEXT_PLAIN, "Hello World".getBytes());
	}
	
	@Test
	public void testSubscribe() throws Exception {
		channelAPI = new PineconeChannelAPI("localhost", "8080", new PineconeAPIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals("Hello World", new String((byte[]) message));
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		channelAPI.subscribe(subject);
	}

}
