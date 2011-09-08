/**
 * 
 */
package com.huishi.security.camera.huishi;

import java.util.TreeMap;

import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.ProtocolHelper;
import com.tenline.pinecone.platform.monitor.httpcomponents.AbstractProtocolRequester;

/**
 * @author Bill
 *
 */
public class HuishiProtocolRequester extends AbstractProtocolRequester {

	/**
	 * @param bundle
	 */
	public HuishiProtocolRequester(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void dispatch(Device device) {
		TreeMap<String, byte[]> map = ProtocolHelper.marshel(device);
		String uri = "http://" + bundle.getHeaders().get("Address").toString() + ":" + 
			bundle.getHeaders().get("Port").toString();
		for (String key : map.keySet()) {
			if (key.equals(bundle.getHeaders().get("Angle-Control").toString())) {
				uri += "/decoder_control.cgi?user=admin&pwd=123456&command=" + new String(map.get(key)) + "&onestep=2";
			} else if (key.equals(bundle.getHeaders().get("Video-Stream").toString())) {
				uri += "/snapshot.cgi?user=admin&pwd=123456";
			}
			sendRequest(uri);
		}
	}

}
