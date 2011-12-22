package com.tenline.pinecone.platform.web.store.client.model;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.User;

public class UserInfo extends BaseModelData {

	private static final long serialVersionUID = 5533539012260085377L;

	private User user;
	
	public UserInfo(){
		user = new User();
	}
	
	public UserInfo(User user){
		this.user = user;
		
		set("name",user.getName());
		set("type",user.getType());
		set("email",user.getEmail());
		set("password",user.getPassword());
		set("avatarUrl",user.getAvatarUrl());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return get("name");
	}

	public void setName(String name) {
		set("name",name);
	}

	public String getType() {
		return get("type");
	}

	public void setType(String type) {
		set("type",type);
	}

	public String getEmail() {
		return get("email");
	}

	public void setEmail(String email) {
		set("email",email);
	}

	public String getPassword() {
		return get("password");
	}

	public void setPassword(String password) {
		set("password",password);
	}

	public String getAvatarUrl() {
		return get("avatarUrl");
	}

	public void setAvatarUrl(String avatarUrl) {
		set("avatarUrl",avatarUrl);
	}
	
	
}
