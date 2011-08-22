/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor;

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
	 * @param device
	 */
	void initialize(Device device);

}
