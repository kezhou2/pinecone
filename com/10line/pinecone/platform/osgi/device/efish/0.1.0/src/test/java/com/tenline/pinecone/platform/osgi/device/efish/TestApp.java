package com.tenline.pinecone.platform.osgi.device.efish;

import java.util.Hashtable;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.serial.SerialAddress;
import org.apache.mina.transport.serial.SerialConnector;

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.osgi.monitor.DeviceService;
import com.tenline.pinecone.platform.osgi.monitor.factory.PineConeProtocolFactory;
import com.tenline.pinecone.platform.osgi.monitor.model.comm.DeviceSessionMap;
import com.tenline.pinecone.platform.osgi.monitor.model.comm.SerialComm;
import com.tenline.pinecone.platform.osgi.monitor.serial.PineconeSerialHandler;
import com.tenline.pinecone.platform.osgi.monitor.service.UserService;
import com.tenline.pinecone.platform.osgi.monitor.task.CommandSubscibeThread;
import com.tenline.pinecone.platform.osgi.monitor.task.DeviceQueryTimerTask;
import com.tenline.pinecone.platform.osgi.monitor.task.StatePublishThread;
import com.tenline.pinecone.platform.osgi.monitor.xml.DeviceParam;

public class TestApp {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put("deviceID", "com.10line.pinecone.platform.osgi.monitor.pinecone-device-usbfishtank ");
		DeviceService deviceService = new DeviceService();
		boolean loadOk = deviceService
				.loadNewDevice("./src/main/resources/META-INF/");
		System.out.println(loadOk);
		DeviceParam deviceParam = DeviceService.getInstance().getDevice(
				"com.10line.pinecone.platform.osgi.monitor.pinecone-device-usbfishtank");
		initialize(deviceParam);

		try {
//			 com.tenline.pinecone.platform.osgi.monitor.service.DeviceService.getInstance().saveDevice(monitorDev2ModelVar);
			 @SuppressWarnings("unused")
			User user = UserService.getInstance().getUserBySnsId("251760162");
//			 ArrayList<Device> devs = com.tenline.pinecone.platform.osgi.monitor.service.DeviceService
//			 .getInstance().getDeviceByUser(user);
//			 System.out.println(devs.get(0).getSymbolicName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// //
		CommandSubscibeThread subThread = new CommandSubscibeThread();
		Thread thread1 = new Thread(subThread, "CommandSubscibeThread");
		thread1.start();
		StatePublishThread pubThread = new StatePublishThread();
		Thread thread2 = new Thread(pubThread, "StatePublishThread");
		thread2.start();
		// //
		// Thread.sleep(30000);
//		 String data = "commstar.serial.usbfishtank.temperatureCtrl=40";
//		 CommandReceiveQueue.getInstance().add(data);
//		 Thread.sleep(3000);
//		 data = "commstar.serial.usbfishtank.bubbleCtrl=281";
//		 CommandReceiveQueue.getInstance().add(data);

	}

	private static void initialize(DeviceParam deviceParam) {
		try {
			if (deviceParam == null) {
				return;
			}
			SerialComm commType = (SerialComm) deviceParam.getMonitorDevice()
					.getCommType();
			SerialAddress serialAddress = new SerialAddress(commType.getName(),
					commType.getBaudRate(), commType.getDataBits(),
					commType.getStopBits(), commType.getParity(),
					commType.getFlowControl());
			SerialConnector serialConnector = new SerialConnector();
			serialConnector.setHandler(new PineconeSerialHandler(deviceParam
					.getMonitorDevice()));
			serialConnector.getFilterChain().addLast(
					"codec",
					new ProtocolCodecFilter(new PineConeProtocolFactory(
							deviceParam.getMonitorDevice())));
			ConnectFuture connect = serialConnector.connect(serialAddress);
			IoSession session = connect.getSession();
			DeviceSessionMap
					.addSession(deviceParam.getMonitorDevice(), session);
			DeviceQueryTimerTask task = new DeviceQueryTimerTask(deviceParam);
			task.queryCommand();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
