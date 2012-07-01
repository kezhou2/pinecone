/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.events;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Bill
 *
 */
public interface MailEvents {

	/**
	 * 
	 */
	static final EventType GET_UNREAD_COUNT = new EventType();
	static final EventType GET_UNREAD = new EventType();
	static final EventType SEND = new EventType();
	static final EventType SETTING = new EventType();
	
}
