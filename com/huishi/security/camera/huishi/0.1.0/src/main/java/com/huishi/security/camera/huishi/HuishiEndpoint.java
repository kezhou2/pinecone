/**
 * 
 */
package com.huishi.security.camera.huishi;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.http.AbstractHttpClientEndpoint;

/**
 * @author Bill
 *
 */
public class HuishiEndpoint extends AbstractHttpClientEndpoint {

	/**
	 * 
	 */
	public HuishiEndpoint() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void dispatch(Device device) {
		// Process Logic
		super.dispatch(device);
	}

}
