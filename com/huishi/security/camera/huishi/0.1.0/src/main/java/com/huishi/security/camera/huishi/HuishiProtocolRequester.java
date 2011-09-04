/**
 * 
 */
package com.huishi.security.camera.huishi;

import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
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
	protected void dispatch(Device device) {
		String path = null;
		Variable variable = (Variable) device.getVariables().toArray()[0];
		if (variable.getName().equals(bundle.getHeaders().get("Angle-Control").toString())) {
			Item item = (Item) variable.getItems().toArray()[0];
			path = "/decoder_control.cgi?&user=admin&pwd=123456&command=" + item.getValue() + "&onestep=2";
		} else if (variable.getName().equals(bundle.getHeaders().get("Video-Stream").toString())) {
			path = "/snapshot.cgi?user=admin&pwd=123456";
		}
		request(path);
	}

}
