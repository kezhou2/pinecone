package com.tenline.pinecone.platform.web.store.shared;

import java.util.Collection;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Driver;

@SuppressWarnings("serial")
public class CategoryInfo extends BaseModelData {

	private Category category;

	public CategoryInfo(){
		category = new Category();
	}
	
	public CategoryInfo(Category cat){
		category = cat;
		
		this.setType(cat.getType());
		this.setName(cat.getName());
		this.setDomain(cat.getDomain());
		this.setSubdomain(cat.getSubdomain());
		this.setConsumers(cat.getConsumers());
		this.setDrivers(cat.getDrivers());
	}
	
	public Category getCategory() {
		return category;
	}

	public String getType() {
		return get("type");
	}
	public void setType(String type) {
		set("type",type);
		category.setType(type);
		
	}
	public String getName() {
		return get("name");
	}
	public void setName(String name) {
		set("name",name);
		category.setName(name);
	}
	public String getDomain() {
		return get("domain");
	}
	public void setDomain(String domain) {
		set("domain",domain);
		category.setDomain(domain);
	}
	public String getSubdomain() {
		return get("subdomain");
	}
	public void setSubdomain(String subdomain) {
		set("subdomain",subdomain);
		category.setSubdomain(subdomain);
	}
	public Collection<Consumer> getConsumers() {
		return get("consumers");
	}
	public void setConsumers(Collection<Consumer> consumers) {
		set("consumers",consumers);
		category.setConsumers(consumers);
	}
	public Collection<Driver> getDrivers() {
		return get("drivers");
	}
	public void setDrivers(Collection<Driver> drivers) {
		set("drivers",drivers);
		category.setDrivers(drivers);
	}
	
	
}
