/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.Protocol;

/**
 * 
 * @author Bill
 *
 */
public interface ChannelServiceListener extends Protocol {
	
	/** Abort event from server. */
	public void onAbort(Event theEvent);

	/** Data event from server. */
	public void onData(Event theEvent);

	/** Heartbeat event from server. */
	public void onHeartbeat(Event theEvent);

	/** Error occurred. */
	public void onError(String message);
	
}
