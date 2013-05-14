//package cc.pinecone.renren.devicecontroller.dao;
//
//import static org.junit.Assert.fail;
//
//import java.util.ArrayList;
//import java.util.UUID;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import cc.pinecone.renren.devicecontroller.controller.AppConfig;
//
//import com.tenline.pinecone.platform.model.Authority;
//import com.tenline.pinecone.platform.model.Device;
//import com.tenline.pinecone.platform.model.Entity;
//import com.tenline.pinecone.platform.model.Item;
//import com.tenline.pinecone.platform.model.User;
//import com.tenline.pinecone.platform.model.Variable;
//import com.tenline.pinecone.platform.sdk.RESTClient;
//
//public class PineconeApiTest {
//
//	private PineconeApi api;
//	private RenrenApi rapi;
////	private User user;
//	@Before
//	public void setUp() throws Exception {
//		api = new PineconeApi();
//		rapi = new RenrenApi();
//		
//		//init data
////		User user = new User();
////		user.setName("liugy");
////		user.setEmail("liugy503@gmail.com");
////		user.setPassword("198297");
////		Authority authority = new Authority();
////		authority.setAuthority("ROLE_USER");
////		authority.setUserName("liugy");
////		Device device = new Device();
////		device.setName("ATM");
////		device.setCode(UUID.randomUUID().toString());
////		
////		RESTClient client = new RESTClient(AppConfig.BASE_URL);
////		user.setId(Long.valueOf(client.post("/user", user)));
////		authority.setId(Long.valueOf(client.post("/authority", authority)));
////		
////		device.setId(Long.valueOf(client.post("/device", device)));
////		System.out.println(client.post("/device/"+device.getId()+"/user", "/user/"+user.getId()));
////		
////		{
////			Variable var = new Variable();
////			var.setName("param1");
////			var.setType(Variable.WRITE);
////			var.setId(Long.valueOf(client.post("/variable", var)));
////			System.out.println(client.post("/variable/"+var.getId()+"/device", "/device/"+device.getId()));
////			{
////				Item it1 = new Item();
////				it1.setValue("item_value_1");
////				it1.setId(Long.valueOf(client.post("/item/", it1)));
////				System.out.println(client.post("/item/"+it1.getId()+"/variable", "/variable/"+var.getId()));
////			}
////			{
////				Item it2 = new Item();
////				it2.setValue("item_value_2");
////				it2.setId(Long.valueOf(client.post("/item/", it2)));
////				System.out.println(client.post("/item/"+it2.getId()+"/variable", "/variable/"+var.getId()));
////			}
////		}
////		
////		{
////			Variable var = new Variable();
////			var.setName("param1");
////			var.setType(Variable.READ);
////			var.setId(Long.valueOf(client.post("/variable", var)));
////			System.out.println(client.post("/variable/"+var.getId()+"/device", "/device/"+device.getId()));
////			{
////				Item it = new Item();
////				it.setValue("item_value_3");
////				it.setId(Long.valueOf(client.post("/item/", it)));
////				System.out.println(client.post("/item/"+it.getId()+"/variable", "/variable/"+var.getId()));
////			}
////			{
////				Item it = new Item();
////				it.setValue("item_value_4");
////				it.setId(Long.valueOf(client.post("/item/", it)));
////				System.out.println(client.post("/item/"+it.getId()+"/variable", "/variable/"+var.getId()));
////			}
////		}
//	}
//
//	@Test
//	public void testLogin() {
//		User user = api.login("liugy", "198297");
//		Assert.assertEquals("liugy", user.getName());
//	}
//
//	@Test
//	public void testGetUserDevices() {
//		User user = api.login("liugy", "198297");
//		user.setPassword("198297");
//		ArrayList<Device> list = api.getUserDevices(user);
//		Assert.assertEquals(1, list.size());
//	}
//
//	@Test
//	public void testGetDeviceVariables() {
//		User user = api.login("liugy", "198297");
//		user.setPassword("198297");
//		
//		ArrayList<Device> list = api.getUserDevices(user);
//		for(Device dev:list){
//			ArrayList<Variable> vars = api.getDeviceVariables(dev);
//			Assert.assertEquals(1, vars.size());
//		}
//		
//	}
//
//	@Test
//	public void testGetVariableItems() {
//		User user = api.login("liugy", "198297");
//		user.setPassword("198297");
//		
//		ArrayList<Device> list = api.getUserDevices(user);
//		for(Device dev:list){
//			ArrayList<Variable> vars = api.getDeviceVariables(dev);
//			
//			for(Variable var:vars){
//				ArrayList<Item> its = api.getVariableItems(var);
//				Assert.assertEquals(2, its.size());
//			}
//		}
//	}
//
//	@Test
//	public void testPublish() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSubscribe() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testIsExist() {
//		Assert.assertNull(api.isExist("liugy1"));
//		Assert.assertNotNull(api.isExist("liugy"));
//	}
//
//	@Test
//	public void testGetUser() {
//		Assert.assertNotNull(api.getUser("liugy"));
//	}
//
//	@Test
//	public void testRegister() {
//		User user = api.register("test100", "sdsd", "sads@gmail.com");
//		Assert.assertNotNull(user);
//		RESTClient client = new RESTClient(AppConfig.BASE_URL);
//		try {
//			System.out.println(client.delete("/user/" + user.getId()));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}
//
//	@Test
//	public void testActiveDevice() {
//		fail("Not yet implemented");
//	}
//
//}
