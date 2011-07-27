/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.ItemAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.VariableAPI;

/**
 * @author Bill
 *
 */
public class FishShowApplicationIntegrationTest {

	private static User user;
	
	private static Device device;
	
	private static Variable variable;
	
	private static Item item;
	
	private static UserAPI userAPI;
	
	private static DeviceAPI deviceAPI;
	
	private static VariableAPI variableAPI;
	
	private static ItemAPI itemAPI;
	
	private static String host = "localhost";
	private static String port = "8080";
	
	@Test
	public void test() throws Exception {
		// TODO Auto-generated constructor stub
		userAPI = new UserAPI(host, port, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				user = (User) message;
				assertEquals("251760162", user.getSnsId());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		user = new User();
		user.setSnsId("251760162");
		userAPI.create(user);
		deviceAPI = new DeviceAPI(host, port, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				device = (Device) message;
				assertEquals("e-fish智能鱼缸", device.getName());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		device = new Device();
		device.setName("e-fish智能鱼缸");
		device.setSymbolicName("com.tenline.pinecone.platform.osgi.device.efish");
		device.setVersion("0.1.0");
		device.setUser(user);
		deviceAPI.create(device);
		variableAPI = new VariableAPI(host, port, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				variable = (Variable) message;
				assertEquals("鱼缸水温", variable.getName());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		variable = new Variable();
		variable.setName("鱼缸水温");
		variable.setType("read_write_continuous");
		variable.setDevice(device);
		variableAPI.create(variable);
		itemAPI = new ItemAPI(host, port, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				item = (Item) message;
				assertEquals("12°C", item.getText());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		item = new Item();
		item.setText("12°C");
		item.setValue("12");
		item.setVariable(variable);
		itemAPI.create(item);
		itemAPI = new ItemAPI(host, port, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				item = (Item) message;
				assertEquals("30°C", item.getText());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
		item = new Item();
		item.setText("30°C");
		item.setValue("30");
		item.setVariable(variable);
		itemAPI.create(item);
	}

}
