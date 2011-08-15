/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.mina;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.mina.core.session.IoSession;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;

import com.tenline.pinecone.platform.model.Device;

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
	 * Endpoint's Id
	 */
	private String endpointId;
	
	/**
	 * 
	 * @param bundleContext
	 * @param endpointId
	 */
	public MinaSerialHandler(BundleContext bundleContext, String endpointId) {
		super(bundleContext);
		// TODO Auto-generated constructor stub
		this.endpointId = endpointId;
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
		Dictionary<String, Object> dic = new Hashtable<String, Object>();
		dic.put("message", (Device) message);
		admin.postEvent(new Event("endpoint/read/" + endpointId, dic));	 
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
