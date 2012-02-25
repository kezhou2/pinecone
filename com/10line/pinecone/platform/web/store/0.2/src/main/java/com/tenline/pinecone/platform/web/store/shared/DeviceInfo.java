package com.tenline.pinecone.platform.web.store.shared;

import java.util.Collection;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;

public class DeviceInfo extends BaseModelData{
	
	private static final long serialVersionUID = -198328489620871574L;
	private Device device;
	
	public DeviceInfo(){
		device = new Device();
	}
	
	public Device getDevice() {
		return device;
	}

	public DeviceInfo(Device dev){
		device = dev;
		this.setIsDefault(dev.isDefault());
		this.setStatus(dev.getStatus());
		this.setUser(dev.getUser());
		this.setDriver(dev.getDriver());
		this.setVariables(dev.getVariables());
	}

	public Boolean getIsDefault() {
		return get("isDefault");
	}

	public void setIsDefault(Boolean isDefault) {
		set("isDefault",isDefault);
		device.setDefault(isDefault);
	}

	public String getStatus() {
		return get("status");
	}

	public void setStatus(String status) {
		set("status",status);
		device.setStatus(status);
	}

	public User getUser() {
		return get("user");
	}

	public void setUser(User user) {
		set("user",user);
		device.setUser(user);
	}

	public Driver getDriver() {
		return get("driver");
	}

	public void setDriver(Driver driver) {
		set("driver",driver);
		device.setDriver(driver);
	}

	public Collection<Variable> getVariables() {
		return get("variables");
	}

	public void setVariables(Collection<Variable> variables) {
		set("variables",variables);
		device.setVariables(variables);
	}

	

}
