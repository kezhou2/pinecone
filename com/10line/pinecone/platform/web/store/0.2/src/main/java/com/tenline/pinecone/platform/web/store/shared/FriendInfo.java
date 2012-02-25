package com.tenline.pinecone.platform.web.store.shared;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.User;

public class FriendInfo extends BaseModelData {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7912348077208983470L;
	private Friend friend;
	
	public FriendInfo(){
		this.friend = new Friend();
	}
	
	public FriendInfo(Friend friend){
		this.friend = friend;
		
		this.setIsDecided(friend.isDecided());
		this.setReceiver(friend.getReceiver());
		this.setSender(friend.getSender());
		this.setType(friend.getType());
		
	}

	public Boolean getIsDecided() {
		return get("isDecided");
	}

	public void setIsDecided(Boolean isDecided) {
		set("isDecided",isDecided);
		friend.setDecided(isDecided);
	}

	public String getType() {
		return get("type");
	}

	public void setType(String type) {
		set("type",type);
		friend.setType(type);
	}

	public User getSender() {
		return get("sender");
	}

	public void setSender(User sender) {
		set("sender",sender);
		friend.setSender(sender);
	}

	public User getReceiver() {
		return get("receiver");
	}

	public void setReceiver(User receiver) {
		set("receiver",receiver);
		friend.setReceiver(receiver);
	}

	public Friend getFriend() {
		return friend;
	}
	
	
}
