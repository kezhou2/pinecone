package com.tenline.pinecone.platform.web.store.shared;

import javax.jdo.annotations.Persistent;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Dependency;
import com.tenline.pinecone.platform.model.Driver;

public class DependencyInfo extends BaseModelData {
	
	private Dependency dependency;
	
	public DependencyInfo(){
		dependency = new Dependency();
	}
	
	public DependencyInfo(Dependency dep){
		dependency = dep;
		
		this.setIsOptional(dep.isOptional());
		this.setDriver(dep.getDriver());
		this.setConsumer(dep.getConsumer());
	}

	public Boolean getIsOptional() {
		return get("isOptional");
	}

	public void setIsOptional(Boolean isOptional) {
		set("isOptional",isOptional);
		dependency.setOptional(isOptional);
	}

	public Driver getDriver() {
		return get("driver");
	}

	public void setDriver(Driver driver) {
		set("driver",driver);
		dependency.setDriver(driver);
	}

	public Consumer getConsumer() {
		return get("consumer");
	}

	public void setConsumer(Consumer consumer) {
		set("consumer",consumer);
		dependency.setConsumer(consumer);
	}
	
	
}
