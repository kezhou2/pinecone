package com.tenline.pinecone.platform.osgi.monitor.model.device;


public enum DeviceStatus {
	/**
	 * 设备未初始化
	 */
	DEVICE_NOT_INIT("DEVICE_NOT_INIT"),
	/**
	 * 设备在线
	 */
	DEVICE_ONLINE("DEVICE_ONLINE"),
	/**
	 * 设备接收数据中
	 */
	DEVICE_RECEIVING("DEVICE_RECEIVING"),
	/**
	 * 设备发送数据中
	 */
	DEVICE_SENTING("DEVICE_SENTING"),
	/**
	 * 设备设备端口已经打开，但端口没建立连接
	 */
	DEVICE_OPENED_BUT_NOT_CONNECTED("DEVICE_OPENED_BUT_NOT_CONNECTED"),
	/**
	 * 设备不在线
	 */
	DEVICE_OFFLINE("DEVICE_OFFLINE"),
	/**
	 * 设备本控
	 */
	DEVICE_LOCAL("DEVICE_LOCAL"),
	/**
	 * 设备参数超限
	 */
	DEVICE_WARN("DEVICE_WARN"),
	/**
	 * 设备出现问题，如端口未打开
	 */
	DEVICE_ERROR("DEVICE_ERROR");
	// /**
	// * 设备空闲，在一定是时间内未收到查训令
	// */
	// DEVICE_IDLE("DEVICE_IDLE",new Color(112,48,160));
	private final String value;

	private DeviceStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
