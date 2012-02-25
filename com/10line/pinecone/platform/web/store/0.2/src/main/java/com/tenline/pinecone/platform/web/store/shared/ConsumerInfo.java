package com.tenline.pinecone.platform.web.store.shared;

import java.util.Collection;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Dependency;

public class ConsumerInfo extends BaseModelData{

	private static final long serialVersionUID = 1550801739578952354L;

	private Consumer consumer;
	
	public ConsumerInfo(){
		this.consumer = new Consumer();
	}
	
	public ConsumerInfo(Consumer con){
		this.consumer = con;
		
		this.setKey(con.getKey());
		this.setSecret(con.getSecret());
		this.setName(con.getName());
		this.setConnectURI(con.getConnectURI());
		this.setIcon(con.getIcon());
		this.setAlias(con.getAlias());
		this.setVersion(con.getVersion());
		this.setScopes(con.getScopes());
		this.setPermissions(con.getPermissions());
		this.setCategory(con.getCategory());
		this.setApplications(con.getApplications());
		this.setDependencies(con.getDependencies());
	}

	public String getKey() {
		return get("key");
	}

	public void setKey(String key) {
		set("key",key);
		consumer.setKey(key);
	}

	public String getSecret() {
		return get("secret");
	}

	public void setSecret(String secret) {
		set("secret",secret);
		consumer.setSecret(secret);
	}

	public String getName() {
		return get("name");
	}

	public void setName(String name) {
		set("name",name);
		consumer.setName(name);
	}

	public String getConnectURI() {
		return get("connectURI");
	}

	public void setConnectURI(String connectURI) {
		set("connectURI",connectURI);
		consumer.setConnectURI(connectURI);
	}

	public byte[] getIcon() {
		return get("icon");
	}

	public void setIcon(byte[] icon) {
		set("icon",icon);
		consumer.setIcon(icon);
	}

	public String getAlias() {
		return get("alias");
	}

	public void setAlias(String alias) {
		set("alias",alias);
		consumer.setAlias(alias);
	}

	public String getVersion() {
		return get("version");
	}

	public void setVersion(String version) {
		set("version",version);
		consumer.setVersion(version);
	}

	public String[] getScopes() {
		return get("scopes");
	}

	public void setScopes(String[] scopes) {
		set("scopes",scopes);
		consumer.setScopes(scopes);
	}

	public String[] getPermissions() {
		return get("permissions");
	}

	public void setPermissions(String[] permissions) {
		set("permissions",permissions);
		consumer.setPermissions(permissions);
	}

	public Category getCategory() {
		return get("category");
	}

	public void setCategory(Category category) {
		set("category",category);
		consumer.setCategory(category);
	}

	public Collection<Application> getApplications() {
		return get("applications");
	}

	public void setApplications(Collection<Application> applications) {
		set("applications",applications);
		consumer.setApplications(applications);
	}

	public Collection<Dependency> getDependencies() {
		return get("dependencies");
	}

	public void setDependencies(Collection<Dependency> dependencies) {
		set("dependencies",dependencies);
		consumer.setDependencies(dependencies);
	}

	public Consumer getConsumer() {
		return consumer;
	}
	
	
	
}
