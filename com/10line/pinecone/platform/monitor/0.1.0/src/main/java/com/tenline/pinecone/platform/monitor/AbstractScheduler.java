/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Device;

/**
 * @author Bill
 * 
 */
public abstract class AbstractScheduler {

	/**
	 * Scheduler Logger
	 */
	protected Logger logger = Logger.getLogger(getClass());

	/**
	 * Scheduler Write Queue
	 */
	private LinkedList<Device> writeQueue;

	/**
	 * Scheduler Read Queue
	 */
	private LinkedList<Device> readQueue;

	/**
	 * Scheduler Read Queue Index
	 */
	private int readIndex;

	/**
	 * Scheduler Last Queue Item
	 */
	private Device lastQueueItem;
	
	/**
	 * Scheduler Timer
	 */
	private Timer timer;

	/**
	 * Scheduler Timer Task
	 */
	private TimerTask task;
	
	/**
	 * Scheduler Current Time Millis
	 */
	private long currentTimeMillis;
	
	/**
	 * Scheduler Max Time Millis
	 */
	private static final int MAX_TIME_MILLIS = 5000;
	
	/**
	 * Scheduler Task Interval
	 */
	private static final int INTERVAL = 1000;

	/**
	 * Scheduler Task Interval After Task Starting
	 */
	private static final int AFTER_START_INTERVAL = 0;

	/**
	 * 
	 */
	public AbstractScheduler() {
		// TODO Auto-generated constructor stub
		writeQueue = new LinkedList<Device>();
		readQueue = new LinkedList<Device>();
	}

	/**
	 * Start Scheduler
	 */
	public void start() {
		update();
		timer = new Timer();
		task = new TimerTask() {

			@Override
			public void run() {
				if (System.currentTimeMillis() - currentTimeMillis >= MAX_TIME_MILLIS) {
					// offline - notify UI
					update();
				} else {
					execute();
				}
			}

		};
		timer.schedule(task, AFTER_START_INTERVAL, INTERVAL);
	}

	/**
	 * Execute Scheduler
	 */
	public void execute() {
		if (!writeQueue.isEmpty()) {
			lastQueueItem = writeQueue.removeFirst();
			dispatch(lastQueueItem);
		} else {
			if (!readQueue.isEmpty()) {
				readIndex %= readQueue.size();
				lastQueueItem = readQueue.get(readIndex);
				dispatch(lastQueueItem);
				readIndex++;
			}
		}
	}

	/**
	 * Dispatch to endpoint
	 * 
	 * @param device
	 */
	protected void dispatch(Device device) {
		logger.info("Dispatch Successfully!");
	}

	/**
	 * Stop Scheduler
	 */
	public void stop() {
		task.cancel();
		timer.purge();
		writeQueue.clear();
		readQueue.clear();
	}
	
	/**
	 * Update Scheduler
	 */
	public void update() {
		currentTimeMillis = System.currentTimeMillis();
	}

	/**
	 * 
	 * @param device
	 */
	public void addToWriteQueue(Device device) {
		writeQueue.addLast(device);
	}

	/**
	 * 
	 * @param device
	 */
	public void removeFromWriteQueue(Device device) {
		writeQueue.remove(device);
	}

	/**
	 * 
	 * @param device
	 */
	public void addToReadQueue(Device device) {
		readQueue.addLast(device);
	}

	/**
	 * 
	 * @param device
	 */
	public void removeFromReadQueue(Device device) {
		readQueue.remove(device);
	}

	/**
	 * 
	 * @return
	 */
	public LinkedList<Device> getReadQueue() {
		return readQueue;
	}

}
