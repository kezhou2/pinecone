package com.tenline.pinecone.platform.web.store.client.model;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.Device;

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
		set("device",dev);
		
		this.setName(dev.getName());
		this.setSymbolicName(dev.getSymbolicName());
		this.setVersion(dev.getVersion());
	}

	public String getName() {
		return get("name");
	}

	public void setName(String name) {
		set("name",name);
	}

	public String getSymbolicName() {
		return get("symbolicName");
	}

	public void setSymbolicName(String symbolicName) {
		set("symbolicName",symbolicName);
	}

	public String getVersion() {
		return get("version");
	}

	public void setVersion(String version) {
		set("version",version);
	}

}
