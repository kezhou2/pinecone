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
		logger.info("Start Scheduler");
		execute();
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
		logger.info("executed!");
	}

	/**
	 * Scheduler Dispatch
	 * 
	 * @param device
	 */
	protected abstract void dispatch(Device device);

	/**
	 * Stop Scheduler
	 */
	public void stop() {
		writeQueue.clear();
		readQueue.clear();
		logger.info("Stop Scheduler");
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
