package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.web.store.client.events.HomeViewEvents;
import com.tenline.pinecone.platform.web.store.client.events.LoginViewEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.LoginViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.RegistryViewport;

public class LoginView extends View  {

	public LoginView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(LoginViewEvents.GO_TO_REGISTRY)) {
				onGoToRegistry(event);
			}else if (event.getType().equals(LoginViewEvents.INIT_LOGIN)) {
				onInit(event);
			}else if( event.getType().equals(LoginViewEvents.USER_LOGIN)){
				onLogin(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onGoToRegistry(AppEvent event){
		RootPanel.get().clear();
		RootPanel.get().add(new RegistryViewport());
	}
	
	public void onInit(AppEvent event){
		RootPanel.get().clear();
		RootPanel.get().add(new LoginViewport());
	}
	
	public void onLogin(AppEvent event){
		AppEvent appEvent = new AppEvent(HomeViewEvents.INITIALIZE);
		appEvent.setData("currentUser", event.getData("currentUser"));
		Dispatcher.get().dispatch(appEvent);
		
	}

}
