/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.LinkedList;

import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;

/**
 * @author Bill
 *
 */
public abstract class AbstractProtocolBuilder {
	
	/**
	 * Protocol Meta Data
	 */
	protected Device metaData;

	/**
	 * Protocol Bundle
	 */
	protected Bundle bundle;

	/**
	 * 
	 * @param bundle
	 */
	public AbstractProtocolBuilder(Bundle bundle) {
		// TODO Auto-generated constructor stub	
		this.bundle = bundle;
	}
	
	/**
	 * Get Meta Data
	 * @return
	 */
	public Device getMetaData() {
		return metaData;
	}
	
	/**
	 * Initialize Read Queue
	 * @param queue
	 */
	public abstract void initializeReadQueue(LinkedList<Device> queue);

}
