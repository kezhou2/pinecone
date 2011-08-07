/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.task;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.tenline.pinecone.platform.osgi.monitor.model.Command;
import com.tenline.pinecone.platform.osgi.monitor.model.Device;
import com.tenline.pinecone.platform.osgi.monitor.model.comm.DeviceSessionMap;

/**
 * @author Administrator
 * 
 */
public class DeviceCommandSender {
	/**
	 * @param device
	 * @param command
	 * @return true or false
	 */
	public static boolean sendCommand(Device device, Command command) {
		IoSession ioSession = DeviceSessionMap.getIoSession(device);
		if (ioSession != null) {
			byte[] datas = device.getEncoder().buildPacketData(command);
			IoBuffer buffer = IoBuffer.allocate(datas.length);
			buffer.put(datas);
			buffer.flip();
			ioSession.write(buffer);
			return true;
		}
		return false;
	}
}
