package com.tenline.pinecone.platform.osgi.monitor.model.point;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PointComboBoxGroup {
	private byte										begin;																				// 褰撹PointComBox鍙崰涓�釜瀛楄妭鐨勬煇鍑犱綅鏃讹紝begin琛ㄧず瀹冨湪涓�釜瀛楄妭涓殑绗嚑浣嶅紑濮嬨�濡傛灉璇ointComBox鐢�涓猙yte琛ㄧず锛屽垯begin=-1.
	private byte										end;																				// 褰撹PointComBox鍙崰涓�釜瀛楄妭鐨勬煇鍑犱綅鏃讹紝end琛ㄧず瀹冨湪涓�釜瀛楄妭涓殑绗嚑浣嶇粨鏉熴�濡傛灉璇ointComBox鐢�涓猙yte琛ㄧず锛屽垯end=-1.
	private String										value;
	private LinkedHashMap<String, PointComboBoxItem>	comboBoxText_ComboBoxItem_Map	= new LinkedHashMap<String, PointComboBoxItem>();
	private LinkedHashMap<String, PointComboBoxItem>	comboBoxValue_ComboBoxItem_Map	= new LinkedHashMap<String, PointComboBoxItem>();
	
	public PointComboBoxGroup() {
	}
	
	public byte getBegin() {
		return begin;
	}
	
	public void setBegin(byte begin) {
		this.begin = begin;
	}
	
	public byte getEnd() {
		return end;
	}
	
	public void setEnd(byte end) {
		this.end = end;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public HashMap<String, PointComboBoxItem> getComboBoxText_ComboBoxItem_Map() {
		return comboBoxText_ComboBoxItem_Map;
	}
	
	public HashMap<String, PointComboBoxItem> getComboBoxValue_ComboBoxItem_Map() {
		return comboBoxValue_ComboBoxItem_Map;
	}
}
