/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.LinkedList;

import org.apache.mina.core.session.IoSession;

import com.tenline.pinecone.platform.model.Device;

/**
 * @author Bill
 *
 */
public class Scheduler {
	
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
	 * Scheduler Current Time Millis
	 */
	private long currentTimeMillis;
	
	/**
	 * Scheduler Max Time Millis
	 */
	private static final int MAX_TIME_MILLIS = 1000;
	
	/**
	 * Scheduler Last Queue Item
	 */
	private Device lastQueueItem;
	
	/**
	 * Scheduler MINA Session
	 */
	private IoSession session;
	
	/**
	 * 
	 * @param builder
	 */
	public Scheduler(AbstractProtocolBuilder builder) {
		writeQueue = new LinkedList<Device>();
		readQueue = new LinkedList<Device>();
		builder.initializeReadQueue(readQueue);
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
				while(true) {
					if (System.currentTimeMillis() - currentTimeMillis >= MAX_TIME_MILLIS) {
						// offline - notify UI
						execute();
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
	 * @param device
	 */
	private void dispatch(Device device) {
		session.write(device);
		currentTimeMillis = System.currentTimeMillis();
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
	 * @param session the session to set
	 */
	public void setSession(IoSession session) {
		this.session = session;
	}

	/**
	 * @return the session
	 */
	public IoSession getSession() {
		return session;
	}
	
}
