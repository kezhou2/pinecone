/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.ChannelAPI;

/**
 * @author Bill
 * 
 */
public class Publisher {

	/**
	 * Publisher Logger
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * Publisher Device
	 */
	private Device device;

	/**
	 * channel  
	 */
	private ChannelAPI channel;
	/**
	 * 
	 */
	public Publisher() {
		channel = new ChannelAPI(
				IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {

					@Override
					public void onMessage(Object message) {
						logger.info(message);
					}

					@Override
					public void onError(String error) {
						logger.error(error);
					}

				});
	}

	/**
	 * Publish Content
	 * 
	 * @param content
	 */
	public void publish(Device content) {
		try {
			ArrayList<Variable> variables = (ArrayList<Variable>) content
					.getVariables();
			for (Variable var : variables) {
				
				if (var.getType()!=null && var.getType().startsWith("read_image")) {
					ArrayList<Item> items = (ArrayList<Item>) var.getItems();
					String id = matchVarId(var.getName()) ;
					String[] split = var.getType().split("_");
					System.out.println("start publish var: "+var.getName()+","+var.getType()+","+split[0]+","+split[1]+", Variable: "+id + "-device");
					channel.publish(id+ "-device",
							split[1], items.get(0)
									.getValue().getBytes());
				} else {
					System.out.println("start publish device: "+var.getName()+","+var.getType() +","+device.getId() + "-device");
					channel.publish(device.getId() + "-device",
							"application/json", content);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * find variable id by variable name in device to publish image
	 * 
	 * @param varName
	 * @return
	 */
	private String matchVarId(String varName) {
		String varId = null;
		ArrayList<Variable> variables = (ArrayList<Variable>) device
				.getVariables();
		for (Variable var : variables) {
			if (var.getName().equals(varName)) {
				return var.getId();
			}
		}
		return varId;
	}

	/**
	 * @param device
	 *            the device to set
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

	/**
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}

}
