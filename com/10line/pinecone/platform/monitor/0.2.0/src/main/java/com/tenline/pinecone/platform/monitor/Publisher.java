/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Record;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.RecordAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.sdk.development.ChannelAPI;

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
	 * Publisher Timer
	 */
	private Timer timer;

	/**
	 * Publisher Timer Task
	 */
	private TimerTask task;

	/**
	 * Publisher Timer Task Interval
	 */
	private static final int INTERVAL = 500;

	/**
	 * Publisher Timer Task Interval After Task Starting
	 */
	private static final int AFTER_START_INTERVAL = 0;
	
	/**
	 * Publisher Read Queue
	 */
	private LinkedList<Device> readQueue;
	
	/**
	 * Publisher Max Read Queue Size
	 */
	private static final int MAX_QUEUE_SIZE = 3;
	
	/**
	 * Publisher Web Service API
	 */
	private RecordAPI recordAPI;
	
	/**
	 * 
	 */
	public Publisher() {
		readQueue = new LinkedList<Device>();
		channel = new ChannelAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, IConstants.WEB_SERVICE_CONTEXT);
		recordAPI = new RecordAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, IConstants.WEB_SERVICE_CONTEXT);
	}
	
	/**
	 * Start Publisher
	 */
	public void start() {
		timer = new Timer();
		task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Device content = execute();
					if (content != null) {
						publish(content);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				}
			}
			
		};
		timer.schedule(task, AFTER_START_INTERVAL, INTERVAL);
		logger.info("Start Publisher");
	}
	
	/**
	 * Publisher Publish
	 * @param content
	 * @throws Exception 
	 */
	private void publish(Device content) throws Exception {
		Object[] variables = content.getVariables().toArray();
		for (Object object : variables) {
			Variable variable = getVariable(((Variable) object).getName());
			Item item = ((Item)((Variable) object).getItems().toArray()[0]);
			int index = variable.getType().indexOf("image");
			if (index >= 0) {
				String subject = variable.getId() + "-device";
				APIResponse response = channel.publish(subject, variable.getType().substring(index), 
								item.getValue(), IConstants.OAUTH_CONSUMER_KEY, IConstants.OAUTH_CONSUMER_SECRET,
								APIHelper.getToken(), APIHelper.getTokenSecret());
				if (response.isDone()) logger.info(response.getMessage());
				else logger.error(response.getMessage());
				item.setValue(subject.getBytes());
			} else {
				Record record = new Record();
				record.setTimestamp(new Date());
				record.setItem(item);
				APIResponse response = recordAPI.create(record);
				if (response.isDone()) logger.info(response.getMessage());
				else logger.error(response.getMessage());
			}
		}
		APIResponse response = channel.publish(device.getId() + "-device", "application/json", content,
				IConstants.OAUTH_CONSUMER_KEY, IConstants.OAUTH_CONSUMER_SECRET,
				APIHelper.getToken(), APIHelper.getTokenSecret());
		if (response.isDone()) logger.info(response.getMessage());
		else logger.error(response.getMessage());
	}
	
	/**
	 * Get Variable
	 * @param name
	 * @return
	 */
	private Variable getVariable(String name) {
		Object[] variables = device.getVariables().toArray();
		for (Object object : variables) {
			Variable variable = ((Variable) object);
			if (variable.getName().equals(name)) {
				return variable;
			}
		}
		return null;
	}
	
	/**
	 * Execute Publisher
	 * @return
	 */
	private Device execute() {
		if (!readQueue.isEmpty()) {
			return readQueue.removeFirst();
		} else {
			return null;
		}
	}

	/**
	 * Stop Publisher
	 */
	public void stop() {
		task.cancel();
		timer.purge();
		readQueue.clear();
		logger.info("Stop Publisher");
	}
	
	/**
	 * 
	 * @param device
	 */
	public void addToReadQueue(Device device) {
		if (readQueue.size() >= MAX_QUEUE_SIZE) {
			readQueue.removeFirst();
		}
		readQueue.addLast(device);
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
