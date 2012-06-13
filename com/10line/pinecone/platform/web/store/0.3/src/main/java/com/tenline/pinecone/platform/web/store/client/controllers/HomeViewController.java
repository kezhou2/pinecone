package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.ArrayList;
import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.events.HomeViewEvents;
import com.tenline.pinecone.platform.web.store.client.services.ApplicationService;
import com.tenline.pinecone.platform.web.store.client.services.ApplicationServiceAsync;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerServiceAsync;
import com.tenline.pinecone.platform.web.store.client.services.FriendService;
import com.tenline.pinecone.platform.web.store.client.services.FriendServiceAsync;
import com.tenline.pinecone.platform.web.store.client.services.MailService;
import com.tenline.pinecone.platform.web.store.client.services.MailServiceAsync;
import com.tenline.pinecone.platform.web.store.client.services.UserService;
import com.tenline.pinecone.platform.web.store.client.services.UserServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.HomeView;

public class HomeViewController extends Controller {
	
	private HomeView view = new HomeView(this);
	
	private Collection<Application> userApps;
	private Collection<Friend> userFriends; 
	private Collection<Mail> userMails;
	private int leftStep;
	private int iCount;

	public HomeViewController() {
		registerEventTypes(HomeViewEvents.REFRESH);
		registerEventTypes(HomeViewEvents.INITIALIZE);
		registerEventTypes(HomeViewEvents.LOAD_APPS);
		registerEventTypes(HomeViewEvents.LOAD_FRIEND);
		registerEventTypes(HomeViewEvents.LOAD_MESSAGE);
	}
	
	@Override
	public void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(HomeViewEvents.REFRESH)) {
				
			}else if(event.getType().equals(HomeViewEvents.INITIALIZE)) {
				initialize(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialize(final AppEvent event){
		
		System.out.println("HomeViewController initialize.");
		Object obj = event.getData("currentUser");
		leftStep = 0;
		
		if(obj instanceof User){
			User user = (User)obj;
			
			//initialize home view
			AppEvent appEvent = new AppEvent(HomeViewEvents.INITIALIZE);
			appEvent.setData("currentUser", user);
			forwardToView(view,appEvent);
			
			//load applications info;
			loadApps(user);
			
			//load friends info;
			loadFriends(user);
		}

	}
	
	public void loadApps(final User user){
		System.out.println("HomeViewController initApps.");
		leftStep = 0;
		
		final ApplicationServiceAsync appService = Registry.get(ApplicationService.class.getName());

		appService.showByUser("id=='"+user.getId()+"'", new AsyncCallback<Collection<Application>>(){

			@Override
			public void onFailure(Throwable arg0) {
				leftStep--;
				arg0.printStackTrace();
				Info.display("Error", arg0.getMessage());	
			}

			@Override
			public void onSuccess(Collection<Application> arg0) {
					
				userApps = new ArrayList<Application>(arg0);
				leftStep = userApps.size();
					
				System.out.println("app size:"+leftStep);
				for(final Application app:userApps){
					ConsumerServiceAsync conService = Registry.get(ConsumerService.class.getName());
					conService.show("id=='"+app.getConsumer().getId()+"'", new AsyncCallback<Collection<Consumer>>(){
						@Override
						public void onFailure(Throwable arg0) {
							leftStep--;
							arg0.printStackTrace();
							Info.display("Error", arg0.getMessage());	
						}

						@Override
						public void onSuccess(Collection<Consumer> arg0) {
							for(Consumer con:arg0){
								app.setConsumer(con);
							}
							leftStep--;
								
							//TODO
//							if(leftStep == 0){
								AppEvent appEvent = new AppEvent(HomeViewEvents.LOAD_APPS);
									
								//TODO
								userApps.clear();
								userApps.add(app);
									
								appEvent.setData("userApps", userApps);
								forwardToView(view,appEvent);
//							}
						}
							
					});
						
					break;
				}
					
			}
				
		});
	}
	
	public void loadFriends(final User user){
		System.out.println("HomeViewController loadFriends.");
		
		final FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		final UserServiceAsync userService = Registry.get(UserService.class.getName());
		
		friendService.showByReceiver("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

			@Override
			public void onFailure(Throwable arg0) {
				arg0.printStackTrace();
				Info.display("Error", arg0.getMessage());	
			}

			@Override
			public void onSuccess(Collection<Friend> arg0) {
				System.out.println("############################1");
				
				userFriends = new ArrayList<Friend>(arg0);
				
				friendService.showBySender("id=='"+user.getId()+"'", new AsyncCallback<Collection<Friend>>(){

					@Override
					public void onFailure(Throwable arg0) {
						arg0.printStackTrace();
						Info.display("Error", arg0.getMessage());	
					}

					@Override
					public void onSuccess(Collection<Friend> arg0) {
						System.out.println("############################1");
						userFriends.addAll(arg0);
						
						iCount = userFriends.size();
						for(final Friend f:userFriends){
							
							if(f.getReceiver().getId().equals(user.getId())){
								f.setReceiver(null);
								userService.show("id=='"+f.getSender().getId()+"'", new AsyncCallback<Collection<User>>(){

									@Override
									public void onFailure(Throwable arg0) {
										arg0.printStackTrace();
										Info.display("Error", arg0.getMessage());	
										iCount--;
										
										if(iCount == 0){
											AppEvent appEvent = new AppEvent(HomeViewEvents.LOAD_FRIEND);
											
											appEvent.setData("userFriends", userFriends);
											forwardToView(view,appEvent);
										}
									}

									@Override
									public void onSuccess(Collection<User> arg0) {
										System.out.println("############################3-1");
										if(arg0 != null){
											if(arg0.size() != 0){
												for(User u:arg0){
													f.setSender(u);
												}
											}
										}
										iCount--;
										
										if(iCount == 0){
											AppEvent appEvent = new AppEvent(HomeViewEvents.LOAD_FRIEND);
											
											appEvent.setData("userFriends", userFriends);
											forwardToView(view,appEvent);
										}
									}
									
								});
							}else{
								f.setSender(null);
								userService.show("id=='"+f.getReceiver().getId()+"'", new AsyncCallback<Collection<User>>(){

									@Override
									public void onFailure(Throwable arg0) {
										arg0.printStackTrace();
										Info.display("Error", arg0.getMessage());
										iCount--;
										
										if(iCount == 0){
											AppEvent appEvent = new AppEvent(HomeViewEvents.LOAD_FRIEND);
											
											appEvent.setData("userFriends", userFriends);
											forwardToView(view,appEvent);
										}
									}

									@Override
									public void onSuccess(Collection<User> arg0) {
										System.out.println("############################3-2");
										if(arg0 != null){
											if(arg0.size() != 0){
												for(User u:arg0){
													f.setReceiver(u);
												}
											}
										}
										
										iCount--;
										
										if(iCount == 0){
											AppEvent appEvent = new AppEvent(HomeViewEvents.LOAD_FRIEND);
											
											appEvent.setData("userFriends", userFriends);
											forwardToView(view,appEvent);
										}
									}
									
								});
							}
							
						}	
						
					}
					
				});
			}
			
		});
	}
	
	public void loadMessage(final User user){
		System.out.println("HomeViewController loadMessage.");
		
		final MailServiceAsync mailService = Registry.get(MailService.class.getName());
		mailService.showByReceiver("id=='"+user.getId()+"'", new AsyncCallback<Collection<Mail>>(){

			@Override
			public void onFailure(Throwable arg0) {
				arg0.printStackTrace();
				Info.display("Error", arg0.getMessage());
			}

			@Override
			public void onSuccess(Collection<Mail> arg0) {
				userMails = new ArrayList<Mail>(arg0);
				
				AppEvent appEvent = new AppEvent(HomeViewEvents.LOAD_MESSAGE);
				
				appEvent.setData("userMails", userMails);
				forwardToView(view,appEvent);
			}
			
		});
		
	}
	
}
