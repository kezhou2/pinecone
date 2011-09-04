/**
 * 
 */
package com.huishi.security.camera.huishi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.asyncweb.common.HttpResponse;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
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
			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ImageIO.write(ImageIO.read(message.getContent().asInputStream()), "jpeg", out);
				content.setVariables(new ArrayList<Variable>());
				Variable variable = new Variable();
				Item item = new Item();
				item.setValue(new String(out.toByteArray()));
				variable.setItems(new ArrayList<Item>());
				variable.getItems().add(item);
				content.getVariables().add(variable);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			message.getContent().array();
		}
		publisher.publish(content);
	}

}
