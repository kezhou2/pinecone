/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.sdk.development.ChannelAPI;

/**
 * @author Bill
 * 
 */
public class Subscriber {
	
	/**
	 * Subscriber Logger
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * Subscriber Device
	 */
	private Device device;

	/**
	 * Subscriber Channel
	 */
	private ChannelAPI channel;

	/**
	 * Subscriber Timer
	 */
	private Timer timer;

	/**
	 * Subscriber Timer Task
	 */
	private TimerTask task;

	/**
	 * Subscriber Timer Task Interval
	 */
	private static final int INTERVAL = 500;

	/**
	 * Subscriber Timer Task Interval After Task Starting
	 */
	private static final int AFTER_START_INTERVAL = 0;

	/**
	 * Subscriber Scheduler
	 */
	private AbstractScheduler scheduler;

	/**
	 * 
	 */
	public Subscriber() {
		channel = new ChannelAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, IConstants.WEB_SERVICE_CONTEXT);
	}

	/**
	 * Start Subscriber
	 */
	public void start() {
		timer = new Timer();
		task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					APIResponse response = channel.subscribe(device.getId() + "-application");
					if (response.isDone()) {
						scheduler.addToWriteQueue((Device) response.getMessage());
						logger.info("Add to write queue");
					} else {
						logger.error(response.getMessage());
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}

		};
		timer.schedule(task, AFTER_START_INTERVAL, INTERVAL);
		logger.info("Start Subscriber");
	}

	/**
	 * Stop Subscriber
	 */
	public void stop() {
		task.cancel();
		timer.purge();
		logger.info("Stop Subscriber");
	}

	/**
	 * @param scheduler the scheduler to set
	 */
	public void setScheduler(AbstractScheduler scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * @return the scheduler
	 */
	public AbstractScheduler getScheduler() {
		return scheduler;
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
