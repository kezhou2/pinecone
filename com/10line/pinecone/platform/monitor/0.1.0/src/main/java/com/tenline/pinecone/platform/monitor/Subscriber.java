/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.Timer;
import java.util.TimerTask;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.ChannelAPI;

/**
 * @author Bill
 *
 */
public class Subscriber {
	
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
	private static final int INTERVAL = 100;
	
	/**
	 * Subscriber Timer Task Interval After Task Starting
	 */
	private static final int AFTER_START_INTERVAL = 5000;
	
	/**
	 * Subscriber Scheduler
	 */
	private Scheduler scheduler;
	
	/**
	 * 
	 */
	public Subscriber() {
		channel = new ChannelAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				scheduler.addToWriteQueue((Device) message);
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				System.out.println(error);
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
				// TODO Auto-generated method stub
				try {
					channel.subscribe(device.getId() + "-application");
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
	 * @param scheduler the scheduler to set
	 */
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * @return the scheduler
	 */
	public Scheduler getScheduler() {
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
