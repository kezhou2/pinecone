///**
// * 
// */
//package com.tenline.pinecone.platform.web.service;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//
//import java.util.UUID;
//import java.util.logging.Level;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.tenline.pinecone.platform.model.Authority;
//import com.tenline.pinecone.platform.model.Device;
//import com.tenline.pinecone.platform.model.Link;
//import com.tenline.pinecone.platform.model.User;
//import com.tenline.pinecone.platform.sdk.RESTClient;
//
///**
// * @author Bill
// *
// */
//public class RESTServiceTest extends AbstractServiceTest {
//	
//	private User user;
//	private Authority authority;
//	private Device device;
//	private RESTClient client;
//	
//	@Before
//	public void testSetup() throws Exception {
//		user = new User();
//		user.setName("bill");
//		user.setEmail("billmse@gmail.com");
//		user.setPassword("19821027");
//		authority = new Authority();
//		authority.setAuthority("ROLE_USER");
//		authority.setUserName("bill");
//		device = new Device();
//		device.setName("ATM");
//		device.setCode(UUID.randomUUID().toString());
//		client = new RESTClient("http://localhost:8080/service");
//	}
//	
//	@After
//	public void testShutdown() throws Exception {
//		user = null;
//		authority = null;
//		device = null;
//		client = null;
//	}
//	
//	@Test
//	public void test() throws Exception {
//		user.setId(Long.valueOf(client.post("/user", user)));
//		authority.setId(Long.valueOf(client.post("/authority", authority)));
//		logger.log(Level.INFO, "---------- User Test Case ----------");
//		user = (User) client.get("/user/" + user.getId(), user.getName(), user.getPassword()).toArray()[0];
//		assertNotNull(user.getId());
//		logger.log(Level.INFO, user.getId().toString());
//		assertEquals("bill", user.getName());
//		logger.log(Level.INFO, user.getName());
//		assertEquals("billmse@gmail.com", user.getEmail());
//		logger.log(Level.INFO, user.getEmail());
//		assertNull(user.getPassword());
//		for (Link link : user.get_links()) {
//			logger.log(Level.INFO, "--------------------");
//			assertNotNull(link.getRel());
//			logger.log(Level.INFO, link.getRel());
//			assertNotNull(link.getHref());
//			logger.log(Level.INFO, link.getHref());
//			logger.log(Level.INFO, "--------------------");
//		}
//		user.setEmail("bill_mse@163.com");
//		logger.log(Level.INFO, client.put("/user/" + user.getId(), user));
//		user = (User) client.get("/user/" + user.getId(), user.getName(), "19821027").toArray()[0];
//		assertEquals("bill", user.getName());
//		logger.log(Level.INFO, user.getName());
//		assertEquals("bill_mse@163.com", user.getEmail());
//		logger.log(Level.INFO, user.getEmail());
//		assertNull(user.getPassword());
//		user.setPassword("123456");
//		logger.log(Level.INFO, client.post("/user/" + user.getId(), user));// Password is updated (exported = false)
//		user = (User) client.get("/user/" + user.getId(), user.getName(), user.getPassword()).toArray()[0];
//		assertEquals("bill", user.getName());
//		logger.log(Level.INFO, user.getName());
//		assertEquals("bill_mse@163.com", user.getEmail());
//		logger.log(Level.INFO, user.getEmail());
//		assertNull(user.getPassword());
//		logger.log(Level.INFO, "----------- Authority Test Case ---------");
//		authority = (Authority) client.get("/authority/" + authority.getId(), "admin", "admin").toArray()[0];
//		assertNotNull(authority.getId());
//		logger.log(Level.INFO, authority.getId().toString());
//		assertEquals("ROLE_USER", authority.getAuthority());
//		logger.log(Level.INFO, authority.getAuthority());
//		assertNull(authority.getUserName());
//		for (Link link : authority.get_links()) {
//			logger.log(Level.INFO, "--------------------");
//			assertNotNull(link.getRel());
//			logger.log(Level.INFO, link.getRel());
//			assertNotNull(link.getHref());
//			logger.log(Level.INFO, link.getHref());
//			logger.log(Level.INFO, "--------------------");
//		}
//		authority.setUserName("bill");
//		authority.setAuthority("ROLE_ADMIN");
//		logger.log(Level.INFO, client.post("/authority/" + authority.getId(), authority));// UserName is updated (exported = false)
//		authority = (Authority) client.get("/authority/" + authority.getId(), "admin", "admin").toArray()[0];
//		logger.log(Level.INFO, authority.getId().toString());
//		assertEquals("ROLE_ADMIN", authority.getAuthority());
//		logger.log(Level.INFO, authority.getAuthority());
//		assertNull(authority.getUserName());
//		logger.log(Level.INFO, client.post("/authority/" + authority.getId() + "/user", "/user/" + user.getId()));	
//		logger.log(Level.INFO, "---------- Device Test Case ----------");
//		device.setId(Long.valueOf(client.post("/device", device)));
//		device = (Device) client.get("/device/" + device.getId(), user.getName(), "123456").toArray()[0];
//		assertNotNull(device.getId());
//		logger.log(Level.INFO, device.getId().toString());
//		assertEquals("ATM", device.getName());
//		logger.log(Level.INFO, device.getName());
//		assertNotNull(device.getCode());
//		logger.log(Level.INFO, device.getCode());
//		for (Link link : device.get_links()) {
//			logger.log(Level.INFO, "--------------------");
//			assertNotNull(link.getRel());
//			logger.log(Level.INFO, link.getRel());
//			assertNotNull(link.getHref());
//			logger.log(Level.INFO, link.getHref());
//			logger.log(Level.INFO, "--------------------");
//		}
//		device.setName("FM");
//		logger.log(Level.INFO, client.put("/device/" + device.getId(), device));
//		device = (Device) client.get("/device/" + device.getId(), user.getName(), "123456").toArray()[0];
//		logger.log(Level.INFO, device.getId().toString());
//		assertEquals("FM", device.getName());
//		logger.log(Level.INFO, device.getName());
//		assertNotNull(device.getCode());
//		logger.log(Level.INFO, device.getCode());
//		logger.log(Level.INFO, client.post("/device/" + device.getId() + "/user", "/user/" + user.getId()));	
//		logger.log(Level.INFO, "----------- Test Case End ---------");
//		assertEquals(1, client.get("/user/" + user.getId() + "/devices", user.getName(), "123456").size());
//		assertEquals(1, client.get("/user/" + user.getId() + "/authorities", user.getName(), "123456").size());
//		logger.log(Level.INFO, client.delete("/user/" + user.getId()));	
//		// Authentication request for failed, because user has been deleted
//		assertEquals(0, client.get("/user", user.getName(), "123456").size());
//		assertEquals(0, client.get("/authority", user.getName(), "123456").size());
//		assertEquals(0, client.get("/device", user.getName(), "123456").size());
//		// The correct result
//		assertEquals(1, client.get("/user", "admin", "admin").size());
//		assertEquals(1, client.get("/authority", "admin", "admin").size());
//		assertEquals(0, client.get("/device", "admin", "admin").size());
//	}
//
//}
