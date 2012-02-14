package com.tenline.pinecone.platform.web.store.shared;

import javax.jdo.annotations.Persistent;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.User;

public class ApplicationInfo extends BaseModelData{

	private static final long serialVersionUID = 2151555937236603318L;

	private Application application;
	
	public ApplicationInfo(){
		application = new Application();
	}
	
	public ApplicationInfo(Application app){
		application = app;

		this.setConsumer(app.getConsumer());
		this.setIsDefault(app.isDefault());
		this.setStatus(app.getStatus());
		this.setUser(app.getUser());
	}

	public Application getApplication() {
		return application;
	}

	public Boolean getIsDefault() {
		return get("isDefault");
	}

	public void setIsDefault(Boolean isDefault) {
		set("isDefault",isDefault);
		application.setDefault(isDefault);
	}

	public String getStatus() {
		return get("status");
	}

	public void setStatus(String status) {
		set("status",status);
		application.setStatus(status);
	}

	public Consumer getConsumer() {
		return get("consumer");
	}

	public void setConsumer(Consumer consumer) {
		set("consumer",consumer);
		application.setConsumer(consumer);
	}

	public User getUser() {
		return get("user");
	}

	public void setUser(User user) {
		set("user",user);
		application.setUser(user);
	}
	
}
