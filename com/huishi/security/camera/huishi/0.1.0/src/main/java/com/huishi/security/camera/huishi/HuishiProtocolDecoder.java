/**
 * 
 */
package com.huishi.security.camera.huishi;

import org.apache.http.HttpResponse;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.monitor.http.AbstractHttpClientProtocolDecoder;

/**
 * @author Bill
 *
 */
public class HuishiProtocolDecoder extends AbstractHttpClientProtocolDecoder {

	/**
	 * @param bundle
	 */
	public HuishiProtocolDecoder(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cancelled() {
		// TODO Auto-generated method stub

	}

	@Override
	public void completed(HttpResponse arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void failed(Exception arg0) {
		// TODO Auto-generated method stub

	}

}
