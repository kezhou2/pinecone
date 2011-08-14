/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.mina;

import java.util.Hashtable;
import java.util.concurrent.ExecutorService;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventConstants;

import com.tenline.pinecone.platform.osgi.monitor.IEndpoint;

/**
 * @author Bill
 *
 */
public abstract class AbstractMinaEndpoint implements IEndpoint {

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
	public AbstractMinaEndpoint() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get Service Properties
	 * @return
	 */
	protected Hashtable<String, String> getProperties(String id) {
		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put(EventConstants.EVENT_TOPIC, "endpoint/" + id);
		return properties;
	}

}
