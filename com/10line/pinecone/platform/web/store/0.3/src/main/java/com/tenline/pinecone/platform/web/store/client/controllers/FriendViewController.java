package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.ArrayList;
import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.events.FriendViewEvents;
import com.tenline.pinecone.platform.web.store.client.services.FriendService;
import com.tenline.pinecone.platform.web.store.client.services.FriendServiceAsync;
import com.tenline.pinecone.platform.web.store.client.services.UserService;
import com.tenline.pinecone.platform.web.store.client.services.UserServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.FriendView;

public class FriendViewController extends Controller {

	private FriendView view = new FriendView(this);
	
	public FriendViewController() {
		registerEventTypes(FriendViewEvents.INIT_FRIEND_VIEW);
		registerEventTypes(FriendViewEvents.LOAD_FRIEND);
		registerEventTypes(FriendViewEvents.LOAD_INVITATION);
		registerEventTypes(FriendViewEvents.LOAD_USER);
	}
	
	@Override
	public void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(FriendViewEvents.INIT_FRIEND_VIEW)) {
				initView(event);
			}else if(event.getType().equals(FriendViewEvents.LOAD_FRIEND)){
				loadFriends(event);
			}else if(event.getType().equals(FriendViewEvents.LOAD_INVITATION)){
				loadInvitation(event);
			}else if(event.getType().equals(FriendViewEvents.LOAD_USER)){
				loadUser(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initView(AppEvent event){
		System.out.println("FriendViewController initView.");
		Object obj = event.getData("currentUser");
		
		if(obj instanceof User){
			User user = (User)obj;
		//initialize friend view
			AppEvent appEvent = new AppEvent(FriendViewEvents.INIT_FRIEND_VIEW);
			appEvent.setData("currentUser", user);
			forwardToView(view,appEvent);
			
			loadFriends(user);
			loadInvitation(user);
			loadUser(user);
		}
	}
	
	public void loadFriends(final User user){
		System.out.println("FriendViewController loadFriends.");
		
		final FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		final Collection<Friend> userFriends = new ArrayList<Friend>();
		friendService.showByReceiver("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

			@Override
			public void onFailure(Throwable arg0) {
				arg0.printStackTrace();
				Info.display("Error", arg0.getMessage());	
			}

			@Override
			public void onSuccess(Collection<Friend> arg0) {
				for(Friend f:arg0){
					if(f.isDecided()){
						userFriends.add(f);
					}
				}
				
				friendService.showBySender("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

					@Override
					public void onFailure(Throwable arg0) {
						arg0.printStackTrace();
						Info.display("Error", arg0.getMessage());
					}

					@Override
					public void onSuccess(Collection<Friend> arg0) {
						for(Friend f:arg0){
							if(f.isDecided()){
								userFriends.add(f);
							}
						}
						
						AppEvent appEvent = new AppEvent(FriendViewEvents.LOAD_FRIEND);
						appEvent.setData("userFriends", userFriends);
						forwardToView(view,appEvent);
					}
				});
			}
			
		});
	}

	public void loadInvitation(final User user){
		System.out.println("FriendViewController loadInvitation.");
		
		final FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		final Collection<Friend> userInvitation = new ArrayList<Friend>();
		friendService.showByReceiver("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

			@Override
			public void onFailure(Throwable arg0) {
				arg0.printStackTrace();
				Info.display("Error", arg0.getMessage());	
			}

			@Override
			public void onSuccess(Collection<Friend> arg0) {
				for(Friend f:arg0){
					if(!f.isDecided()){
						userInvitation.add(f);
					}
				}
				
				friendService.showBySender("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

					@Override
					public void onFailure(Throwable arg0) {
						arg0.printStackTrace();
						Info.display("Error", arg0.getMessage());
					}

					@Override
					public void onSuccess(Collection<Friend> arg0) {
						for(Friend f:arg0){
							if(!f.isDecided()){
								userInvitation.add(f);
							}
						}
						
						AppEvent appEvent = new AppEvent(FriendViewEvents.LOAD_FRIEND);
						appEvent.setData("userInvitation", userInvitation);
						forwardToView(view,appEvent);
					}
				});
			}
			
		});
	}
	
	public void loadUser(final User user){
		System.out.println("FriendViewController loadUser.");
		
		final FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		final UserServiceAsync userService = Registry.get(UserService.class.getName());
		final Collection<Friend> userFriends = new ArrayList<Friend>();
		friendService.showByReceiver("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

			@Override
			public void onFailure(Throwable arg0) {
				arg0.printStackTrace();
				Info.display("Error", arg0.getMessage());	
			}

			@Override
			public void onSuccess(Collection<Friend> arg0) {
				for(Friend f:arg0){
					if(!f.isDecided()){
						userFriends.add(f);
					}
				}
				
				friendService.showBySender("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

					@Override
					public void onFailure(Throwable arg0) {
						arg0.printStackTrace();
						Info.display("Error", arg0.getMessage());
					}

					@Override
					public void onSuccess(Collection<Friend> arg0) {
						for(Friend f:arg0){
							if(!f.isDecided()){
								userFriends.add(f);
							}
						}
						
						userService.show("id=='"+user.getId()+"'", new AsyncCallback<Collection<User>>(){

							@Override
							public void onFailure(Throwable arg0) {
								arg0.printStackTrace();
								Info.display("Error", arg0.getMessage());
							}

							@Override
							public void onSuccess(Collection<User> arg0) {
								Collection<User> users = new ArrayList<User>();
								users.addAll(arg0);
								AppEvent appEvent = new AppEvent(FriendViewEvents.LOAD_USER);
								appEvent.setData("allUsers", users);
								forwardToView(view,appEvent);
							}
							
						});
					}
				});
			}
			
		});
	}
	
	public void loadFriends(AppEvent event){
		System.out.println("FriendViewController loadFriends.");
		
		Object obj = event.getData("curUser");
		if(obj == null){
			return;
		}
		
		if(!(obj instanceof User)){
			return;
		}
		final User user = (User)obj;
		
		final FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		final Collection<Friend> userFriends = new ArrayList<Friend>();
		friendService.showByReceiver("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

			@Override
			public void onFailure(Throwable arg0) {
				arg0.printStackTrace();
				Info.display("Error", arg0.getMessage());	
			}

			@Override
			public void onSuccess(Collection<Friend> arg0) {
				for(Friend f:arg0){
					if(f.isDecided()){
						userFriends.add(f);
					}
				}
				
				friendService.showBySender("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

					@Override
					public void onFailure(Throwable arg0) {
						arg0.printStackTrace();
						Info.display("Error", arg0.getMessage());
					}

					@Override
					public void onSuccess(Collection<Friend> arg0) {
						for(Friend f:arg0){
							if(f.isDecided()){
								userFriends.add(f);
							}
						}
						
						AppEvent appEvent = new AppEvent(FriendViewEvents.LOAD_FRIEND);
						appEvent.setData("userFriends", userFriends);
						forwardToView(view,appEvent);
					}
				});
			}
			
		});
	}

	public void loadInvitation(AppEvent event){
		System.out.println("FriendViewController loadInvitation.");
		
		Object obj = event.getData("curUser");
		if(obj == null){
			return;
		}
		
		if(!(obj instanceof User)){
			return;
		}
		final User user = (User)obj;
		
		final FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		final Collection<Friend> userInvitation = new ArrayList<Friend>();
		friendService.showByReceiver("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

			@Override
			public void onFailure(Throwable arg0) {
				arg0.printStackTrace();
				Info.display("Error", arg0.getMessage());	
			}

			@Override
			public void onSuccess(Collection<Friend> arg0) {
				for(Friend f:arg0){
					if(!f.isDecided()){
						userInvitation.add(f);
					}
				}
				
				friendService.showBySender("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

					@Override
					public void onFailure(Throwable arg0) {
						arg0.printStackTrace();
						Info.display("Error", arg0.getMessage());
					}

					@Override
					public void onSuccess(Collection<Friend> arg0) {
						for(Friend f:arg0){
							if(!f.isDecided()){
								userInvitation.add(f);
							}
						}
						
						AppEvent appEvent = new AppEvent(FriendViewEvents.LOAD_FRIEND);
						appEvent.setData("userInvitation", userInvitation);
						forwardToView(view,appEvent);
					}
				});
			}
			
		});
	}
	
	public void loadUser(AppEvent event){
		System.out.println("FriendViewController loadUser.");
		
		Object obj = event.getData("curUser");
		if(obj == null){
			return;
		}
		
		if(!(obj instanceof User)){
			return;
		}
		final User user = (User)obj;
		
		final FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		final UserServiceAsync userService = Registry.get(UserService.class.getName());
		final Collection<Friend> userFriends = new ArrayList<Friend>();
		friendService.showByReceiver("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

			@Override
			public void onFailure(Throwable arg0) {
				arg0.printStackTrace();
				Info.display("Error", arg0.getMessage());	
			}

			@Override
			public void onSuccess(Collection<Friend> arg0) {
				for(Friend f:arg0){
					if(!f.isDecided()){
						userFriends.add(f);
					}
				}
				
				friendService.showBySender("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

					@Override
					public void onFailure(Throwable arg0) {
						arg0.printStackTrace();
						Info.display("Error", arg0.getMessage());
					}

					@Override
					public void onSuccess(Collection<Friend> arg0) {
						for(Friend f:arg0){
							if(!f.isDecided()){
								userFriends.add(f);
							}
						}
						
						userService.show("id=='"+user.getId()+"'", new AsyncCallback<Collection<User>>(){

							@Override
							public void onFailure(Throwable arg0) {
								arg0.printStackTrace();
								Info.display("Error", arg0.getMessage());
							}

							@Override
							public void onSuccess(Collection<User> arg0) {
								Collection<User> users = new ArrayList<User>();
								users.addAll(arg0);
								AppEvent appEvent = new AppEvent(FriendViewEvents.LOAD_USER);
								appEvent.setData("allUsers", users);
								forwardToView(view,appEvent);
							}
							
						});
					}
				});
			}
			
		});
	}
}
