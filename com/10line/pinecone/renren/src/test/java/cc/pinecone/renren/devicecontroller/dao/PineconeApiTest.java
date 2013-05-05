package cc.pinecone.renren.devicecontroller.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cc.pinecone.renren.devicecontroller.controller.AppConfig;

import com.tenline.pinecone.platform.model.Authority;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.RESTClient;

public class PineconeApiTest {

	private PineconeApi api;
//	private User user;
	@Before
	public void setUp() throws Exception {
		api = new PineconeApi();
//		user = new User();
//		user.setName("liugy");
//		user.setEmail("liugy503@gmail.com");
//		user.setPassword("198297");
//		Authority authority = new Authority();
//		authority.setAuthority("ROLE_USER");
//		authority.setUserName("liugy");
//		Device device = new Device();
//		device.setName("ATM");
//		device.setCode(UUID.randomUUID().toString());
	}

	@Test
	public void testLogin() {
		User user = api.login("liugy", "198297");
		Assert.assertEquals("liugy", user.getName());
	}

	@Test
	public void testGetUserDevices() {
		User user = api.login("liugy", "198297");
		
		Device device = new Device();
		device.setName("ATM");
		device.setCode(UUID.randomUUID().toString());
		
		RESTClient client = new RESTClient(AppConfig.BASE_URL);
		try {
			long id = Long.valueOf(client.post("/device", device));
			System.out.println(client.post("/device/" + id + "/user", "/user/" + user.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<Device> list = api.getUserDevices(user);
		Assert.assertEquals(0, list);
	}

	@Test
	public void testGetDeviceVariables() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVariableItems() {
		fail("Not yet implemented");
	}

	@Test
	public void testPublish() {
		fail("Not yet implemented");
	}

	@Test
	public void testSubscribe() {
		fail("Not yet implemented");
	}

	@Test
	public void testTest() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsExist() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testRegister() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRenrenUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testActiveDevice() {
		fail("Not yet implemented");
	}

}
