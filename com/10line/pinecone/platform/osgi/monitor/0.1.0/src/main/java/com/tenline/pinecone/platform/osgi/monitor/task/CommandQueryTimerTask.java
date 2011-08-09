/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.task;

import java.util.TimerTask;

import com.tenline.pinecone.platform.osgi.monitor.model.Command;
import com.tenline.pinecone.platform.osgi.monitor.xml.DeviceParam;

/**
 * Detect Task
 * 
 * @author xuel
 * 
 */
public class CommandQueryTimerTask extends TimerTask {
	/**
	 * Device Detect Interval
	 */
	public static final int DETECT_INTERVAL = 1000;

	/**
	 * Device Detect Timeout
	 */
	public static final int DETECT_TIMEOUT = 3000;
	/**
	 * device
	 */
	private DeviceParam deviceParam;
	/**
	 * command
	 */
	private Command command;

	/**
	 * 
	 * @param address
	 */
	public CommandQueryTimerTask(DeviceParam deviceParam, Command command) {
		this.deviceParam = deviceParam;
		this.command = command;
	}

	@Override
	public void run() {
		updateDeviceOffline();
	}

	/**
	 * Update Device Offline
	 */
	private void updateDeviceOffline() {
		DeviceCommandSender.sendCommand(deviceParam.getMonitorDevice(), command);
	}
}
