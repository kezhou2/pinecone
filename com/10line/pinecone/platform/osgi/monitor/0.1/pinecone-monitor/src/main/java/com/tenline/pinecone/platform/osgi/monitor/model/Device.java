package com.tenline.pinecone.platform.osgi.monitor.model;

import java.util.List;

import com.tenline.pinecone.platform.osgi.monitor.mina.protocol.AbstractProtocolDecoder;
import com.tenline.pinecone.platform.osgi.monitor.mina.protocol.AbstractProtocolEncoder;
import com.tenline.pinecone.platform.osgi.monitor.model.comm.CommType;
import com.tenline.pinecone.platform.osgi.monitor.model.device.DeviceStatus;

/**
 * <p>
 * Description：设备类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company:航天恒星科技有限公司软件中心
 * </p>
 * <p>
 * LastEdit:2010.11.17
 * </p>
 * 
 * @author wangyq
 */
public class Device implements Cloneable {
	/**
	 * 设备号
	 */
	private String deviceId;
	/**
	 * 设备名称
	 */
	private String name;
	/**
	 * 设备类型
	 */
	private CommType commType;
	private com.tenline.pinecone.platform.model.Device modelDevice;
	public com.tenline.pinecone.platform.model.Device getModelDevice() {
		return modelDevice;
	}

	public void setModelDevice(
			com.tenline.pinecone.platform.model.Device modelDevice) {
		this.modelDevice = modelDevice;
	}

	/**
	 * 设备别名
	 */
	private String alias;
	/**
	 * 设备分类：一级分类
	 */
	private String system;
	/**
	 * 设备分类：二级分类
	 */
	private String subSystem;
	/**
	 * 设备运行环境
	 */
	private String environmentId;
	/**
	 * 命令列表
	 */
	private List<Command> commandList;
	/**
	 * 设备状态
	 */
	private DeviceStatus deviceStatus;
	/**
	 * 运行Id
	 */
	private String runId;
	/**
	 * 设备协议encoder
	 */
	private AbstractProtocolEncoder encoder;
	/**
	 * 设备协议decoder
	 */
	private AbstractProtocolDecoder decoder;

	public AbstractProtocolEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(AbstractProtocolEncoder encoder) {
		this.encoder = encoder;
	}

	public AbstractProtocolDecoder getDecoder() {
		return decoder;
	}

	public void setDecoder(AbstractProtocolDecoder decoder) {
		this.decoder = decoder;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	public String getEnvironmentId() {
		return environmentId;
	}

	public void setEnvironmentId(String environmentId) {
		this.environmentId = environmentId;
	}

	public List<Command> getCommandList() {
		return commandList;
	}

	public void setCommandList(List<Command> commandList) {
		this.commandList = commandList;
	}

	public DeviceStatus getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(DeviceStatus deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}

	public CommType getCommType() {
		return commType;
	}

	public void setCommType(CommType commType) {
		this.commType = commType;
	}

	public String toString() {
		return this.alias;
	}
}
