/**
 * 
 */
package com.huishi.security.camera.huishi;

import org.apache.http.client.HttpClient;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.Publisher;
import com.tenline.pinecone.platform.monitor.http.AbstractHttpClientProtocolExecutor;

/**
 * @author Bill
 *
 */
public class HuishiProtocolExecutor extends AbstractHttpClientProtocolExecutor {

	/**
	 * @param bundle
	 */
	public HuishiProtocolExecutor(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void execute(HttpClient client, Device device, Publisher publisher) {
		// TODO Auto-generated method stub

	}

}
