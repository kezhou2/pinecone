package com.tenline.pinecone.platform.osgi.monitor.model.point;

public class PointType {
	/**
	 * 自定义类型配置项id
	 */
	private String	id;
	/**
	 * 自定义类型的名称
	 */
	private String	name;
	/**
	 * 自定义类型的别名
	 */
	private String	alias;
	/**
	 * 自定义类型的路径，实例化需要
	 */
	private String	classPath;
	/**
	 * 自定义类型的读写顺序：HiToLo正序读写、LoToHi为反序书写
	 */
	private String	sequence;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getClassPath() {
		return classPath;
	}
	
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	
	public String getSequence() {
		return sequence;
	}
	
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
}
