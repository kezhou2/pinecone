package com.tenline.pinecone.platform.osgi.monitor.tool;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Administrator
 * 
 */
public class CommandReceiveQueue {
	/**
	 * queue
	 */
	private static CommandReceiveQueue instance;
	private Queue<Object> queue;

	private CommandReceiveQueue() {
		queue = new LinkedList<Object>();
	}

	/**
	 * @return instance
	 */
	public static CommandReceiveQueue getInstance() {
		if (instance == null) {
			instance = new CommandReceiveQueue();
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
