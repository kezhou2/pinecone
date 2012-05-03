/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Bill
 *
 */
public interface StoreEvents {
	
	static final EventType INIT_VIEW = new EventType();
	static final EventType LOGIN_USER = new EventType();
	static final EventType REGISTER_USER = new EventType();

}
