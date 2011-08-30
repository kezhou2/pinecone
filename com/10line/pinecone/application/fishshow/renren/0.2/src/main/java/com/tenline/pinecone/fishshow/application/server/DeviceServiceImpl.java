package com.tenline.pinecone.fishshow.application.server;

import java.util.Collection;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tenline.pinecone.fishshow.application.client.service.DeviceService;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.ChannelAPI;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.ItemAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.VariableAPI;

public class DeviceServiceImpl extends RemoteServiceServlet implements
		DeviceService {

	private static String host = "pinecone.web.service.10line.cc";
//	private static String host = "192.168.0.100";
	private static String port = "80";
//	private static String port = "8080";
	//video
//	http://192.168.0.106:8080/api/channel/subscribe/333-device

	private User user;
	private Device devices[];
	private Variable vars[];
	private Item its[];
	
	private Device device;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public User getUser(String snsId) {
		user = null;
		UserAPI api = new UserAPI(host, port, new APIListener() {

			
			public void onError(String arg0) {
				System.out.println(arg0);
			}

			
			public void onMessage(Object arg0) {
				if (arg0 instanceof Collection) {
					Collection co = (Collection) arg0;
					for(Object o: co){
						if(o instanceof User){
							user = (User)o;
							break;
						}
					}
				}
			}
		});

		try {
			api.show("snsId=='" + snsId + "'");
		} catch (InterruptedException e) {
			user = null;
			e.printStackTrace();
		} catch (Exception e) {
			user = null;
			e.printStackTrace();
		}

		return user;
	}

	
	public Device[] getDevice(String userId) {
		devices = null;
		DeviceAPI api = new DeviceAPI(host, port, new APIListener() {

			
			public void onError(String arg0) {
				System.out.println(arg0);
			}

			
			public void onMessage(Object arg0) {
				if (arg0 instanceof Collection) {
					Collection co = (Collection) arg0;
					devices = new Device[co.size()];
					int ii = 0;
					for(Object o:co){
						if(o instanceof Device){
							devices[ii] = (Device)o;
							ii++;
						}
					}
				}
			}
		});

		try {
			api.showByUser("id=='"+userId+"'");
		} catch (InterruptedException e) {
			devices = null;
			e.printStackTrace();
		} catch (Exception e) {
			devices = null;
			e.printStackTrace();
		}

		return devices;
	}

	
	public Variable[] getVariable(String deviceId) {
		vars = null;
		VariableAPI api = new VariableAPI(host, port, new APIListener() {

			
			public void onError(String arg0) {
				System.out.println(arg0);
			}

			
			public void onMessage(Object arg0) {
				if (arg0 instanceof Collection) {
					Collection co = (Collection) arg0;
					vars = new Variable[co.size()];
					int ii = 0;
					for(Object o:co){
						if(o instanceof Variable){
							vars[ii] = (Variable)o;
							ii++;
						}
					}
				}
			}
		});

		try {
			api.showByDevice("id=='"+deviceId+"'");
		} catch (InterruptedException e) {
			vars = null;
			e.printStackTrace();
		} catch (Exception e) {
			vars = null;
			e.printStackTrace();
		}

		return vars;
	}

	
	public Item[] getItem(String variableId) {
		its = null;
		ItemAPI api = new ItemAPI(host, port, new APIListener() {

			
			public void onError(String arg0) {
				System.out.println(arg0);
			}

			
			public void onMessage(Object arg0) {
				if (arg0 instanceof Collection) {
					Collection co = (Collection) arg0;
					its = new Item[co.size()];
					int ii = 0;
					for(Object o:co){
						if(o instanceof Item){
							its[ii] = (Item)o;
							ii++;
						}
					}
				}
			}
		});

		try {
			api.showByVariable("id=='"+variableId+"'");
		} catch (InterruptedException e) {
			its = null;
			e.printStackTrace();
		} catch (Exception e) {
			its = null;
			e.printStackTrace();
		}

		return its;
	}
	
	
	public Device getStatus(String type, String deviceId) {
		ChannelAPI api = new ChannelAPI(host, port, new APIListener() {

			
			public void onError(String arg0) {
				System.out.println(arg0);
			}

			
			public void onMessage(Object arg0) {
				if(arg0 instanceof Device){
					device = (Device)arg0;
				}
			}
		});
		
		try {
			System.out.println(deviceId+"-device");
			api.subscribe(deviceId+"-device");
		} catch (InterruptedException e) {
			device = null;
			e.printStackTrace();
		}  catch (Exception e) {
			device = null;
			e.printStackTrace();
		}
		return device;
	}
	
	
	public void setStatus(String deviceId, Device value) {
		ChannelAPI api = new ChannelAPI(host, port, new APIListener() {
//		ChannelAPI api = new ChannelAPI("192.168.0.100", "8080", new APIListener(){
			
			public void onError(String arg0) {
				System.out.println(arg0);
			}

			
			public void onMessage(Object arg0) {
				System.out.println(arg0.toString());
			}
		});
		
		try {
			System.out.println(deviceId+"-application");
//			System.out.println(value.getVariables().size());
			for(Variable v:value.getVariables()){
//				System.out.println(v.getItems().size());
				for(Item it:v.getItems()){
					System.out.println("item id:"+it.getId());
				}
			}
			api.publish(deviceId+"-application", "application/json", value);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
//		DeviceServiceImpl impl = new DeviceServiceImpl();
//		User user = impl.getUser("251760162");
//		System.out.println("Id:"+user.getId());
//		
//		Device[] device = impl.getDevice(user.getId());
//		System.out.println(device.length);
//		
//		System.out.println(impl.getStatus("", device[0].getId()));
//		System.out.println(impl.getStatus("", "2"));
		
//		impl.setStatus("2", "12");
		DeviceServiceImpl impl = new DeviceServiceImpl();
		User user = impl.getUser("251760162");
		Device[] devices = impl.getDevice(user.getId());
		Variable[] vars = impl.getVariable(devices[0].getId());
		
		
	}

	

	

	
}
