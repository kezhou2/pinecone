/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.Protocol;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.sdk.ChannelClient;
import com.tenline.pinecone.platform.sdk.ChannelClientListener;

/**
 * @author Bill
 *
 */
public class ChannelServiceTest extends AbstractServiceTest {
	
	private String subject = "test";
	private Map<String, String> theAttributes = new HashMap<String, String>();
	private ChannelClient client;
	
	@Before
	public void testSetup() throws Exception {
		theAttributes.put("hello", "world");
		client = new ChannelClient(URL);
		client.setUsername("admin");
		client.setPassword("admin");
		client.setDebug(true);
		client.join();
		client.listen(new ChannelClientListener() {

			@Override
			public void onAbort(Event theEvent) {
				// TODO Auto-generated method stub
				logger.log(Level.INFO, theEvent.toString());
			}

			@Override
			public void onData(Event theEvent) {
				// TODO Auto-generated method stub
				logger.log(Level.INFO, "onData -- " + theEvent.toXML());
				assertEquals("world", theEvent.getField("hello"));
			}

			@Override
			public void onHeartbeat(Event theEvent) {
				// TODO Auto-generated method stub
				logger.log(Level.INFO, "onHeartBeat -- " + theEvent.toString());
			}

			@Override
			public void onError(String message) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, message);
			}
			
		}, Protocol.MODE_STREAM, subject);
	}
	
	@After
	public void testShutdown() throws Exception {
		subject = null;
		theAttributes.clear();
		theAttributes = null;
		client.leave();
		client = null;	
	}
	
	@Test
	public void test() throws Exception {
		client.publish(subject, theAttributes);
	}

}
