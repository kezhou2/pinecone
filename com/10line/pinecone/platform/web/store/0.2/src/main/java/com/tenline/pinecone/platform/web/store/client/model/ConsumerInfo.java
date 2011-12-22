package com.tenline.pinecone.platform.web.store.client.model;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.Consumer;

public class ConsumerInfo extends BaseModelData{

	private static final long serialVersionUID = 1550801739578952354L;

	private Consumer consumer;
	
	public ConsumerInfo(){
		this.consumer = new Consumer();
	}
	
	public ConsumerInfo(Consumer consumer){
		this.consumer = consumer;
		
		this.setConnectURI(this.consumer.getConnectURI());
		this.setDisplayName(this.consumer.getDisplayName());
		this.setKey(this.consumer.getKey());
		this.setPermissions(this.consumer.getPermissions());
		this.setScopes(this.consumer.getScopes());
		this.setSecret(this.getSecret());
	}
	
	public String getKey() {
		return get("key");
	}

	public void setKey(String key) {
		set("key",key);
	}

	public String getSecret() {
		return get("secret");
	}

	public void setSecret(String secret) {
		set("secret",secret);
	}

	public String getDisplayName() {
		return get("displayName");
	}

	public void setDisplayName(String displayName) {
		set("displayName",displayName);
	}

	public String getConnectURI() {
		return get("connectURI");
	}

	public void setConnectURI(String connectURI) {
		set("connectURI",connectURI);
	}

	public String[] getScopes() {
		return get("scopes");
	}

	public void setScopes(String[] scopes) {
		set("scopes",scopes);
	}

	public String[] getPermissions() {
		return get("permissions");
	}

	public void setPermissions(String[] permissions) {
		set("permissions",permissions);
	}

	public Consumer getConsumer() {
		return consumer;
	}
	
	
}
