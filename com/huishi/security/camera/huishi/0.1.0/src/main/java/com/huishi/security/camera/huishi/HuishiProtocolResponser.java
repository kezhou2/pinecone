/**
 * 
 */
package com.huishi.security.camera.huishi;

import org.apache.asyncweb.common.HttpResponse;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.mina.AbstractMinaProtocolResponser;

/**
 * @author Bill
 *
 */
public class HuishiProtocolResponser extends AbstractMinaProtocolResponser {

	/**
	 * @param bundle
	 */
	public HuishiProtocolResponser(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onResponse(HttpResponse message) {
		// TODO Auto-generated method stub
		Device content = new Device();
		if (message.getContentType().equals("image/jpeg")) {
			message.getContent().asInputStream();
		} else {
			message.getContent().array();
		}
		publisher.publish(content);
	}

}
