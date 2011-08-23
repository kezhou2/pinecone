/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.ArrayList;

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
	 * Publisher Device
	 */
	private Device device;
	
	/**
	 * Publisher Channel
	 */
	private ChannelAPI channel;
	
	/**
	 * 
	 */
	public Publisher() {
		// TODO Auto-generated constructor stub
		channel = new ChannelAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				System.out.println(message);
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
			}
			
		});
	}
	
	/**
	 * Publish Content
	 * @param content
	 */
	public void publish(Device content) {
		try {
			ArrayList<Variable> vars = (ArrayList<Variable>)content.getVariables();
			ArrayList<Item> items = (ArrayList<Item>)vars.get(0).getItems();
			String value = items.get(0).getValue();
			System.out.println("publish: "+device.getId() + "-device"+",item value: "+value);
			channel.publish(device.getId() + "-device", "application/json", content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param device the device to set
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
