package com.tenline.pinecone.platform.web.store.client.views;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.events.HomeViewEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.HomeViewport;

public class HomeView extends View {

	private HomeViewport viewport;
	
	public HomeView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(HomeViewEvents.REFRESH)) {
				onRefresh(event);
			}else if(event.getType().equals(HomeViewEvents.INITIALIZE)){
				onInit(event);
			}else if (event.getType().equals(HomeViewEvents.LOAD_APPS)){
				onLoadApps(event);
			}else if (event.getType().equals(HomeViewEvents.LOAD_FRIEND)){
				onLoadFriends(event);
			}else if( event.getType().equals(HomeViewEvents.LOAD_MESSAGE)){
				onLoadMails(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onRefresh(AppEvent event){
		if(event.getData() != null){
			Registry.register("Application", event.getData());
			
			if(event.getData() instanceof Application){
				RootPanel.get().clear();
				RootPanel.get().add(new HomeViewport((User)event.getData()));
			}
		}
	}
	
	public void onInit(AppEvent event){
		
		System.out.println("onInit");	
		User currentUser = (User)event.getData("currentUser");
		viewport = new HomeViewport(currentUser);
		RootPanel.get().clear();
		RootPanel.get().add(viewport);
		
		 
	}
	
	@SuppressWarnings("unchecked")
	public void onLoadApps(AppEvent event){
		System.out.println("*****onLoadApps");
		
		Collection<Application> userApps = (Collection<Application>)event.getData("userApps");
		viewport.loadApps(userApps);
	}
	
	@SuppressWarnings("unchecked")
	public void onLoadFriends(AppEvent event){
		System.out.println("*****onLoadFriends");
		
		Collection<Friend> userFriends = (Collection<Friend>)event.getData("userFriends");
		viewport.loadFriends(userFriends);
	}
	
	public void onLoadMails(AppEvent event){
		System.out.println("*****onLoadMails");
	}
}
