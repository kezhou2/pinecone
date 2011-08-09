package com.tenline.pinecone.platform.osgi.monitor.model.point;

public class ResponseItem {
	private byte	value;
	private String	text;
	
	public ResponseItem() {
	}
	
	public byte getValue() {
		return value;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setValue(byte value) {
		this.value = value;
	}
	
	public String getText() {
		return text;
	}
}
