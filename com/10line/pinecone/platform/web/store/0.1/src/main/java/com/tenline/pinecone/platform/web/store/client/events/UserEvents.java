/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.events;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Bill
 *
 */
public interface UserEvents {
	
	/**
	 * 
	 */
	static final EventType LOGIN = new EventType();
	static final EventType LOGOUT = new EventType();
	static final EventType REGISTER = new EventType();
	static final EventType CHECK_EMAIL = new EventType();
	static final EventType SETTING = new EventType();

}
