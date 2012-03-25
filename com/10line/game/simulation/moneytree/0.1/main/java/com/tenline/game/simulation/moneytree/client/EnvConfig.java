package com.tenline.game.simulation.moneytree.client;

import com.google.gwt.core.client.GWT;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.User;

public class EnvConfig {
	
	private static String _sessionKey = null;
	private static String _renrenUserId = null;
	private static User selfUser = null;
	private static Application selfApp = null;
	
	private static DataServiceAsync service = null;
	
	public static User getSelfUser() {
		return selfUser;
	}
	public static void setSelfUser(User selfUser) {
		EnvConfig.selfUser = selfUser;
	}
	public static Application getSelfApp() {
		return selfApp;
	}
	public static void setSelfApp(Application selfApp) {
		EnvConfig.selfApp = selfApp;
	}
	public static String getSessionKey() {
		return _sessionKey;
	}
	public static void setSessionKey(String sessionKey) {
		_sessionKey = sessionKey;
	}
	public static String getRenrenUserId() {
		return _renrenUserId;
	}
	public static void setRenrenUserId(String renrenUserId) {
		_renrenUserId = renrenUserId;
	}
	public static DataServiceAsync getRPCService(){
		if(service == null){
			service = GWT.create(DataService.class);
		}
		
		return service;
	}

}
