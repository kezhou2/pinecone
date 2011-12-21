package com.tenline.pinecone.platform.web.store.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.tenline.pinecone.platform.web.store.client.model.ConsumerInfo;
import com.tenline.pinecone.platform.web.store.client.model.UserInfo;


public class EnvConfig {
	/**当前登录的用户*/
	private static UserInfo loginUser = null;
	/**用户的homePage*/
	private static UserHomePage homePage = null;
	/**应用商店展示page*/
	private static AllAppPage appPage = null;
	
	private static PineconeServiceAsync pineconeService = null;
	
	private static List<ConsumerInfo> myApps = null;

	
	public static UserHomePage getHomePage(){
		if(homePage == null){
			homePage = new UserHomePage();
		}
//		homePage.reset();
		return homePage;
	}
	
	public static PineconeServiceAsync getPineconeService() {
		if(pineconeService == null){
			pineconeService = (PineconeServiceAsync)GWT.create(PineconeService.class);
		}
		return pineconeService;
	}

	public static AllAppPage getAppPage(){
		if(appPage == null){
			appPage = new AllAppPage();
		}
		appPage.reset(null);
		return appPage;
	}

	public static UserInfo getLoginUser() {
		return loginUser;
	}

	public static void setLoginUser(UserInfo loginUser) {
		EnvConfig.loginUser = loginUser;
	}
	
}
