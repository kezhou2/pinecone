package com.tenline.pinecone.fishshow.application.shared.pinecone;

import java.io.Serializable;
import java.util.Map;

public class UserDeviceInfo implements Serializable{

	private static final long serialVersionUID = 1556724879891499147L;
	private String id;
	private String snsId;
	private Map<String,PineconeDevice> devList;
	private Map<String,PineconeVariable> varList;
	private Map<String,PineconeItem> itList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSnsId() {
		return snsId;
	}
	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}
	public Map<String, PineconeDevice> getDevList() {
		return devList;
	}
	public void setDevList(Map<String, PineconeDevice> devList) {
		this.devList = devList;
	}
	public Map<String, PineconeVariable> getVarList() {
		return varList;
	}
	public void setVarList(Map<String, PineconeVariable> varList) {
		this.varList = varList;
	}
	public Map<String, PineconeItem> getItList() {
		return itList;
	}
	public void setItList(Map<String, PineconeItem> itList) {
		this.itList = itList;
	}
	
}
