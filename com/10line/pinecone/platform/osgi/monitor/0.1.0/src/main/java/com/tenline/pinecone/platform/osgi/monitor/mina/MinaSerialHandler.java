/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.mina;

import org.apache.mina.core.session.IoSession;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;

/**
 * @author Bill
 *
 */
public class MinaSerialHandler extends AbstractMinaHandler {

	/**
	 * Communication Session
	 */
	private IoSession session; 
	
	/**
	 * 
	 * @param bundleContext
	 */
	public MinaSerialHandler(BundleContext bundleContext) {
		super(bundleContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		session.write(event.getProperty("message"));
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		super.exceptionCaught(session, cause);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		this.session = null;
		super.sessionClosed(session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		// TODO Auto-generated method stub
		this.session = session;
	}
	
}
