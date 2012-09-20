/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.events;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Bill
 *
 */
public interface FriendEvents {
	
	/**
	 * 
	 */
	static final EventType GET_BY_USER = new EventType();
	static final EventType GET_REQUESTS = new EventType();
	static final EventType CHECK = new EventType();
	static final EventType ADD = new EventType();
	static final EventType DELETE = new EventType();
	static final EventType SETTING = new EventType();
	static final EventType INIT_MAIL_SENDER = new EventType();

}
