package com.tenline.pinecone.fishshow.application.shared.pinecone;

import java.io.Serializable;

public class PineconeUser implements Serializable{
	
	private String id;
	private String snsId;
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

}
