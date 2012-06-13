package com.tenline.pinecone.platform.web.store.client.events;

import com.extjs.gxt.ui.client.event.EventType;

public interface LoginViewEvents {
	
	static final EventType INIT_LOGIN = new EventType();
	static final EventType GO_TO_REGISTRY = new EventType();
	static final EventType USER_LOGIN = new EventType();
}
