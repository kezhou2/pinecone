/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Record;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.ChannelAPI;
import com.tenline.pinecone.platform.sdk.RecordAPI;

/**
 * @author Bill
 *
 */
public class Publisher {
	
	/**
	 * Publisher Logger
	 */
	private Logger logger = Logger.getLogger(Publisher.class);

	/**
	 * Publisher Device
	 */
	private Device device;
	
	/**
	 * Publisher Channel
	 */
	private ChannelAPI channel;
	
	/**
	 * Publisher Record API
	 */
	private RecordAPI recordAPI;
	
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
		recordAPI = new RecordAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

			@Override
			public void onError(String arg0) {
				// TODO Auto-generated method stub
				logger.error(arg0);
			}

			@Override
			public void onMessage(Object arg0) {
				// TODO Auto-generated method stub
				logger.info("Record: " + ((Record) arg0).getId());
			}
			
		});
	}
	
	/**
	 * Publish Content
	 * @param content
	 */
	public void publish(Device content) {
		try {
			channel.publish(device.getId() + "-device", "application/json", content);
			addRecord(content);
			logger.info("Publish: " + device.getId() + "-device");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Add Record
	 * @param device
	 * @throws Exception
	 */
	private void addRecord(Device device) throws Exception {
		Variable variable = (Variable) device.getVariables().toArray()[0];
		Item item = (Item) variable.getItems().toArray()[0];
		Record record = new Record();
		record.setValue(item.getValue());
		record.setVariable(variable);
		recordAPI.create(record);
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
