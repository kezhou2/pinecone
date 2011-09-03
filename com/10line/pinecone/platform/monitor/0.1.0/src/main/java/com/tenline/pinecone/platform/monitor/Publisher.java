/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Device;
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
				logger.info(message);
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
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
			channel.publish(device.getId() + "-device", "application/json", content);
		} catch (Exception e) {
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
