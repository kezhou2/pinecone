/**
 * 
 */
package com.huishi.security.camera.huishi;

import java.util.TreeMap;

import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.ProtocolHelper;
import com.tenline.pinecone.platform.monitor.mina.AbstractMinaProtocolRequester;

/**
 * @author Bill
 *
 */
public class HuishiProtocolRequester extends AbstractMinaProtocolRequester {

	/**
	 * @param bundle
	 */
	public HuishiProtocolRequester(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	@Override
	protected void dispatch(Device device) {
		TreeMap<String, String> map = ProtocolHelper.marshel(device);
		for (String key : map.keySet()) {
			if (key.equals(bundle.getHeaders().get("Angle-Control").toString())) {
				request("/decoder_control.cgi?&user=admin&pwd=123456&command=" + map.get(key) + "&onestep=2");
			} else if (key.equals(bundle.getHeaders().get("Video-Stream").toString())) {
				request("/snapshot.cgi?user=admin&pwd=123456");
			}
		}
	}

}
