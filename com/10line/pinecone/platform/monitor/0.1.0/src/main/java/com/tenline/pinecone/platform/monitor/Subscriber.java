/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

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
public class Subscriber {
	
	/**
	 * Subscriber Logger
	 */
	private Logger logger = Logger.getLogger(Subscriber.class);

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
	private static final int AFTER_START_INTERVAL = 5000;

	/**
	 * Subscriber Scheduler
	 */
	private AbstractScheduler scheduler;

	/**
	 * 
	 */
	public Subscriber() {
		channel = new ChannelAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {

					@Override
					public void onMessage(Object message) {
						scheduler.addToWriteQueue((Device) message);
						logger.info("Add to write queue");
					}

					@Override
					public void onError(String error) {
						logger.error(error);
					}

				});
	}

	/**
	 * Start Subscriber
	 */
	public void start() {
		timer = new Timer();
		task = new TimerTask() {

			@Override
			public void run() {
				try {
					channel.subscribe(device.getId() + "-application");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		};
		timer.schedule(task, AFTER_START_INTERVAL, INTERVAL);
	}

	/**
	 * Stop Subscriber
	 */
	public void stop() {
		task.cancel();
		timer.purge();
	}

	/**
	 * @param scheduler
	 *            the scheduler to set
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
