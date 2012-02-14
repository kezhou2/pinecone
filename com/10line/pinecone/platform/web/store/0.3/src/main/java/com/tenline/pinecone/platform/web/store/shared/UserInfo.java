package com.tenline.pinecone.platform.web.store.shared;

import java.util.Collection;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.model.User;

public class UserInfo extends BaseModelData {

	private static final long serialVersionUID = 5533539012260085377L;

	private User user;
	
	public UserInfo(){
		user = new User();
	}
	
	public UserInfo(User user){
		this.user = user;
		
		this.setName(user.getName());
		this.setEmail(user.getEmail());
		this.setPassword(user.getPassword());
		this.setAvatar(user.getAvatar());
	}

	public User getUser() {
		return user;
	}

	public String getName() {
		return get("name");
	}

	public void setName(String name) {
		set("name",name);
		user.setName(name);
	}

	public String getEmail() {
		return get("email");
	}

	public void setEmail(String email) {
		set("email",email);
		user.setEmail(email);
	}

	public String getPassword() {
		return get("password");
	}

	public void setPassword(String password) {
		set("password",password);
		user.setPassword(password);
	}

	public byte[] getAvatar() {
		return get("avatar");
	}

	public void setAvatar(byte[] avatar) {
		set("avatar",avatar);
		user.setAvatar(avatar);
	}
	
	public String getAvatarUrl(){
		return get("avatarUrl");
	}
	
	public void setAvatarUrl(String avatarUrl){
		set("avatarUrl", avatarUrl);
	}

}
