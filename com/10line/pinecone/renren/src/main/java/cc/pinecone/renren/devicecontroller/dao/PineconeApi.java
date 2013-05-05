package cc.pinecone.renren.devicecontroller.dao;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import cc.pinecone.renren.devicecontroller.controller.AppConfig;

import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.param.impl.AccessToken;
import com.renren.api.client.utils.JsonUtils;
import com.tenline.pinecone.platform.model.Authority;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.ChannelClient;
import com.tenline.pinecone.platform.sdk.RESTClient;

public class PineconeApi {
	private RESTClient client = new RESTClient(AppConfig.BASE_URL);
	private final int OVERTIME = 20000;
	private final int BEAT_TIME = 2000;
	private final String ADMIN_NAME = "admin";
	private final String ADMIN_PWD = "admin";

	public User login(String id, String pwd) {
		try {
			ArrayList<Entity> users = (ArrayList<Entity>) client.get(
					"/user/search/names?name=" + id, ADMIN_NAME, ADMIN_PWD);
			for (Entity en : users) {
				if (en instanceof User) {
					User user = (User) en;
					User u = (User) client.get("/user/" + user.getId(), id, pwd).toArray()[0];
					return u;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Device> getUserDevices(User user) {

		ArrayList<Device> list = new ArrayList<Device>();
		try {
			ArrayList<Entity> devs = (ArrayList<Entity>) client.get("/user/"
					+ user.getId() + "/devices", user.getName(), user.getPassword());
			for (Entity e : devs) {
				Device dev = (Device) e;
				list.add(dev);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return list;
	}

	public List<Variable> getDeviceVariables(Device device) {

		System.out.println("getDeviceVariables");
		ArrayList<Variable> list = new ArrayList<Variable>();
		try {
			ArrayList<Entity> vars = (ArrayList<Entity>) client.get("/device/"
					+ device.getId() + "/variables", ADMIN_NAME, ADMIN_PWD);
			for (Entity e : vars) {
				Variable var = (Variable) e;
				list.add(var);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return list;
	}

	public List<Item> getVariableItems(Variable variable) {
		ArrayList<Item> list = new ArrayList<Item>();
		try {
			ArrayList<Entity> items = (ArrayList<Entity>) client.get(
					"/variable/" + variable.getId() + "/items", ADMIN_NAME,
					ADMIN_PWD);
			for (Entity e : items) {
				Item item = (Item) e;
				list.add(item);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return list;
	}

	public boolean publish(String subject, String payload) throws Exception {

		ChannelClient cclient = new ChannelClient(AppConfig.BASE_URL);
		cclient.listen(new MqttCallback() {

			@Override
			public void connectionLost(Throwable arg0) {
				System.out.println("Connection lost.");
			}

			@Override
			public void deliveryComplete(MqttDeliveryToken arg0) {
				System.out.println("deliveryComplete");
			}

			@Override
			public void messageArrived(MqttTopic arg0, MqttMessage arg1)
					throws Exception {
				System.out.println("messageArrived");
			}
		}, subject);
		cclient.publish(payload);
		return true;
	}

	public String subscribe(String subject, String var_id) {

		final String varid = var_id;
		final String sub = subject;
		final boolean isReceived = false;
		final String value = null;
		try {

			ChannelClient cclient = new ChannelClient(AppConfig.BASE_URL);
			cclient.listen(new MqttCallback() {

				@Override
				public void connectionLost(Throwable cause) {
					System.out.println(cause.getMessage());
				}

				@Override
				public void deliveryComplete(MqttDeliveryToken arg0) {
					System.out.println("deliveryComplete");
				}

				@Override
				public void messageArrived(MqttTopic topic, MqttMessage message)
						throws Exception {
					if(topic.getName() != null){
						if(topic.getName().equals(sub)){
							
						}
					}
				}

			}, subject);

			int lapsetime = 0;
			while (!isReceived) {
				try {
					Thread.sleep(BEAT_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				}

				lapsetime = lapsetime + BEAT_TIME;
				if (lapsetime > OVERTIME) {
					break;
				}
			}

			cclient.disconnect();
			cclient = null;
			return value;
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
	}

	public void test(String access_token, String userid, String sessionKey) {
		// System.out.println("Start test!");
		// System.out.println("access_token:"+access_token);
		// System.out.println("userid:"+userid);
		// System.out.println("sessionKey:"+sessionKey);
		// RenrenApiClient api = RenrenApiClient.getInstance();
		//
		// JSONArray ja =
		// JSONParser.parseLenient(api.getUserService().getInfo(userid,
		// "uid,name,headurl,tinyurl,mainurl",new
		// AccessToken(access_token)).toJSONString()).isArray();
		// if (ja != null) {
		// JSONObject o = (JSONObject) ja.get(0);
		// if (o != null) {
		//
		// System.out.println(o.get("uid").isNumber().toString());
		// System.out.println(o.get("name").isString().stringValue());
		// System.out.println(o.get("headurl").isString().stringValue());
		// System.out.println(o.get("tinyurl").isString().stringValue());
		// System.out.println(o.get("mainurl").isString().stringValue());
		//
		// return;
		// }
		// }
	}

	public User isExist(String userid) {
		try {
			ArrayList<Entity> users = (ArrayList<Entity>) client.get(
					"/user/search/names?name=" + userid, ADMIN_NAME, ADMIN_PWD);
			if (users.size() > 0) {
				return (User) users.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public User getUser(String id) {
		try {
			ArrayList<Entity> users = (ArrayList<Entity>) client.get(
					"/user/search/names?name=" + id, ADMIN_NAME, ADMIN_PWD);
			if (users.size() > 0) {
				User user = (User) users.get(0);
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public User register(String id, String pwd, String mail) {
		try {
			ArrayList<Entity> users = (ArrayList<Entity>) client.get(
					"/user/search/names?name=" + id, "admin", "admin");
			if (users.size() == 0) {
				User user = new User();
				user.setName(id);
				user.setPassword(mail);
				user.setEmail(mail);
				user.setId(Long.getLong(client.post("/user", user)));

				Authority authority = new Authority();
				authority.setAuthority("ROLE_USER");
				authority.setUserName(user.getName());
				client.post("/authority", authority);

				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public User getRenrenUser(String id, String access_token) {
		RenrenApiClient api = RenrenApiClient.getInstance();
		String fields = "name,email_hash, sex,star,birthday,tinyurl,headurl,mainurl,hometown_location,hs_history,university_history,work_history,contact_info";
		JSONArray users = api.getUserService().getInfo("251760162", fields,
				new AccessToken(access_token));
		if (users.size() > 0) {
			JSONObject u = JsonUtils.getIndexJSONObject(users, 0);
			String name = JsonUtils.getValue(u, "name", String.class);
			String email_hash = JsonUtils.getValue(u, "email_hash",
					String.class);
			User user = new User();
			user.setName(name);
			user.setEmail(email_hash);
			return user;
		} else {
			return null;
		}
	}

	public boolean activeDevice(String deviceCode, String devicename, User user) {
		try {
			Device dev = (Device) (client.get("/device/search/codes?code="
					+ deviceCode, "admin", "admin")).toArray()[0];

			String msg = client.post("/device/" + dev.getId() + "/user",
					"/user/" + user.getId());
			System.out.println("executed: post:" + msg);

			Device device = new Device();
			device.setName(devicename);
			msg = client.put("/device/" + dev.getId(), device);
			System.out.println("executed: post:" + msg);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
