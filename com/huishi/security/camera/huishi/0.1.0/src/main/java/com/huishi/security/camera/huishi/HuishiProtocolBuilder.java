/**
 * 
 */
package com.huishi.security.camera.huishi;

import java.util.LinkedList;

import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;

/**
 * @author Bill
 *
 */
public class HuishiProtocolBuilder extends AbstractProtocolBuilder {

	/**
	 * @param bundle
	 */
	public HuishiProtocolBuilder(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeReadQueue(LinkedList<Device> queue) {
		// TODO Auto-generated method stub

	}

}
