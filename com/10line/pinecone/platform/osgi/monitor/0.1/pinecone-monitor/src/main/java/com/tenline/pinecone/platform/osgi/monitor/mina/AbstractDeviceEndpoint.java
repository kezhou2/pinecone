/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.mina;

import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;
import org.osgi.framework.ServiceRegistration;

import com.tenline.pinecone.platform.osgi.monitor.xml.DeviceParam;

/**
 * @author wangyq
 * 
 */
public class AbstractDeviceEndpoint {
	protected static Logger logger = Logger
			.getLogger(AbstractDeviceEndpoint.class);
	/**
	 * Concurrent Executor Service
	 */
	protected ExecutorService executor;

	/**
	 * Event Handler Registration
	 */
	protected ServiceRegistration registration;

	/**
	 * 
	 */
	public AbstractDeviceEndpoint() {
	}

	/**
	 * close
	 */
	protected void close() {
	}

	/**
	 * Initialize AbstractMinaEndpoint
	 * 
	 * @param device
	 */
	protected void initialize(DeviceParam deviceParam) {
	}

}
