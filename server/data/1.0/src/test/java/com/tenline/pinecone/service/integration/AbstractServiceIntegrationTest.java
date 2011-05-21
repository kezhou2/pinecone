/**
 * 
 */
package com.tenline.pinecone.service.integration;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.After;
import org.junit.Before;

/**
 * @author Bill
 *
 */
public abstract class AbstractServiceIntegrationTest {
	
	protected String url;

	protected ClientRequest request;
	
	protected ClientResponse<?> response;
	
	@Before
	public void testSetup() {
		url = "http://localhost:8080/api";
	}
	
	@After
	public void testShutdown() {	
		url = null;
		request = null;
		response = null;
	}

}
