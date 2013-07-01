package cc.pinecone.renren.devicecontroller.model;

import com.tenline.pinecone.platform.model.Device;

@SuppressWarnings("serial")
public class ExDeviceInfo extends Device{
	private String macId="";
	private String address="";
	private String description="";
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
