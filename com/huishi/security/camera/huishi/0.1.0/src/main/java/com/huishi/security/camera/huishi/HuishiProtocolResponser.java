/**
 * 
 */
package com.huishi.security.camera.huishi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import org.apache.asyncweb.common.HttpResponse;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.monitor.ProtocolHelper;
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
		super.onResponse(message);
		if (message.getContentType().equals("image/jpeg")) {
			try {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				ImageIO.write(ImageIO.read(message.getContent().asInputStream()), "jpeg", output);
				TreeMap<String, String> map = new TreeMap<String, String>();
				map.put(bundle.getHeaders().get("Video-Stream").toString(), new String(output.toByteArray()));
				publisher.addToReadQueue(ProtocolHelper.unmarshel(map));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		} 
	}

}
