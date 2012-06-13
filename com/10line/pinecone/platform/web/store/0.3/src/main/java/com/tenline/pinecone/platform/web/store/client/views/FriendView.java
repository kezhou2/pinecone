package com.tenline.pinecone.platform.web.store.client.views;

import java.util.Collection;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.events.FriendViewEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.FriendViewport;

public class FriendView extends View {

	private FriendViewport viewport;
	
	public FriendView(Controller controller) {
		super(controller);
		
	}

	@Override
	protected void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(FriendViewEvents.INIT_FRIEND_VIEW)) {
				onInit(event);
			}else if (event.getType().equals(FriendViewEvents.LOAD_FRIEND)) {
				onLoadFriends(event);
			}if (event.getType().equals(FriendViewEvents.LOAD_INVITATION)) {
				onLoadInvitation(event);
			}if (event.getType().equals(FriendViewEvents.LOAD_USER)) {
				onLoadUser(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void onInit(AppEvent event){
		System.out.println("FriendView onInit");	
		User currentUser = (User)event.getData("currentUser");
		viewport = new FriendViewport(currentUser);
		System.out.println(viewport);
		RootPanel.get().clear();
		RootPanel.get().add(viewport);
	}
	
	@SuppressWarnings("unchecked")
	public void onLoadFriends(AppEvent event){
		System.out.println("FriendView onLoadFriends");	
		Collection<Friend> userFriends = (Collection<Friend>)event.getData("userFriends");
		viewport.loadFriends(userFriends);
	}

	@SuppressWarnings("unchecked")
	public void onLoadInvitation(AppEvent event){
		System.out.println("FriendView onLoadInvitation");	
		Collection<Friend> userInvitation = (Collection<Friend>)event.getData("userInvitation");
		viewport.loadFriendInvite(userInvitation);
	}
	
	@SuppressWarnings("unchecked")
	public void onLoadUser(AppEvent event){
		System.out.println("FriendView onLoadUser");	
		Collection<User> users = (Collection<User>)event.getData("allUsers");
		viewport.loadAllUser(users);
	}
}
