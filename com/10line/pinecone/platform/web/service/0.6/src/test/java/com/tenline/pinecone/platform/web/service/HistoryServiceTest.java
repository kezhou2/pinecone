/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.sdk.HistoryClient;

/**
 * @author Bill
 *
 */
public class HistoryServiceTest extends AbstractServiceTest {
	
	private String id = "Test";
	private Random random;
	private HistoryClient client;

	@Before
	public void testSetup() throws Exception {
		client = new HistoryClient("www.pinecone.cc");
		random = new Random();
	}
	
	@After
	public void testShutdown() throws Exception {
		client.disconnect();
		client = null;
		random = null;
		id = null;
	}
	
	@Test
	public void test() throws Exception {
		Date now = new Date();
		client.setValue(id, now, String.valueOf(random.nextInt(50)));
		assertNotNull(client.getValue(id, now));
		for (String date : client.getDates(id)) {
			logger.info("History Date: " + date); assertNotNull(date);
		}
		for (String value : client.getValues(id)) {
			logger.info("History Value: " + value); assertNotNull(value);
		}
		client.deleteValue(id, now);
		assertNull(client.getValue(id, now));
	}

}
