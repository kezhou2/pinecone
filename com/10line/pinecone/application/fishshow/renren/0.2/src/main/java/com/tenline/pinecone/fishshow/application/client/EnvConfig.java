package com.tenline.pinecone.fishshow.application.client;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.fishshow.application.client.resource.IconsConfig;
import com.tenline.pinecone.fishshow.application.client.service.DeviceService;
import com.tenline.pinecone.fishshow.application.client.service.DeviceServiceAsync;
import com.tenline.pinecone.fishshow.application.client.service.RenrenApi;
import com.tenline.pinecone.fishshow.application.client.service.RenrenApiAsync;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;

public class EnvConfig {
	private static String snsId = null;
	private static String sessionKey = null;
	private static Map<String,Device> devMap = new LinkedHashMap<String, Device>();
	private static Map<String, List<Variable>> varMap = new LinkedHashMap<String, List<Variable>>(); 
	private static Map<String, List<Item>> itMap = new LinkedHashMap<String, List<Item>>();
	
	private static DeviceServiceAsync dsa;
	private static RenrenApiAsync rapi;
	
	public static final IconsConfig ICONS = GWT.create(IconsConfig.class);
	
	public static String getSnsId() {
		return snsId;
	}

	public static void setSnsId(String snsId) {
		if(EnvConfig.snsId == null){
			EnvConfig.snsId = snsId;
		}
	}

	public static String getSessionKey() {
		return sessionKey;
	}

	public static void setSessionKey(String sessionKey) {
		if(EnvConfig.sessionKey == null){
			EnvConfig.sessionKey = sessionKey;
		}
	}

	public static void init1(String sessionKey){
		setSessionKey(sessionKey);
		
		rapi = GWT.create(RenrenApi.class);
		rapi.getLoggedInUser(sessionKey, new AsyncCallback<Integer>(){

			public void onFailure(Throwable caught) {
				System.out.println("Failure in getLoggedInUser");
			}

			public void onSuccess(Integer result) {
				init(result.toString());
			}});
	}
	
	public static void init(String snsId){
		EnvConfig.snsId = snsId;
		
		dsa = GWT.create(DeviceService.class);
		dsa.getUser(snsId, new AsyncCallback<User>(){

			public void onFailure(Throwable caught) {
				System.out.println("Failure in getUser");				
			}

			public void onSuccess(User result) {
				if(result != null){
					dsa.getDevice(result.getId(),new AsyncCallback<Device[]>(){

						public void onFailure(Throwable caught) {
							System.out.println("Failure in getDevice");
						}

						public void onSuccess(Device[] result) {
							if(result == null){
								return;
							}
							
							for(final Device pd:result){
								addDevie(pd);
								
								dsa.getVariable(pd.getId(), new AsyncCallback<Variable[]>(){

									public void onFailure(Throwable caught) {
										System.out.println("Failure in getVariable");
									}

									public void onSuccess(
											Variable[] result) {
										if(result == null){
											return;
										}
										
										for(final Variable pv:result){
											addVariable(pd.getId(), pv);
											
											dsa.getItem(pv.getId(), new AsyncCallback<Item[]>(){

												public void onFailure(
														Throwable caught) {
													System.out.println("Failure in getItem");
													
												}

												public void onSuccess(
														Item[] result) {
													if(result == null){
														return;
													}
													for(Item pi:result){
														addItem(pv.getId(), pi);
													}
												}});
										}
									}});
							}
						}});
				}
			}
		});
		
	}
	
	public static Map<String, Device> getDevList() {
		return devMap;
	}

	public static Map<String, List<Variable>> getVarMap() {
		return varMap;
	}

	public static Map<String, List<Item>> getItMap() {
		return itMap;
	}

	public static void addDevie(Device pd){
		for(String id:devMap.keySet()){
			if(id.equals(pd.getId())){
				return;
			}
		}
		
		devMap.put(snsId, pd);
	}
	
	public static void addVariable(String devId, Variable pv){
		if(varMap.get(devId) == null){
			List<Variable> list = new ArrayList<Variable>();
			varMap.put(devId, list);
		}
		
		varMap.get(devId).add(pv);
	}
	
	public static void addItem(String varId, Item pi){
		if(itMap.get(varId) == null){
			List<Item> list = new ArrayList<Item>(); 
			itMap.put(varId, list);
		}
		
		itMap.get(varId).add(pi);
	}
}
