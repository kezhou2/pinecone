package com.tenline.pinecone.platform.osgi.monitor.tool;

import java.util.Collection;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;

public class QueryDeviceVariable {
	/**
	 * query device
	 */
	public static Device getDevice(String userId, String devSynName) {
		// device
		User user = UserService.getInstance().getUserBySnsId(userId);
		if (user == null) {
			return null;
		}
		Collection<Device> devices = user.getDevices();
		for (Device dev : devices) {
			if (dev.getSymbolicName().equals(devSynName)) {
				return dev;
			}
		}
		return null;
	}

	/**
	 * query var
	 */
	public static Variable getVariable(Device efish, String varName) {
		if (efish == null) {
			return null;
		}
		Collection<Variable> vars = efish.getVariables();
		for (Variable var : vars) {
			if (var.getName().equals(varName)) {
				return var;
			}
		}
		return null;
	}

	/**
	 * create item and save
	 */
	public static Item getItem(Variable efish, String itemName) {
		if (efish == null) {
			return null;
		}
		Collection<Item> items = efish.getItems();
		for (Item var : items) {
			if (var.getText().equals(itemName)) {
				return var;
			}
		}
		return null;
	}
}
