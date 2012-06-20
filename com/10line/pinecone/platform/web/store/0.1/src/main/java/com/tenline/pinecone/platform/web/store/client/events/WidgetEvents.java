/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.events;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Bill
 *
 */
public interface WidgetEvents {
	
	/**
	 * 
	 */
	static final EventType UPDATE_LOGIN_TO_PANEL = new EventType();
	static final EventType UPDATE_REGISTER_TO_PANEL = new EventType();
	static final EventType UPDATE_USERHOME_TO_PANEL = new EventType();
	static final EventType UPDATE_FRIENDS_MANAGE_TO_PANEL = new EventType();

}
