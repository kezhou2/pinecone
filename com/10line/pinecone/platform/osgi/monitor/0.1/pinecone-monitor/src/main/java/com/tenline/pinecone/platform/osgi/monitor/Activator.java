package com.tenline.pinecone.platform.osgi.monitor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.tenline.pinecone.platform.osgi.monitor.task.CommandSubscibeThread;
import com.tenline.pinecone.platform.osgi.monitor.task.StatePublishThread;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("PineComm start!");
		CommandSubscibeThread subThread = new CommandSubscibeThread();
		Thread thread1 = new Thread(subThread, "CommandSubscibeThread");
		thread1.start();
		StatePublishThread pubThread = new StatePublishThread();
		Thread thread2 = new Thread(pubThread, "StatePublishThread");
		thread2.start();
		context.registerService(IDeviceSerivce.class.getName(),
				DeviceService.getInstance(), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("PineComm stop!");
	}

	public void test() throws Exception {
		// System.out.println("PineComm start!");
		// System.out.print("Initial xml properties : ");
		// boolean isok = XMLLoad.loadConfigration();
		// System.out.print(isok);
		// Point point = DeviceParam.getInstance().getStatePoint(
		// "commstar.serial.usbfishtank.temperatureState");
		// System.out.print(point.getAlias());
	}

}
