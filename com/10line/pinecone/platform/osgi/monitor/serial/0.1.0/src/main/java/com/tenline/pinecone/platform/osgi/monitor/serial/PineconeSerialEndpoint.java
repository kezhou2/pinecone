package com.tenline.pinecone.platform.osgi.monitor.serial;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.serial.SerialAddress;
import org.apache.mina.transport.serial.SerialConnector;

import com.tenline.pinecone.platform.osgi.monitor.DeviceService;
import com.tenline.pinecone.platform.osgi.monitor.factory.PineConeProtocolFactory;
import com.tenline.pinecone.platform.osgi.monitor.mina.AbstractDeviceEndpoint;
import com.tenline.pinecone.platform.osgi.monitor.model.comm.DeviceSessionMap;
import com.tenline.pinecone.platform.osgi.monitor.model.comm.SerialComm;
import com.tenline.pinecone.platform.osgi.monitor.task.DeviceQueryTimerTask;
import com.tenline.pinecone.platform.osgi.monitor.xml.DeviceParam;

public class PineconeSerialEndpoint extends AbstractDeviceEndpoint {

	protected void load(DeviceService deviceService,String devId) {
		boolean loadOk = deviceService.loadNewDevice( "./src/main/resources/META-INF/");
		System.out.println("load "+devId+" "+loadOk);
		DeviceParam deviceParam = DeviceService.getInstance().getDevice(devId);
		initialize(deviceParam);
	}

	@Override
	protected void close() {
		// TODO Auto-generated method stub
		super.close();
	}

	@Override
	protected void initialize(DeviceParam deviceParam) {
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
			DeviceSessionMap.addSession(deviceParam.getMonitorDevice(), session);
			DeviceQueryTimerTask task = new DeviceQueryTimerTask(deviceParam);
			task.queryCommand();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
