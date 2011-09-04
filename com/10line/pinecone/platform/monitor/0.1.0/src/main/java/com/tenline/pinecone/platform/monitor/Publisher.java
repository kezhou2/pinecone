/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

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
	private static final int INTERVAL = 1000;

	/**
	 * Publisher Timer Task Interval After Task Starting
	 */
	private static final int AFTER_START_INTERVAL = 0;
	
	/**
	 * Publisher Read Queue
	 */
	private LinkedList<Device> readQueue;
	
	/**
	 * 
	 */
	public Publisher() {
		readQueue = new LinkedList<Device>();
		channel = new ChannelAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

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
						channel.publish(device.getId() + "-device", "application/json", content);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				}
			}
			
		};
		timer.schedule(task, AFTER_START_INTERVAL, INTERVAL);
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
	}
	
	/**
	 * 
	 * @param device
	 */
	public void addToReadQueue(Device device) {
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
