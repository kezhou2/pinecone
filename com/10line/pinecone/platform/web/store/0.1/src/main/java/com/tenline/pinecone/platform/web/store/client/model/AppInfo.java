package com.tenline.pinecone.platform.web.store.client.model;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.User;

public class AppInfo extends BaseModelData{

	private static final long serialVersionUID = 2151555937236603318L;

	private Application application;
//	private Consumer consumer;

	public AppInfo(){
		application = new Application();
	}
	
	public AppInfo(Application app){
		this.application = app;

		set("id", app.getId());
		set("user", app.getUser());
		set("consumerId",app.getConsumerId());
	}

	public Application getApplication() {
		return application;
	}
	
	public void setId(String id){
		set("id", id);
	}

	public String getId() {
		return get("id");
	}

	public void setUser(User user){
		set("user",user);
	}
	public User getUser() {
		return get("user");
	}
	
	public void setConsumerId(String consumerId){
		set("consumerId",consumerId);
	}

	public String getConsumerId(){
		return get("consumerId");
	}
}
