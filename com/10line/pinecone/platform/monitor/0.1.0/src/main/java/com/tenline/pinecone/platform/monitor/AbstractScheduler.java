/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.LinkedList;

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
	 * Scheduler Timer
	 */
	private Thread timer;

	/**
	 * Scheduler Max Time Millis
	 */
	private static final int MAX_TIME_MILLIS = 5000;

	/**
	 * Scheduler Thread Sleep Time Millis
	 */
	private static final int SLEEP_TIME_MILLIS = 1000;

	/**
	 * Scheduler Current Time Millis
	 */
	private long currentTimeMillis;

	/**
	 * Scheduler Last Queue Item
	 */
	private Device lastQueueItem;

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
		execute();
		timer = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					if (System.currentTimeMillis() - currentTimeMillis >= MAX_TIME_MILLIS) {
						// offline - notify UI
						execute();
					} else {
						try {
							Thread.sleep(SLEEP_TIME_MILLIS);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		timer.start();
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
		try {
			Thread.sleep(SLEEP_TIME_MILLIS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentTimeMillis = System.currentTimeMillis();
		logger.info("Dispatch Successfully!");
	}

	/**
	 * Stop Scheduler
	 */
	public void stop() {
		timer.interrupt();
		writeQueue.clear();
		readQueue.clear();
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
