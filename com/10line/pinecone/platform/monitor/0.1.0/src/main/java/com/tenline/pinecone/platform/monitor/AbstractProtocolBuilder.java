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
	 * EFish Protocol Meta Data
	 */
	protected static Device metaData;

	/**
	 * Get Meta Data
	 * @return
	 */
	public static Device getMetaData() {
		return metaData;
	}

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
	 * Initialize Read Queue
	 * @param queue
	 */
	public abstract void initializeReadQueue(LinkedList<Device> queue);

}
