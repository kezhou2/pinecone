/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor;

import java.util.Hashtable;

import org.apache.mina.core.session.IoSession;

import com.tenline.pinecone.platform.model.Device;

/**
 * @author Bill
 *
 */
public interface IEndpoint {

	/**
	 * Close Endpoint
	 */
	void close();
	
	/**
	 * Initialize Endpoint
	 * @param params
	 * @param mapping
	 */
	void initialize(Hashtable<String, String> params, Hashtable<Device, IoSession> mapping);

}
