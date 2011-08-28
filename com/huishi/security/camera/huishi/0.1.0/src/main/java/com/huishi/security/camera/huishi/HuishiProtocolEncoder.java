/**
 * 
 */
package com.huishi.security.camera.huishi;

import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.http.AbstractHttpClientProtocolEncoder;

/**
 * @author Bill
 *
 */
public class HuishiProtocolEncoder extends AbstractHttpClientProtocolEncoder {

	/**
	 * @param bundle
	 */
	public HuishiProtocolEncoder(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void encode(Device device) {
		// TODO Auto-generated method stub

	}

}
