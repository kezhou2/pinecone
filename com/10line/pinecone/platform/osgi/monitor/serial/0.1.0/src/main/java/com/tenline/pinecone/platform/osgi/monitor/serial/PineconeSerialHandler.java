package com.tenline.pinecone.platform.osgi.monitor.serial;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.osgi.service.event.Event;

import com.tenline.pinecone.platform.osgi.monitor.model.Command;
import com.tenline.pinecone.platform.osgi.monitor.model.Device;
import com.tenline.pinecone.platform.osgi.monitor.tool.StateSendQueue;

/**
 * @author wangyq
 * 
 */
public class PineconeSerialHandler extends IoHandlerAdapter {
	/**
	 * logger
	 */
	private static Logger logger = Logger
			.getLogger(PineconeSerialHandler.class);
	/**
	 * Communication Session
	 */
	private IoSession session;
	/**
	 * device
	 */
	private Device device;

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}

	/**
	 * 
	 * @param bundleContext
	 * @param id
	 */
	public PineconeSerialHandler(Device device) {
		this.device = device;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.service.event.EventHandler#handleEvent(org.osgi.service.event
	 * .Event)
	 */
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		session.write(event.getProperty("device"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandlerAdapter#exceptionCaught(org.apache
	 * .mina.core.session.IoSession, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		super.exceptionCaught(session, cause);
		logger.error("session exception. " + this.device.getName() + ":"
				+ cause.toString(), cause);
		session.close(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache
	 * .mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		Command command = (Command) message;
		// System.out.println("Recevied :"+NumTools.bytes2HexString(buf.array()));
		logger.info(this.device.getName() + " recevied :" + command.getAlias()
				+ ":" + command.getStatePointList().size());
		if (message != null) {
			// this.device.getDecoder().decode(session, buf, null);
			StateSendQueue.getInstance().add(command);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandlerAdapter#sessionClosed(org.apache
	 * .mina.core.session.IoSession)
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		this.session = null;
		super.sessionClosed(session);
		logger.info("close session ok. " + this.device.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandlerAdapter#sessionCreated(org.apache
	 * .mina.core.session.IoSession)
	 */
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		this.session = session;
		logger.info("create session ok. " + this.device.getName());
	}

}
