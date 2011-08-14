package com.tenline.pinecone.fishshow.application.server;

import java.util.Collection;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tenline.pinecone.fishshow.application.client.DeviceService;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeDevice;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeItem;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeUser;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeVariable;
import com.tenline.pinecone.fishshow.application.shared.pinecone.UserDeviceInfo;
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
//	private static String host = "192.168.0.106";
	private static String port = "80";
//	private static String port = "8080";
	//video
//	http://192.168.0.106:8080/api/channel/subscribe/333-device

	private PineconeUser user;
	private PineconeDevice devices[];
	private PineconeVariable vars[];
	private PineconeItem its[];
	
	private String status;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public PineconeUser getUser(String snsId) {
		user = null;
		UserAPI api = new UserAPI(host, port, new APIListener() {

			@Override
			public void onError(String arg0) {
				System.out.println(arg0);
			}

			@Override
			public void onMessage(Object arg0) {
				if (arg0 instanceof Collection) {
					Collection co = (Collection) arg0;
					for(Object o: co){
						if(o instanceof User){
							User u = (User)o;
							user = new PineconeUser();
							user.setId(u.getId());
							user.setSnsId(u.getSnsId());
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

	@Override
	public PineconeDevice[] getDevice(String userId) {
		devices = null;
		DeviceAPI api = new DeviceAPI(host, port, new APIListener() {

			@Override
			public void onError(String arg0) {
				System.out.println(arg0);
			}

			@Override
			public void onMessage(Object arg0) {
				if (arg0 instanceof Collection) {
					Collection co = (Collection) arg0;
					devices = new PineconeDevice[co.size()];
					int ii = 0;
					for(Object o:co){
						if(o instanceof Device){
							Device d = (Device)o;
							PineconeDevice pd = new PineconeDevice();
							pd.setId(d.getId());
							pd.setName(d.getName());
							pd.setSymbolicName(d.getSymbolicName());
							pd.setVersion(d.getVersion());
							devices[ii] = pd;
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

	@Override
	public PineconeVariable[] getVariable(String deviceId) {
		vars = null;
		VariableAPI api = new VariableAPI(host, port, new APIListener() {

			@Override
			public void onError(String arg0) {
				System.out.println(arg0);
			}

			@Override
			public void onMessage(Object arg0) {
				if (arg0 instanceof Collection) {
					Collection co = (Collection) arg0;
					vars = new PineconeVariable[co.size()];
					int ii = 0;
					for(Object o:co){
						if(o instanceof Variable){
							Variable v = (Variable)o;
							PineconeVariable pv = new PineconeVariable();
							pv.setId(v.getId());
							pv.setName(v.getName());
							pv.setType(v.getType());
							vars[ii] = pv;
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

	@Override
	public PineconeItem[] getItem(String variableId) {
		its = null;
		ItemAPI api = new ItemAPI(host, port, new APIListener() {

			@Override
			public void onError(String arg0) {
				System.out.println(arg0);
			}

			@Override
			public void onMessage(Object arg0) {
				if (arg0 instanceof Collection) {
					Collection co = (Collection) arg0;
					its = new PineconeItem[co.size()];
					int ii = 0;
					for(Object o:co){
						if(o instanceof Item){
							Item i = (Item)o;
							PineconeItem pi = new PineconeItem();
							pi.setId(i.getId());
							pi.setText(i.getText());
							pi.setValue(i.getValue());
							its[ii] = pi;
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
	
	@Override
	public String getStatus(String type, String varId) {
		status = "--";
		ChannelAPI api = new ChannelAPI(host, port, new APIListener() {

			@Override
			public void onError(String arg0) {
				System.out.println(arg0);
			}

			@Override
			public void onMessage(Object arg0) {
				if(arg0 instanceof byte[]){
					status = new String((byte[])arg0);
				}
			}
		});
		
		try {
			api.subscribe(varId+"-device");
		} catch (InterruptedException e) {
			status = "--";
			e.printStackTrace();
		}  catch (Exception e) {
			status = "--";
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	public void setStatus(String varId, String value) {
		ChannelAPI api = new ChannelAPI(host, port, new APIListener() {

			@Override
			public void onError(String arg0) {
				System.out.println(arg0);
			}

			@Override
			public void onMessage(Object arg0) {
				System.out.println(arg0.toString());
			}
		});
		
		try {
			api.publish(varId+"-application", "text/plain", value.getBytes());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserDeviceInfo getUserDeviceInfo(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// @Override
	// public PineconeUser getUser(String snsId) {
	// try {
	// URL url = new URL(
	// "http://pinecone.web.service.10line.cc/api/user/show/snsId=='"+snsId+"'");
	// HttpURLConnection connection = (HttpURLConnection) url
	// .openConnection();
	// connection.setRequestProperty("User-Agent",
	// "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	// connection.setDoOutput(true);
	// connection.setRequestMethod("GET");
	// connection.setReadTimeout(30000);
	// connection.setConnectTimeout(30000);
	//
	// if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	//
	// JSONParser parser = new JSONParser();
	// BufferedReader br = new BufferedReader(new InputStreamReader(
	// connection.getInputStream()));
	// JSONArray ja = (JSONArray) parser.parse(br);
	// JSONObject o = (JSONObject) ((JSONObject) ja.get(0))
	// .get("user");
	// PineconeUser user = new PineconeUser();
	// user.setId(o.get("id").toString());
	// user.setSnsId(o.get("snsId").toString());
	//
	// return user;
	// } else {
	// return null;
	// }
	// } catch (MalformedURLException e) {
	// // ...
	// e.printStackTrace();
	// } catch (IOException e) {
	// // ...
	// e.printStackTrace();
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// @Override
	// public PineconeDevice[] getDevice(String userId) {
	// try {
	// URL url = new URL(
	// "http://pinecone.web.service.10line.cc/api/device/show/id=='"+userId+"'/@User");
	// HttpURLConnection connection = (HttpURLConnection) url
	// .openConnection();
	// connection.setRequestProperty("User-Agent",
	// "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	// connection.setDoOutput(true);
	// connection.setRequestMethod("GET");
	// connection.setReadTimeout(30000);
	// connection.setConnectTimeout(30000);
	//
	// if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	//
	// JSONParser parser = new JSONParser();
	// BufferedReader br = new BufferedReader(new InputStreamReader(
	// connection.getInputStream()));
	// JSONArray ja = (JSONArray) parser.parse(br);
	// PineconeDevice[] devices = new PineconeDevice[ja.size()];
	// for(int i=0;i<ja.size();i++){
	// JSONObject o = (JSONObject) ((JSONObject) ja.get(0))
	// .get("device");
	// PineconeDevice device = new PineconeDevice();
	// device.setId(o.get("id").toString());
	// device.setName(o.get("name").toString());
	// device.setSymbolicName(o.get("symbolicName").toString());
	// device.setVersion(o.get("version").toString());
	// devices[i] = device;
	// }
	// return devices;
	// } else {
	// return null;
	// }
	// } catch (MalformedURLException e) {
	// // ...
	// e.printStackTrace();
	// } catch (IOException e) {
	// // ...
	// e.printStackTrace();
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// @Override
	// public PineconeVariable[] getVariable(String deviceId) {
	// try {
	// URL url = new URL(
	// "http://pinecone.web.service.10line.cc/api/variable/show/id=='"+deviceId+"'/@Device");
	// HttpURLConnection connection = (HttpURLConnection) url
	// .openConnection();
	// connection.setRequestProperty("User-Agent",
	// "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	// connection.setDoOutput(true);
	// connection.setRequestMethod("GET");
	// connection.setReadTimeout(30000);
	// connection.setConnectTimeout(30000);
	//
	// if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	//
	// JSONParser parser = new JSONParser();
	// BufferedReader br = new BufferedReader(new InputStreamReader(
	// connection.getInputStream()));
	// JSONArray ja = (JSONArray) parser.parse(br);
	// PineconeVariable[] vars = new PineconeVariable[ja.size()];
	// for(int i=0;i<ja.size();i++){
	// JSONObject o = (JSONObject) ((JSONObject) ja.get(0))
	// .get("variable");
	// PineconeVariable v = new PineconeVariable();
	// v.setId(o.get("id").toString());
	// v.setName(o.get("name").toString());
	// v.setType(o.get("type").toString());
	// vars[i] = v;
	// }
	// return vars;
	// } else {
	// return null;
	// }
	// } catch (MalformedURLException e) {
	// // ...
	// e.printStackTrace();
	// } catch (IOException e) {
	// // ...
	// e.printStackTrace();
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// @Override
	// public PineconeItem[] getItem(String variableId) {
	// try {
	// URL url = new URL(
	// "http://pinecone.web.service.10line.cc/api/item/show/id=='"+variableId+"'/@Variable");
	// HttpURLConnection connection = (HttpURLConnection) url
	// .openConnection();
	// connection.setRequestProperty("User-Agent",
	// "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	// connection.setDoOutput(true);
	// connection.setRequestMethod("GET");
	// connection.setReadTimeout(30000);
	// connection.setConnectTimeout(30000);
	//
	// if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	//
	// JSONParser parser = new JSONParser();
	// BufferedReader br = new BufferedReader(new InputStreamReader(
	// connection.getInputStream()));
	// JSONArray ja = (JSONArray) parser.parse(br);
	// PineconeItem[] items = new PineconeItem[ja.size()];
	// for(int i=0;i<ja.size();i++){
	// JSONObject o = (JSONObject) ((JSONObject) ja.get(0))
	// .get("item");
	// PineconeItem it = new PineconeItem();
	// it.setId(o.get("id").toString());
	// it.setText(o.get("text").toString());
	// it.setValue(o.get("value").toString());
	// items[i] = it;
	// }
	// return items;
	// } else {
	// return null;
	// }
	// } catch (MalformedURLException e) {
	// // ...
	// e.printStackTrace();
	// } catch (IOException e) {
	// // ...
	// e.printStackTrace();
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// public static void main(String[] args) {
	// DeviceServiceImpl impl = new DeviceServiceImpl();
	// PineconeUser user = impl.getUser("251760162");
	// PineconeDevice[] devices = impl.getDevice(user.getId());
	// if(devices == null){
	// return;
	// }
	// for(PineconeDevice d: devices){
	// System.out.println("id="+d.getId());
	// System.out.println("name="+d.getName());
	// System.out.println("symbolicName="+d.getSymbolicName());
	// System.out.println("version="+d.getVersion());
	// PineconeVariable[] vars = impl.getVariable(d.getId());
	// if(vars == null){
	// continue;
	// }
	// for(PineconeVariable v:vars){
	// System.out.println("id="+v.getId());
	// System.out.println("name="+v.getName());
	// System.out.println("type="+v.getType());
	// PineconeItem[] its = impl.getItem(v.getId());
	// if(its==null){
	// continue;
	// }
	// for(PineconeItem it:its){
	// System.out.println("id="+it.getId());
	// System.out.println("text="+it.getText());
	// System.out.println("value="+it.getValue());
	// }
	// }
	//
	// }
	//
	// }

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
		
	}

	

	

	
}
