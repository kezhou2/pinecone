/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.events;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Bill
 *
 */
public interface ModelEvents {
	
	/**
	 * 
	 */
	static final EventType CREATE = new EventType();
	static final EventType DELETE = new EventType();
	static final EventType UPDATE = new EventType();
	static final EventType SHOW = new EventType();

}
