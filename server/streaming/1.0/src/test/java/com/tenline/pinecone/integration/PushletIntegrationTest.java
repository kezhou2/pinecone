/**
 * 
 */
package com.tenline.pinecone.integration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nl.justobjects.pushlet.client.PushletClient;
import nl.justobjects.pushlet.client.PushletClientListener;
import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.util.PushletException;

/**
 * @author Bill
 *
 */
public class PushletIntegrationTest implements PushletClientListener {

	private String subject;
	private Random random;
	private PushletClient subscriber;
	private PushletClient publisher;
	
	@Before
	public void testSetup() throws PushletException {
		subject = "/test";
		random = new Random();
		subscriber = new PushletClient("localhost", 8080);
		subscriber.joinListen(this, MODE_STREAM, FORMAT_XML, subject);
		publisher = new PushletClient("localhost", 8080);
		publisher.join();
	}
	
	@After
	public void testShutdown() throws PushletException {
		subject = null;
		random = null;
		subscriber.leave();
		publisher.leave();
	}
	
	@Test
	public void testPublish() throws PushletException, InterruptedException, UnsupportedEncodingException {
		for (int i=0; i<5; i++) {
			HashMap<String, String> eventData = new HashMap<String, String>();
			byte[] bytes = new byte[10];
			random.nextBytes(bytes);
			eventData.put("bytes", URLEncoder.encode(new String(bytes), "UTF-8"));
			publisher.publish(subject, eventData);
		}
	}

	/* (non-Javadoc)
	 * @see nl.justobjects.pushlet.client.PushletClientListener#onAbort(nl.justobjects.pushlet.core.Event)
	 */
	@Override
	public void onAbort(Event theEvent) {
		// TODO Auto-generated method stub
		System.out.println("======Abort");
	}

	/* (non-Javadoc)
	 * @see nl.justobjects.pushlet.client.PushletClientListener#onData(nl.justobjects.pushlet.core.Event)
	 */
	@Override
	public void onData(Event theEvent) {
		// TODO Auto-generated method stub
		System.out.println("======Data");
		System.out.println(theEvent.getField("bytes"));
	}

	/* (non-Javadoc)
	 * @see nl.justobjects.pushlet.client.PushletClientListener#onHeartbeat(nl.justobjects.pushlet.core.Event)
	 */
	@Override
	public void onHeartbeat(Event theEvent) {
		// TODO Auto-generated method stub
		System.out.println("======Heartbeat");
	}

	/* (non-Javadoc)
	 * @see nl.justobjects.pushlet.client.PushletClientListener#onError(java.lang.String)
	 */
	@Override
	public void onError(String message) {
		// TODO Auto-generated method stub
		System.out.println("======Error");
	}

}
