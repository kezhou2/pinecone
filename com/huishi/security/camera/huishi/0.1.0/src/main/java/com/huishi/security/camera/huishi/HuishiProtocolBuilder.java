/**
 * 
 */
package com.huishi.security.camera.huishi;

import java.util.ArrayList;
import java.util.LinkedList;

import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;

/**
 * @author Bill
 *
 */
public class HuishiProtocolBuilder extends AbstractProtocolBuilder {


	/**
	 * @param bundle
	 */
	public HuishiProtocolBuilder(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
		if (metaData == null) {
			metaData = new Device();
			metaData.setName(bundle.getHeaders().get("Bundle-Name").toString());
			metaData.setSymbolicName(bundle.getSymbolicName());
			metaData.setVersion(bundle.getVersion().toString());
			metaData.setVariables(new ArrayList<Variable>());
			
			Variable variable = new Variable();
			variable.setName(bundle.getHeaders().get("Angle-Control").toString());
			variable.setType("write_discrete");
			variable.setItems(new ArrayList<Item>());
			
			Item item = new Item();
			item.setText(bundle.getHeaders().get("Up").toString());
			item.setValue(String.valueOf(2));
			variable.getItems().add(item);
			
			item = new Item();
			item.setText(bundle.getHeaders().get("Down").toString());
			item.setValue(String.valueOf(0));
			variable.getItems().add(item);

			item = new Item();
			item.setText(bundle.getHeaders().get("Left").toString());
			item.setValue(String.valueOf(6));
			variable.getItems().add(item);

			item = new Item();
			item.setText(bundle.getHeaders().get("Right").toString());
			item.setValue(String.valueOf(4));
			variable.getItems().add(item);
			
			metaData.getVariables().add(variable);
			
			variable = new Variable();
			variable.setName(bundle.getHeaders().get("Video-Stream").toString());
			variable.setType("read_image/png");
			
			metaData.getVariables().add(variable);
		}
	}

	@Override
	public void initializeReadQueue(LinkedList<Device> queue) {
		// TODO Auto-generated method stub
		Device device = new Device();
		device.setVariables(new ArrayList<Variable>());
		Variable variable = new Variable();
		variable.setName(bundle.getHeaders().get("Video-Stream").toString());
		device.getVariables().add(variable);
		queue.addLast(device);
	}

}
