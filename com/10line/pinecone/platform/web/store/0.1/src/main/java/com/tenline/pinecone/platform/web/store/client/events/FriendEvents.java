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
	static final EventType GET_BY_SENDER = new EventType();
	static final EventType GET_BY_RECEIVER = new EventType();
	static final EventType GET_INVITATIONS = new EventType();
	static final EventType SEND_INVITATION = new EventType();
	static final EventType BREAK_OFF_RELATION = new EventType();
	static final EventType SETTING = new EventType();

}
