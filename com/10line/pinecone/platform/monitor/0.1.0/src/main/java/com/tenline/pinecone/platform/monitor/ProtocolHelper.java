/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;

/**
 * @author Bill
 *
 */
public class ProtocolHelper {

	/**
	 * 
	 */
	public ProtocolHelper() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public static Device unmarshel(TreeMap<String, String> map) {
		Device device = new Device();
		device.setVariables(new ArrayList<Variable>());
		for (String name : map.keySet()) {
			Variable variable = new Variable();
			variable.setName(name);
			String value = map.get(name);
			if (value != null) {
				variable.setItems(new ArrayList<Item>());
				Item item = new Item();
				item.setValue(value);
				variable.getItems().add(item);
			}
			device.getVariables().add(variable);
		}
		return device;
	}
	
	/**
	 * 
	 * @param device
	 * @return
	 */
	public static TreeMap<String, String> marshel(Device device) {
		TreeMap<String, String> map = new TreeMap<String, String>();
		Object[] variables = device.getVariables().toArray();
		for (Object object : variables) {
			Variable variable = (Variable) object;
			Collection<Item> items = variable.getItems();
			if (items != null) {
				map.put(variable.getName(), ((Item) items.toArray()[0]).getValue());
			} else {
				map.put(variable.getName(), null);
			}
		}
		return map;
	}

}
