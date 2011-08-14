package com.tenline.pinecone.fishshow.application.shared.pinecone;

import java.io.Serializable;

public class PineconeItem implements Serializable{
	private String id;
	private String text;
	private String value;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
