/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.sdk.StaticResourceAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class StaticResourceServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private String id;
	
	private StaticResourceAPI resourceAPI;
	
	@Before
	public void testSetup() {
		super.testSetup();
		id = "4028818233c63d000133c63d1d40000e";
		resourceAPI = new StaticResourceAPI("localhost", "8080", "service");
	}
	
	@After
	public void testShutdown() {
		id = null;
		resourceAPI = null;
		super.testShutdown();
	}
	
	@Test
	public void testUploadIcon() throws Exception {
		FileInputStream input= new FileInputStream(new File("D:\\Picture\\png\\favorite.png"));
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int data;
		while ((data = input.read()) != -1) {
			output.write(data);	
		}
		output.flush();
		output.close();
		APIResponse response = resourceAPI.uploadIcon(id, output.toByteArray());
		if (response.isDone()) {
			assertEquals("Upload Icon Successful!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}
	
	@Test
	public void testDownloadIcon() throws Exception {
		APIResponse response = resourceAPI.downloadIcon(id, consumerKey, consumerSecret, token, tokenSecret);
		if (response.isDone()) {
			assertNotNull(response.getMessage());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
