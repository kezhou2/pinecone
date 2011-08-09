/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.task;

import java.util.List;
import java.util.Timer;

import com.tenline.pinecone.platform.osgi.monitor.factory.PineConeChannleFactory;
import com.tenline.pinecone.platform.osgi.monitor.model.Command;
import com.tenline.pinecone.platform.osgi.monitor.model.Point;
import com.tenline.pinecone.platform.osgi.monitor.xml.DeviceParam;

/**
 * Detect Task
 * 
 */
public class DeviceQueryTimerTask {
	/**
	 * Device Detect Interval
	 */
	public static final int DETECT_INTERVAL = 1000;

	/**
	 * Device Detect Timeout
	 */
	public static final int DETECT_TIMEOUT = 3000;
	/**
	 * DeviceParam
	 */
	private DeviceParam deviceParam;

	/**
	 * 
	 * @param address
	 */
	public DeviceQueryTimerTask(DeviceParam deviceParam) {
		this.deviceParam = deviceParam;
	}

	/**
	 * start query
	 */
	public void queryCommand() {
		updateDeviceOffline();
	}

	/**
	 * Update Device Offline
	 */
	private void updateDeviceOffline() {
		List<Command> commands = this.deviceParam.getCommands();
		for (Command command : commands) {
			if (command.getType().equalsIgnoreCase("in")) {
				// state query thread
				Timer timer = new Timer("Device:"
						+ deviceParam.getMonitorDevice().getDeviceId()
						+ ",command:" + command.getId());
				CommandQueryTimerTask timerTask = new CommandQueryTimerTask(
						deviceParam, command);
				timer.schedule(timerTask, 1000, command.getQueryTime());
			} else {
				// start ctrl subsribe
				for (Point p : command.getControlPointList()) {
					PineConeChannleFactory.getInstance().subscribe(
							p.getVariable().getId());
				}

			}
		}
	}
}
