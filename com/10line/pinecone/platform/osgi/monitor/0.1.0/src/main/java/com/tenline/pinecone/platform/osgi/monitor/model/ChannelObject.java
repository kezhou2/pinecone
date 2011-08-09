package com.tenline.pinecone.platform.osgi.monitor.model;

/**
 * @author wong
 * 
 */
public class ChannelObject {

	/**
	 * variable Id
	 */
	private String varId;
	/**
	 * value
	 */
	private Object value;

	public ChannelObject(String varId, Object value) {
		super();
		this.varId = varId;
		this.value = value;
	}

	public String getVarId() {
		return varId;
	}

	public void setVarId(String varId) {
		this.varId = varId;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
