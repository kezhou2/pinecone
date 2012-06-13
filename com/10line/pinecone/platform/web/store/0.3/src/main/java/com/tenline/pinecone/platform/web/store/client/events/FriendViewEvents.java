package com.tenline.pinecone.platform.web.store.client.events;

import com.extjs.gxt.ui.client.event.EventType;

public interface FriendViewEvents {
	
	static final EventType INIT_FRIEND_VIEW = new EventType();
	static final EventType LOAD_FRIEND = new EventType();
	static final EventType LOAD_INVITATION = new EventType();
	static final EventType LOAD_USER = new EventType();
}
