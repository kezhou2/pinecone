/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.Link;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.RESTClient;

/**
 * @author Bill
 *
 */
public class RESTServiceTest extends AbstractServiceTest {
	
	private User user;
	private Device device;
	private RESTClient client;
	
	@Before
	public void testSetup() throws Exception {
		user = new User();
		user.setName("bill");
		user.setEmail("billmse@gmail.com");
		user.setPassword("19821027");
		device = new Device();
		device.setName("ATM");
		device.setCode(UUID.randomUUID().toString());
		client = new RESTClient(URL);
	}
	
	@After
	public void testShutdown() throws Exception {
		user = null;
		device = null;
		client = null;
	}
	
	@Test
	public void test() throws Exception {
		logger.log(Level.INFO, client.post("/user", user));
		logger.log(Level.INFO, client.post("/device", device));
		for (Entity entity : client.get("/user")) {
			User user = (User) entity;
			logger.log(Level.INFO, "--------------------");
			assertNotNull(user.getId());
			logger.log(Level.INFO, user.getId().toString());
			assertEquals("bill", user.getName());
			logger.log(Level.INFO, user.getName());
			assertEquals("billmse@gmail.com", user.getEmail());
			logger.log(Level.INFO, user.getEmail());
			assertEquals("19821027", user.getPassword());
			logger.log(Level.INFO, user.getPassword());
			for (Link link : user.get_links()) {
				logger.log(Level.INFO, "--------------------");
				logger.log(Level.INFO, link.getRel());
				logger.log(Level.INFO, link.getHref());
				logger.log(Level.INFO, "--------------------");
			}
			this.user.setId(user.getId());
			this.user.setName("Jack");
			this.user.setEmail("bill_mse@163.com");
			this.user.setPassword("123456");
			logger.log(Level.INFO, client.put("/user/" + user.getId(), this.user));
			user = (User) client.get("/user/" + user.getId()).toArray()[0];
			assertEquals("Jack", user.getName());
			logger.log(Level.INFO, user.getName());
			assertEquals("bill_mse@163.com", user.getEmail());
			logger.log(Level.INFO, user.getEmail());
			assertEquals("123456", user.getPassword());
			logger.log(Level.INFO, user.getPassword());
			logger.log(Level.INFO, "--------------------");
		}
		for (Entity entity : client.get("/device")) {
			Device device = (Device) entity;
			logger.log(Level.INFO, "--------------------");
			assertNotNull(device.getId());
			logger.log(Level.INFO, device.getId().toString());
			assertEquals("ATM", device.getName());
			logger.log(Level.INFO, device.getName());
			assertNotNull(device.getCode());
			logger.log(Level.INFO, device.getCode());
			for (Link link : device.get_links()) {
				logger.log(Level.INFO, "--------------------");
				logger.log(Level.INFO, link.getRel());
				logger.log(Level.INFO, link.getHref());
				logger.log(Level.INFO, "--------------------");
			}
			this.device.setName("FM");
			this.device.setCode("123456");
			logger.log(Level.INFO, client.put("/device/" + device.getId(), this.device));
			device = (Device) client.get("/device/" + device.getId()).toArray()[0];
			logger.log(Level.INFO, device.getId().toString());
			assertEquals("FM", device.getName());
			logger.log(Level.INFO, device.getName());
			assertEquals("123456", device.getCode());
			logger.log(Level.INFO, device.getCode());
			logger.log(Level.INFO, client.put("/user/" + user.getId() + "/devices", "/device/" + device.getId()));	
			logger.log(Level.INFO, "--------------------");
		}
		assertEquals(1, client.get("/user/" + user.getId() + "/devices").size());
		logger.log(Level.INFO, client.delete("/user/" + user.getId()));	
		assertEquals(0, client.get("/user").size());
		assertEquals(0, client.get("/device").size());
	}

}
