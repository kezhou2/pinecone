package cc.pinecone.renren.devicecontroller.controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.tenline.pinecone.platform.model.Authority;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.ChannelClient;
import com.tenline.pinecone.platform.sdk.RESTClient;

public class TestAPI {

	public static final String baseUrl = "http://pinecone-service.cloudfoundry.com";
	
	private User user;
	private Authority authority;
	private Device device;
	private RESTClient client;
	/**
	 * @param liugy
	 */
	public static void main(String[] args) {
		TestAPI api = new TestAPI();
//		api.testCreateData();
		try {
//			api.getAllDevice();
//			api.createUser();
//			api.test();
//			api.activeDevice("26", "19");
//			api.getUserData("liugy");
//			api.createDevice();
//			api.getAllDevice();
//			api.getAllUser();
//			api.getAllUser();
			
//			api.testChannel();
//			api.test();
			
//			api.modifyUser();
			
			String a = "abcdefg";
			
			System.out.println(a.indexOf("cde", 0));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public TestAPI(){
		user = new User();
		user.setName("liugy");
		user.setEmail("liugyang@gmail.com");
		user.setPassword("198297");
		authority = new Authority();
		authority.setAuthority("ROLE_USER");
		authority.setUserName("liugy");
		device = new Device();
		device.setName("Device for Test");
		device.setCode(UUID.randomUUID().toString());
		client = new RESTClient(baseUrl);
	}
	
	public void modifyUser() throws Exception{
		User user = (User) client.get("/user/21", "admin", "admin").toArray()[0];
		user.setPassword("liugy");
		System.out.println(client.post("/user/"+user.getId(), user));
	}
	
	public void test() throws Exception{
//		user.setId(Long.valueOf(client.post("/user", user)));
//		authority.setId(Long.valueOf(client.post("/authority", authority)));
//		System.out.println("---------- User Test Case ----------");
//		user = (User) client.get("/user/" + user.getId(), user.getName(), user.getPassword()).toArray()[0];
//		System.out.println("user id:"+user.getId().toString());
//		System.out.println("user name:"+user.getName());
//		System.out.println("user email:"+user.getEmail());
//
//		System.out.println("Modify:"+client.put("/user/" + user.getId(), user));
//		user = (User) client.get("/user/" + user.getId(), user.getName(), "198297").toArray()[0];
//		
//		System.out.println("----------- Authority Test Case ---------");
//		authority = (Authority) client.get("/authority/" + authority.getId(), "admin", "admin").toArray()[0];
//		System.out.println("authority id:"+authority.getId().toString());
//		
//		authority.setUserName("liugy");
//		authority.setAuthority("ROLE_ADMIN");
//		System.out.println("modify:"+client.post("/authority/" + authority.getId(), authority));// UserName is updated (exported = false)
//		authority = (Authority) client.get("/authority/" + authority.getId(), "admin", "admin").toArray()[0];
//		System.out.println(authority.getId().toString());
//		System.out.println(authority.getAuthority());
//		System.out.println(client.post("/authority/" + authority.getId() + "/user", "/user/" + user.getId()));	
//		System.out.println("---------- Device Test Case ----------");
//		device.setId(Long.valueOf(client.post("/device", device)));
//		device = (Device) client.get("/device/" + device.getId(), "admin", "admin").toArray()[0];
//		System.out.println("device id:"+device.getId().toString());
//		System.out.println("device name:"+device.getName());
//		System.out.println("device code:"+device.getCode());
//		device.setName("FM");
//		System.out.println(client.put("/device/" + device.getId(), device));
//		device = (Device) client.get("/device/" + device.getId(), "admin", "admin").toArray()[0];
//		System.out.println(device.getId().toString());
//		System.out.println(device.getName());
//		System.out.println(device.getCode());
//		System.out.println(client.post("/device/" + device.getId() + "/user", "/user/" + user.getId()));	
//		System.out.println("----------- Test Case End ---------");
//		client.get("/user/" + user.getId() + "/devices", user.getName(), "123456");
//		client.get("/user/" + user.getId() + "/authorities", user.getName(), "123456");
//		System.out.println(client.delete("/user/" + user.getId()));	
//		// Authentication request for failed, because user has been deleted
//		client.get("/user", user.getName(), "123456");
//		client.get("/authority", user.getName(), "123456");
//		client.get("/device", user.getName(), "123456");
//		// The correct result
//		client.get("/user", "admin", "admin");
//		client.get("/authority", "admin", "admin");
//		client.get("/device", "admin", "admin");
	}
	
	public void activeDevice(String userid, String deviceid) throws Exception{
		String msg = client.post("/device/"+deviceid+"/user", "/user/"+userid);
		System.out.println(msg);
	}
	
	public void createDevice() throws Exception{
		device = new Device();
		device.setName("Mock");
		device.setCode(UUID.randomUUID().toString());
		
		device.setId(Long.valueOf(client.post("/device", device)));

		Random ran = new Random();
		int count = ran.nextInt(100);
		for(int i=0;i<count;i++){
			Variable var = new Variable();
			var.setName("Readable_var_"+i);
			var.setType(Variable.READ);
			var.setId(Long.valueOf(client.post("/variable", var)));
			System.out.println(client.post("/variable/"+var.getId()+"/device", "/device/"+device.getId()));
		}
		
		count = ran.nextInt(20);
		for(int i=0;i<count;i++){
			Variable var = new Variable();
			var.setName("Writable_var_"+i);
			var.setType(Variable.WRITE);
			var.setId(Long.valueOf(client.post("/variable", var)));
			System.out.println(client.post("/variable/"+var.getId()+"/device", "/device/"+device.getId()));
			int ic = ran.nextInt(10);
			for(int n=0;n<ic;n++){
				Item it = new Item();
				it.setValue("value_"+n);
				it.setId(Long.valueOf(client.post("/item/", it)));
				System.out.println(client.post("/item/"+it.getId()+"/variable", "/variable/"+var.getId()));
			}
		}
	}
	
	public void getAllUser() throws Exception{
		ArrayList<Entity> users = (ArrayList<Entity>) client.get("/user/","admin","admin");
		for(Entity e:users){
			User u = (User) e;
			System.out.println("user id:"+u.getId());
			System.out.println("user name:"+u.getName());
			System.out.println("user mail:"+u.getEmail());
		}
	}
	
	public ArrayList<Device> getAllDevice1(){
		ArrayList<Device> list = new ArrayList<Device>();
		ArrayList<Entity> devs;
		try {
			devs = (ArrayList<Entity>) client.get("/device/","liugy","liugy");
			for(Entity e:devs){
				Device dev = (Device) e;
				list.add(dev);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return list;
	}
	
	public void getAllDevice() throws Exception{
		ArrayList<Entity> devs = (ArrayList<Entity>) client.get("/device/","admin","admin");
		for(Entity e:devs){
			Device dev = (Device) e;
			System.out.println("device name:"+dev.getName());
			System.out.println("device id:"+dev.getId());
			System.out.println("device code:"+dev.getCode());
			
			ArrayList<Entity> vars = (ArrayList<Entity>) client.get("/device/"+dev.getId()+"/variables","admin","admin");
			for(Entity ent:vars){
				Variable var = (Variable)ent;
				System.out.println("variable id:"+var.getId());
				System.out.println("variable name:"+var.getName());
				
				ArrayList<Entity> items = (ArrayList<Entity>) client.get("/variable/"+var.getId()+"/items","admin","admin");
				for(Entity ee:items){
					Item item = (Item)ee;
					System.out.println("item id:"+item.getId());
					System.out.println("item value:"+item.getValue());
				}
				
			}
		}
	}
	
	public void getUserData(String username){
		try {
			System.out.println("########TEST GET DATA########");
			User user = ((User) ((ArrayList<?>) client.get("/user/search/names?name="+username,"admin","admin")).toArray()[0]);
			System.out.println("User name:"+user.getName());
			System.out.println("User id:"+user.getId());
			
			ArrayList<Entity> devs = (ArrayList<Entity>) client.get("/user/"+user.getId()+"/devices","admin","admin");
			for(Entity e:devs){
				Device dev = (Device) e;
				System.out.println("device name:"+dev.getName());
				System.out.println("device id:"+dev.getId());
				
				ArrayList<Entity> vars = (ArrayList<Entity>) client.get("/device/"+dev.getId()+"/variables","admin","admin");
				for(Entity ent:vars){
					Variable var = (Variable)ent;
					System.out.println("variable id:"+var.getId());
					System.out.println("variable name:"+var.getName());
					
					ArrayList<Entity> items = (ArrayList<Entity>) client.get("/variable/"+var.getId()+"/items","admin","admin");
					for(Entity ee:items){
						Item item = (Item)ee;
						System.out.println("item id:"+item.getId());
						System.out.println("item value:"+item.getValue());
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void testAccessChannel(){
		
//		try {
//			System.out.println("########TEST ACCESS CHANNEL########");
//			User user = ((User) ((ArrayList<?>) client.get("/user/search/names?name=bill","admin","admin")).toArray()[0]);
//			
//			ArrayList<Entity> devs = (ArrayList<Entity>) client.get("/user/"+user.getId()+"/devices","admin","admin");
//			for(Entity e:devs){
//				Device dev = (Device) e;
//				ArrayList<Entity> vars = (ArrayList<Entity>) client.get("/device/"+dev.getId()+"/variables","admin","admin");
//				for(Entity ent:vars){
//					final Variable var = (Variable)ent;
//					
//					ChannelClient cclient = new ChannelClient(baseUrl);
//					cclient.setDebug(true);
//					cclient.join();
//					if(var.getType().equals(Variable.READ)){
//						
//						cclient.listen(new ChannelClientListener() {
//	
//							@Override
//							public void onAbort(Event arg0) {
//								System.out.println("Abort:"+arg0.getSubject());
//							}
//	
//							@Override
//							public void onData(Event arg0) {
//								System.out.println("var_id:"+arg0.getField("var_id"));
//								System.out.println("var_value"+arg0.getField("var_value"));
//							}
//	
//							@Override
//							public void onError(String arg0) {
//								System.out.println("Error:"+arg0);
//							}
//	
//							@Override
//							public void onHeartbeat(Event arg0) {
//								System.out.println("Heart Beat:"+arg0.getSubject());
//							}
//							
//						}, Protocol.MODE_STREAM, ""+dev.getId());
//					}else{
//						Map<String, String> map = new LinkedHashMap<String,String>();
//						map.put("var_id", "10");
//						map.put("var_value", "");
//						cclient.publish(""+dev.getId(), map);
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public void testCreateData(){
		
		User user = new User();
		user.setName("liugy");
		user.setEmail("liugy503@gmail.com");
		user.setPassword("198297");
		Authority authority = new Authority();
		authority.setAuthority("ROLE_USER");
		authority.setUserName("liugy");
		Device device = new Device();
		device.setName("ATM");
		device.setCode(UUID.randomUUID().toString());

		try{
//			client.post("/user","admin","admin", user);
			
			for (Entity entity : client.get("/user","admin","admin")) {
				User uu = (User) entity;
				System.out.println("id:"+uu.getId());
				System.out.println("name:"+uu.getName());
				System.out.println("email:"+uu.getEmail());
				System.out.println("password:"+uu.getPassword());
			}
			
//			client.post("/authority", authority);
			
			device.setId(Long.valueOf(client.post("/device", device)));
			ArrayList<Entity> list = client.get("/device/" + device.getId(), user.getName(), user.getPassword());
			device = (Device) list.toArray()[0];
			System.out.println("device id:"+device.getId().toString());
			System.out.println("device name:"+device.getName());
			System.out.println("device code:"+device.getCode());
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void createUser(){
		
		User user = new User();
		user.setName("liugy");
		user.setEmail("liugy503@gmail.com");
		user.setPassword("198297");
		Authority authority = new Authority();
		authority.setAuthority("ROLE_USER");
		authority.setUserName("liugy");
		Device device = new Device();
		device.setName("ATM");
		device.setCode(UUID.randomUUID().toString());

		try{
//			System.out.println(client.post("/user", user));
			
//			ArrayList<Entity> list = client.get("/user?page=2","liugy","198297");
//			for (Entity entity : list) {
//				User uu = (User) entity;
//				System.out.println("id:"+uu.getId());
//				System.out.println("name:"+uu.getName());
//				System.out.println("email:"+uu.getEmail());
//				System.out.println("password:"+uu.getPassword());
//			}
			
			System.out.println(client.post("/authority", authority));
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void testChannel() throws Exception{
		ChannelClient client = new ChannelClient("tcp://m2m.eclipse.org:1883");
		client.listen(new MqttCallback(){
				@Override
				public void connectionLost(Throwable arg0) {
					System.out.println(arg0.getMessage());
					
				}
	
				@Override
				public void deliveryComplete(MqttDeliveryToken arg0) {
					System.out.println("deliveryComplete");
				}
	
				@Override
				public void messageArrived(MqttTopic arg0, MqttMessage message)
						throws Exception {
					System.out.println(new String(message.getPayload()));
				}
				
			}
			, "pinecone@device.1234");
		
		Thread.sleep(10000);
		
		client.disconnect();
	}
	
	public void deleteData(){
		
		try {
			client.delete("/user/" + "15");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
