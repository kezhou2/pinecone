/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.mina;

import java.util.Hashtable;
import java.util.concurrent.Executors;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.serial.SerialAddress;
import org.apache.mina.transport.serial.SerialConnector;
import org.apache.mina.transport.serial.SerialAddress.DataBits;
import org.apache.mina.transport.serial.SerialAddress.FlowControl;
import org.apache.mina.transport.serial.SerialAddress.Parity;
import org.apache.mina.transport.serial.SerialAddress.StopBits;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventHandler;

/**
 * @author Bill
 *
 */
public class MinaSerialEndpoint extends AbstractMinaEndpoint {

	/**
	 * MINA IoConnector
	 */
	private IoConnector connector;
	
	/**
	 * MINA Session
	 */
	private IoSession session;
	
	/**
	 * MINA Serial Handler
	 */
	private MinaSerialHandler handler;
	
	/**
	 * 
	 */
	public MinaSerialEndpoint() {
		// TODO Auto-generated constructor stub
		connector = new SerialConnector();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		CloseFuture future = session.close(true);
		future.awaitUninterruptibly(); // wait until the connection is closed
		if(future.isClosed()) {
			registration.unregister();
			connector.dispose();
			executor.shutdown();
		}
	}

	@Override
	public void initialize(BundleContext bundleContext, Hashtable<String, String> params) {
		// TODO Auto-generated method stub
		executor = Executors.newCachedThreadPool();
		connector.getFilterChain().addLast("executor", new ExecutorFilter(executor));
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MinaProtocolCodecFactory
				(bundleContext, params.get("packageName"))));
		handler = new MinaSerialHandler(bundleContext, params.get("id"));
		registration = bundleContext.registerService(EventHandler.class.getName(), handler, getProperties(params.get("id")));
		connector.setHandler(handler);
		ConnectFuture future = connector.connect(new SerialAddress(params.get("port"), 
				Integer.valueOf(params.get("baudRate")), 
				getDataBits(Integer.valueOf(params.get("dataBits"))), 
				getStopBits(Integer.valueOf(params.get("stopBits"))), 
				getParity(params.get("parity")), 
				getFlowControl(params.get("flowControl"))));
		future.awaitUninterruptibly(); // wait until the connection is finished
		if(future.isConnected()) session = future.getSession();
	}

	/**
	 * Get Data Bits
	 * @param dataBits
	 * @return
	 */
	private DataBits getDataBits(int dataBits) {
		switch(dataBits){
			case 5:
				return DataBits.DATABITS_5;
			case 6:
				return DataBits.DATABITS_6;
			case 7:
				return DataBits.DATABITS_7;
			case 8:
				return DataBits.DATABITS_8;
			default:
				return null;
		}
	}
	
	/**
	 * Get Stop Bits
	 * @param stopBits
	 * @return
	 */
	private StopBits getStopBits(int stopBits) {
		switch(stopBits) {
			case 1:
				return StopBits.BITS_1;
			case 2:
				return StopBits.BITS_2;
			default:
				return StopBits.BITS_1_5;
		}
	}
	
	/**
	 * Get Parity
	 * @param parity
	 * @return
	 */
	private Parity getParity(String parity) {
		if (parity.toLowerCase().equals("odd")) {
			return Parity.ODD;
		} else if (parity.toLowerCase().equals("even")) {
			return Parity.EVEN;
		} else if(parity.toLowerCase().equals("mark")) {
			return Parity.MARK;
		} else if(parity.toLowerCase().equals("space")) {
			return Parity.SPACE;
		} else {
			return Parity.NONE;
		}
	}
	
	/**
	 * Get Flow Control
	 * @param flowControl
	 * @return
	 */
	private FlowControl getFlowControl(String flowControl) {
		if (flowControl.toLowerCase().equals("rtscts_in")) {
			return FlowControl.RTSCTS_IN;
		} else if (flowControl.toLowerCase().equals("rtscts_out")) {
			return FlowControl.RTSCTS_OUT;
		} else if (flowControl.toLowerCase().equals("xonxoff_in")) {
			return FlowControl.XONXOFF_IN;
		} else if (flowControl.toLowerCase().equals("xonxoff_out")) {
			return FlowControl.XONXOFF_OUT;
		} else {
			return FlowControl.NONE;
		}
	}

}
