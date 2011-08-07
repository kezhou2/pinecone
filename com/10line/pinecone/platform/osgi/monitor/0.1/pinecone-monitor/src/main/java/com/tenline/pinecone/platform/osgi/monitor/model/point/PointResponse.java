package com.tenline.pinecone.platform.osgi.monitor.model.point;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
import java.util.TreeMap;

public class PointResponse {
	private byte							rightValue;															// 真正正确的值dongk
	private byte							value;																	// 通信模块置的值
	private TreeMap<Byte, ResponseItem>		responseValue_ResponseItem_Map	= new TreeMap<Byte, ResponseItem>();
	private TreeMap<String, ResponseItem>	responseText_ResponseItem_Map	= new TreeMap<String, ResponseItem>();
	
	public PointResponse() {
	}
	
	public void setRightValue(byte rightValue) {
		this.rightValue = rightValue;
	}
	
	public void setValue(byte value) {
		this.value = value;
	}
	
	public byte getRightValue() {
		return rightValue;
	}
	
	public byte getValue() {
		return value;
	}
	
	public String toString() {
		return getResponseValue_ResponseItem_Map().get(getValue()).getText();
	}
	
	public TreeMap<Byte, ResponseItem> getResponseValue_ResponseItem_Map() {
		return responseValue_ResponseItem_Map;
	}
	
	public TreeMap<String, ResponseItem> getResponseText_ResponseItem_Map() {
		return responseText_ResponseItem_Map;
	}
}
