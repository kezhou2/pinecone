package com.tenline.pinecone.platform.web.store.client.events;

import com.extjs.gxt.ui.client.event.EventType;

public interface HomeViewEvents {
	static final EventType REFRESH = new EventType();
	static final EventType INITIALIZE = new EventType();
	
	static final EventType LOAD_APPS = new EventType();
	static final EventType LOAD_FRIEND = new EventType();
	static final EventType LOAD_MESSAGE = new EventType();
}
