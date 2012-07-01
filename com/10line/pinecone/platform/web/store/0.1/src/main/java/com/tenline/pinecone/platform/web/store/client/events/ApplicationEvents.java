/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.events;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Bill
 *
 */
public interface ApplicationEvents {

	/**
	 * 
	 */
	static final EventType INSTALL = new EventType();
	static final EventType UNINSTALL = new EventType();
	static final EventType SETTING = new EventType();
	static final EventType GET_BY_OWNER = new EventType();
	
}
