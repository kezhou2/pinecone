package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.User;

public class Database {
	public static List<Consumer> conList;
	public static List<Application> appList;
	
	static{
		conList = new ArrayList<Consumer>();
		{
			Consumer con = new Consumer();
			con.setConnectURI("http://www.sina.cn");
			con.setName("Sina");
			con.setIcon(null);
			con.setId("1");
			con.setVersion("1.0.0");
			conList.add(con);
		}
		{
			Consumer con = new Consumer();
			con.setConnectURI("http://www.163.com");
			con.setName("163");
			con.setIcon(null);
			con.setId("2");
			con.setVersion("1.0.0");
			conList.add(con);
		}
	}
	
	public static List<Consumer> getConsumers(){
		return conList;
	}
	
	public static List<Application> getApplications(){
		if(Registry.get(User.class.getName()) == null){
			return null;
		}
		
		if(appList == null){
			appList = new ArrayList<Application>();
			{
				Application app = new Application();
				app.setConsumer(conList.get(0));
				app.setDefault(false);
				app.setId("1");
				app.setStatus(Application.CLOSED);
				app.setUser((User)Registry.get(User.class.getName()));
				appList.add(app);
			}
			{
				Application app = new Application();
				app.setConsumer(conList.get(1));
				app.setDefault(false);
				app.setId("2");
				app.setStatus(Application.CLOSED);
				app.setUser((User)Registry.get(User.class.getName()));
				appList.add(app);
			}
		}
		
		return appList;
	}
}
