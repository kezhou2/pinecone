package com.tenline.pinecone.platform.osgi.monitor.tool;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Administrator
 * 
 */
public class StateSendQueue {
	/**
	 * queue
	 */
	private static StateSendQueue instance;
	private Queue<Object> queue;

	private StateSendQueue() {
		queue = new LinkedList<Object>();
	}

	/**
	 * @return instance
	 */
	public static StateSendQueue getInstance() {
		if (instance == null) {
			instance = new StateSendQueue();
		}
		return instance;
	}

	/**
	 * @param object
	 */
	public void add(Object object) {
		queue.add(object);
	}

	/**
	 * @return
	 */
	public Object poll() {
		if (queue.isEmpty()) {
			return null;
		}
		return queue.poll();
	}

	/**
	 * @return
	 */
	public Object peek() {
		if (queue.isEmpty()) {
			return null;
		}
		return queue.peek();
	}
}
