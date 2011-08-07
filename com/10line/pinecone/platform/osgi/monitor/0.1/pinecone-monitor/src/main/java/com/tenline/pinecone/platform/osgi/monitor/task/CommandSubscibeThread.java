/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.task;

import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.osgi.monitor.DeviceService;
import com.tenline.pinecone.platform.osgi.monitor.model.Command;
import com.tenline.pinecone.platform.osgi.monitor.model.Point;
import com.tenline.pinecone.platform.osgi.monitor.tool.CommandReceiveQueue;
import com.tenline.pinecone.platform.osgi.monitor.xml.DeviceParam;

/**
 * @author Administrator
 * 
 */
public class CommandSubscibeThread implements Runnable {
	private static Logger logger = Logger
			.getLogger(CommandSubscibeThread.class);
	private boolean flag = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (flag) {
			try {
				Object message = CommandReceiveQueue.getInstance().poll();
				if (message == null) {
					Thread.sleep(1000);
					continue;
				}
				logger.info("Receive Message:" + message.toString());
				StringTokenizer st = new StringTokenizer(message.toString(),
						"-");
				Command cmd = null;
				DeviceService deviceService = DeviceService.getInstance();
				DeviceParam deviceParam = null;
				while (st.hasMoreElements()) {
					String data = st.nextToken();
					String[] strs = data.split("=");
					if (deviceParam == null) {
						int index = strs[0].lastIndexOf(".");
						String deviceId = strs[0].substring(0, index);
						deviceParam = deviceService.getDevice(deviceId);
					}
					Point p = deviceParam.getControlPoint(strs[0]);
					if (cmd == null) {
						cmd = deviceParam.getCommand(p.getDeviceId(),
								p.getCommandId());
					}
					p.setValue(strs[1]);
					TreeMap<String, Object>  map = new TreeMap<String, Object>();
					map.put(p.getPointId(), p.getValue());
					cmd.setMap(map);
				}
				DeviceCommandSender.sendCommand(deviceParam.getMonitorDevice(), cmd);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
