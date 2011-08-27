/**
 * 
 */
package com.tenline.pinecone.platform.monitor.mina;

import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.monitor.IProtocolDecoder;

/**
 * @author Bill
 *
 */
public abstract class AbstractMinaProtocolDecoder extends CumulativeProtocolDecoder implements IProtocolDecoder {
	
	/**
	 * Protocol Bundle
	 */
	protected Bundle bundle;
	
	/**
	 * Protocol Logger
	 */
	protected Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 
	 * @param bundle
	 */
	public AbstractMinaProtocolDecoder(Bundle bundle) {
		// TODO Auto-generated constructor stub
		this.bundle = bundle;
	}
	
}
