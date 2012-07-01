/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.events;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Bill
 *
 */
public interface ConsumerEvents {
	
	/**
	 * 
	 */
	static final EventType GET_BY_CATEGORY = new EventType();
	static final EventType REGISTER = new EventType();
	static final EventType UNREGISTER = new EventType();
	static final EventType SETTING = new EventType();
	static final EventType GET_ALL = new EventType();
	static final EventType GET_BY_NAME = new EventType();
}
