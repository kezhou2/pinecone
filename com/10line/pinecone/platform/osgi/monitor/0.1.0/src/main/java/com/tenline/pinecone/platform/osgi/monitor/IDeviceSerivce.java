package com.tenline.pinecone.platform.osgi.monitor;

import com.tenline.pinecone.platform.osgi.monitor.xml.DeviceParam;

public interface IDeviceSerivce {

	public abstract boolean loadNewDevice(String cfgPath);

	/**
	 * @param deviceId
	 * @return get device by id
	 */
	public abstract DeviceParam getDevice(String deviceId);

	/**
	 * @return get device nums
	 */
	public abstract int getDeviceNums();

	/**
	 * @param deviceId
	 * @return wheather contains device
	 */
	public abstract boolean containsDevice(String deviceId);

}