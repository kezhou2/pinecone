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
	static final EventType UPDATE_HOME_TO_PANEL = new EventType();
	static final EventType SHOW_LOGIN_ERROR_DIALOG = new EventType();
	
}
