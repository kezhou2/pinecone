package com.tenline.pinecone.platform.web.store.client.model;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.UserRelation;

public class UserRelationInfo extends BaseModelData {

	private static final long serialVersionUID = -5037680949782130470L;

	private UserRelation userRelation;
	
	public UserRelationInfo(){
		
	}
	
	public UserRelationInfo(UserRelation userRelation){
		this.userRelation = userRelation;
		
		this.set("id", userRelation.getId());
		this.set("owner", userRelation.getOwner());
		this.set("type", userRelation.getType());
		this.set("userId", userRelation.getUserId());
	}

	public UserRelation getUserRelation() {
		return userRelation;
	}

	public void setUserRelation(UserRelation userRelation) {
		this.userRelation = userRelation;
	}

	public String getType() {
		return get("type");
	}

	public void setType(String type) {
		this.userRelation.setType(type);
		set("type",type);
	}

	public String getUserId() {
		return get("userId");
	}

	public void setUserId(String userId) {
		this.userRelation.setUserId(userId);
		set("userId",userId);
	}

	public User getOwner() {
		return get("owner");
	}

	public void setOwner(User owner) {
		this.userRelation.setOwner(owner);
		set("owner",owner);
	}
}
