package com.tenline.pinecone.platform.osgi.monitor.model.point;

public class PointComboBoxItem {
	private String	text;
	private String	value;
	
	public PointComboBoxItem() {
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
