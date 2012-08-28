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
	static final EventType REGISTER_USER = new EventType();
	static final EventType LOGIN_USER = new EventType();
	static final EventType LOGOUT_USER = new EventType();
	
	static final EventType REGISTER_CONSUMER = new EventType();
	static final EventType GET_ALL_CONSUMER = new EventType();
	
	static final EventType INSTALL_APPLICATION = new EventType();
	static final EventType GET_APPLICATION_BY_USER = new EventType(); 
	
	static final EventType GET_FRIEND_BY_SENDER = new EventType();
	static final EventType GET_FRIEND_BY_RECEIVER = new EventType();

}
