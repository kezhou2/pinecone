package com.tenline.pinecone.platform.osgi.monitor;

import java.util.Hashtable;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.serial.SerialConnector;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventHandler;

import com.tenline.pinecone.platform.osgi.monitor.model.comm.DeviceSessionMap;
import com.tenline.pinecone.platform.osgi.monitor.xml.DeviceParam;

public class UsbFishTankBundle extends PineconeSerialEndpoint implements
BundleActivator {
	private SerialConnector serialConnector;
	private IoSession session;
	private DeviceParam deviceParam;

	@Override
	public void start(BundleContext bundlle) throws Exception {
		String devId = "commstar.serial.usbfishtank";
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put("deviceID", devId);
		registration = bundlle.registerService(EventHandler.class.getName(),
				this, table);

		ServiceReference serviceReference = bundlle
				.getServiceReference(IDeviceSerivce.class.getName());
		DeviceService deviceService = (DeviceService) serviceReference;
		load(deviceService, devId);
		
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		close();
	}

	@Override
	protected void close() {
		CloseFuture future = session.close(true);
		future.awaitUninterruptibly(); // wait until the connection is closed
		if (future.isClosed()) {
			registration.unregister();
			serialConnector.dispose();
			executor.shutdown();
			DeviceSessionMap.removeSession(deviceParam.getMonitorDevice());
		}
	}

}
