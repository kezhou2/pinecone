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
	static final EventType UPDATE_SEND_MAIL_TO_PANEL = new EventType();
	static final EventType UPDATE_MAX_PORTLET_TO_PANEL = new EventType();
	static final EventType UPDATE_MAIL_LIST_TO_PANEL = new EventType();
	static final EventType UPDATE_APP_STORE_TO_PANEL = new EventType();
	static final EventType UPDATE_REGISTER_APP_TO_PANEL = new EventType();
	static final EventType UPDATE_SETTING_TO_PANEL = new EventType();
	static final EventType UPDATE_CREATE_MAIL_TO_PANEL = new EventType();
}
