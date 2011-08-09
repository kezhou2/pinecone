package com.tenline.pinecone.platform.osgi.monitor.model.comm;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.tenline.pinecone.platform.osgi.monitor.model.Device;

/**
 * @author Administrator
 * 
 */
public class DeviceSessionMap {
	private static Map<String, IoSession> deviceSessionMap = new HashMap<String, IoSession>();

	/**
	 * @return add device session
	 * @param device
	 * @param handler
	 */
	public static void addSession(Device device, IoSession handler) {
		if (handler != null) {
			deviceSessionMap.put(device.getDeviceId(), handler);
		}
	}

	/**
	 * @return remove device session
	 * @param device
	 * @param handler
	 */
	public static void removeSession(Device device) {
		if (device != null) {
			deviceSessionMap.remove(device.getDeviceId());
		}
	}

	/**
	 * @param device
	 * @return device session
	 */
	public static IoSession getIoSession(Device device) {
		if (device != null && deviceSessionMap != null) {
			return deviceSessionMap.get(device.getDeviceId());
		}
		return null;
	}
}
