package com.tenline.pinecone.fishshow.application.server;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;


public class DeviceServiceDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		DeviceServiceDemo.testCreateUser();
		DeviceServiceDemo.testIsAppUser();
	}
	
	public static void testCreateUser(){
		DeviceServiceImpl impl = new DeviceServiceImpl();
		User user = impl.getUser("251760162");
		System.out.println("Id:"+user.getId());
		
		Device[] device = impl.getDevice(user.getId());
		System.out.println(device.length);
		
		Variable[] pvs = impl.getVariable(device[0].getId());
		
		System.out.println(impl.getStatus("", pvs[0].getId()));
//		
//		System.out.println(impl.getStatus("", device[0].getId()));
//		System.out.println(impl.getStatus("", "12"));
		
//		impl.setStatus("2", "12");
	}
	
	public static void testIsAppUser(){
		RenrenApiImpl impl = new RenrenApiImpl();
		impl.login("2.4682fab6861e75d1dd5b4c2ba5705ed8.3600.1311951600-251760162");
//		if(!impl.isAppUser("2.28b580048349c3f75c11d78db19d34c5.3600.1311872400-251760162", "704043561")){
			String url = impl.createLink("2.4682fab6861e75d1dd5b4c2ba5705ed8.3600.1311951600-251760162", 0);
			System.out.println(url);
			System.out.println(impl.send("2.4682fab6861e75d1dd5b4c2ba5705ed8.3600.1311951600-251760162", "250483565", "Hello"));
//		}
	}

}
