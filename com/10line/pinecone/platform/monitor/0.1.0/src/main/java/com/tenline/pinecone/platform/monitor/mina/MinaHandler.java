/**
 * 
 */
package com.tenline.pinecone.platform.monitor.mina;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.Publisher;
import com.tenline.pinecone.platform.monitor.Subscriber;

/**
 * @author Bill
 * 
 */
public class MinaHandler extends IoHandlerAdapter {
	
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * The Mapping between Device and Session
	 */
	private TreeMap<Device, IoSession> mapping;

	/**
	 * MINA Sessions
	 */
	private Hashtable<Long, IoSession> sessions;

	/**
	 * Schedulers
	 */
	private Hashtable<Long, MinaScheduler> schedulers;

	/**
	 * Subscribers
	 */
	private Hashtable<Long, Subscriber> subscribers;

	/**
	 * Publishers
	 */
	private Hashtable<Long, Publisher> publishers;

	/**
	 * Protocol Builder
	 */
	private AbstractProtocolBuilder builder;

	/**
	 * 
	 */
	public MinaHandler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initialize Handler
	 * 
	 * @param builder
	 */
	public void initialize(AbstractProtocolBuilder builder) {
		this.builder = builder;
		mapping = new TreeMap<Device, IoSession>();
		sessions = new Hashtable<Long, IoSession>();
		schedulers = new Hashtable<Long, MinaScheduler>();
		subscribers = new Hashtable<Long, Subscriber>();
		publishers = new Hashtable<Long, Publisher>();
	}

	/**
	 * Close Handler
	 */
	public void close() {
		for (Enumeration<IoSession> elements = sessions.elements(); elements
				.hasMoreElements();) {
			removeSession(elements.nextElement());
		}
		for (Device keys : mapping.keySet()) {
			mapping.remove(keys);
		}
	}

	/**
	 * 
	 * @param session
	 */
	private void removeSession(IoSession session) {
		schedulers.get(session.getId()).stop();
		schedulers.remove(session.getId());
		subscribers.get(session.getId()).stop();
		subscribers.remove(session.getId());
		publishers.remove(session.getId());
		removeMapping(session);
		sessions.remove(session.getId());
	}

	/**
	 * 
	 * @param session
	 */
	private void putSession(IoSession session) {
		sessions.put(session.getId(), session);
		putMapping(session);
		MinaScheduler scheduler = new MinaScheduler();
		builder.initializeReadQueue(scheduler.getReadQueue());
		schedulers.put(session.getId(), scheduler);
		schedulers.get(session.getId()).setSession(session);
		schedulers.get(session.getId()).start();
		subscribers.put(session.getId(), new Subscriber());
		subscribers.get(session.getId()).setScheduler(
				schedulers.get(session.getId()));
		subscribers.get(session.getId()).setDevice(getDevice(session));
		subscribers.get(session.getId()).start();
		publishers.put(session.getId(), new Publisher());
		publishers.get(session.getId()).setDevice(getDevice(session));
	}

	/**
	 * 
	 * @param session
	 */
	private void removeMapping(IoSession session) {
		for (Device device : mapping.keySet()) {
			if (mapping.get(device).getId() == session.getId()) {
				mapping.put(device, null);
				logger.info("Remove Session (" + session.getId() + ") from Device (" + device.getId() + ")");
				break;
			}
		}
	}

	/**
	 * 
	 * @param session
	 */
	private void putMapping(IoSession session) {
		for (Device device : mapping.keySet()) {
			if (mapping.get(device) == null) {
				mapping.put(device, session);
				logger.info("Put Session (" + session.getId() + ") to Device (" + device.getId() + ")");
				break;
			}
		}
	}

	/**
	 * 
	 * @param session
	 * @return
	 */
	private Device getDevice(IoSession session) {
		for (Device device : mapping.keySet()) {
			if (mapping.get(device).getId() == session.getId()) {
				return device;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public TreeMap<Device, IoSession> getMapping() {
		return mapping;
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		removeSession(session);
		super.sessionClosed(session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		putSession(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		super.messageReceived(session, message);
		schedulers.get(session.getId()).execute();
		publishers.get(session.getId()).publish((Device) message);
	}

}
