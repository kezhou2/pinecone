/**
 * 
 */
package com.tenline.pinecone.platform.monitor.mina;

import org.apache.mina.core.session.IoSession;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.AbstractScheduler;

/**
 * @author Bill
 *
 */
public class MinaScheduler extends AbstractScheduler {
	
	/**
	 * Scheduler MINA Session
	 */
	private IoSession session;
	
	/**
	 * 
	 */
	public MinaScheduler() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void dispatch(Device device) {
		session.write(device);	
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
