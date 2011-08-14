package com.tenline.pinecone.fishshow.application.client;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeDevice;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeItem;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeUser;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeVariable;

public class EnvConfig {
	private static String snsId = null;
	private static String sessionKey = null;
	private static Map<String,PineconeDevice> devList = new LinkedHashMap<String, PineconeDevice>();
	private static Map<String, List<PineconeVariable>> varMap = new LinkedHashMap<String, List<PineconeVariable>>(); 
	private static Map<String, List<PineconeItem>> itMap = new LinkedHashMap<String, List<PineconeItem>>();
	
	private static DeviceServiceAsync dsa;
	private static RenrenApiAsync rapi;
	
	public static String getSnsId() {
		return snsId;
	}

	public static void setSnsId(String snsId) {
		EnvConfig.snsId = snsId;
	}

	public static String getSessionKey() {
		return sessionKey;
	}

	public static void setSessionKey(String sessionKey) {
		EnvConfig.sessionKey = sessionKey;
	}

	public static void init1(String sessionKey){
		setSessionKey(sessionKey);
		
		rapi = GWT.create(RenrenApi.class);
		rapi.getLoggedInUser(sessionKey, new AsyncCallback<Integer>(){

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Failure in getLoggedInUser");
			}

			@Override
			public void onSuccess(Integer result) {
				init(result.toString());
			}});
	}
	
	public static void init(String snsId){
		EnvConfig.snsId = snsId;
		
		dsa = GWT.create(DeviceService.class);
		dsa.getUser(snsId, new AsyncCallback<PineconeUser>(){

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Failure in getUser");				
			}

			@Override
			public void onSuccess(PineconeUser result) {
				if(result != null){
					dsa.getDevice(result.getId(),new AsyncCallback<PineconeDevice[]>(){

						@Override
						public void onFailure(Throwable caught) {
							System.out.println("Failure in getDevice");
						}

						@Override
						public void onSuccess(PineconeDevice[] result) {
							if(result == null){
								return;
							}
							
							for(final PineconeDevice pd:result){
								addDevie(pd);
								
								dsa.getVariable(pd.getId(), new AsyncCallback<PineconeVariable[]>(){

									@Override
									public void onFailure(Throwable caught) {
										System.out.println("Failure in getVariable");
									}

									@Override
									public void onSuccess(
											PineconeVariable[] result) {
										if(result == null){
											return;
										}
										
										for(final PineconeVariable pv:result){
											addVariable(pd.getId(), pv);
											
											dsa.getItem(pv.getId(), new AsyncCallback<PineconeItem[]>(){

												@Override
												public void onFailure(
														Throwable caught) {
													System.out.println("Failure in getItem");
													
												}

												@Override
												public void onSuccess(
														PineconeItem[] result) {
													if(result == null){
														return;
													}
													for(PineconeItem pi:result){
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
	
	public static Map<String, PineconeDevice> getDevList() {
		return devList;
	}

	public static Map<String, List<PineconeVariable>> getVarMap() {
		return varMap;
	}

	public static Map<String, List<PineconeItem>> getItMap() {
		return itMap;
	}

	public static void addDevie(PineconeDevice pd){
		devList.put(snsId, pd);
	}
	
	public static void addVariable(String devId, PineconeVariable pv){
		if(varMap.get(devId) == null){
			List<PineconeVariable> list = new ArrayList<PineconeVariable>();
			varMap.put(devId, list);
		}
		
		varMap.get(devId).add(pv);
	}
	
	public static void addItem(String varId, PineconeItem pi){
		if(itMap.get(varId) == null){
			List<PineconeItem> list = new ArrayList<PineconeItem>(); 
			itMap.put(varId, list);
		}
		
		itMap.get(varId).add(pi);
	}
}
