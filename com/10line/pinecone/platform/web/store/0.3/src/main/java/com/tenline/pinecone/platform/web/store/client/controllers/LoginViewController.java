package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.events.HomeViewEvents;
import com.tenline.pinecone.platform.web.store.client.events.LoginViewEvents;
import com.tenline.pinecone.platform.web.store.client.services.UserService;
import com.tenline.pinecone.platform.web.store.client.services.UserServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.LoginView;

public class LoginViewController extends Controller {

	private LoginView view = new LoginView(this);
	
	public LoginViewController() {
		registerEventTypes(LoginViewEvents.GO_TO_REGISTRY);
		registerEventTypes(LoginViewEvents.INIT_LOGIN);
		registerEventTypes(LoginViewEvents.USER_LOGIN);
	}

	@Override
	public void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(LoginViewEvents.GO_TO_REGISTRY)) {
				goToRegistry(event);
			}else if(event.getType().equals(LoginViewEvents.INIT_LOGIN)) {
				init(event);
			}else if(event.getType().equals(LoginViewEvents.USER_LOGIN)){
				userLogin(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void goToRegistry(final AppEvent event) throws Exception{
		forwardToView(view, event.getType(), null);
	}
	
	public void init(final AppEvent event) throws Exception{
		forwardToView(view, event.getType(), null);
	}
	
	public void userLogin(final AppEvent event) throws Exception{
		System.out.println("userLogin");
		UserServiceAsync service = Registry.get(UserService.class.getName());
		String name = (String)event.getData("name");
		String pwd = (String)event.getData("password");
		service.show("name=='"+name+"'&&password=='"+pwd+"'", new AsyncCallback<Collection<User>>(){

			@Override
			public void onFailure(Throwable arg0) {
				arg0.printStackTrace();
				Info.display("Error", arg0.getMessage());
			}

			@Override
			public void onSuccess(Collection<User> arg0) {
				for(User u:arg0){
					AppEvent appEvent = new AppEvent(HomeViewEvents.INITIALIZE);
					appEvent.setData("currentUser", u);
					Dispatcher.get().dispatch(appEvent);
//					System.out.println(u);
//					forwardToView(view, event.getType(), u);
					return;
				}
				
				//TODO
				forwardToView(view, event.getType(), null);
			}
			
		});
		
	}
}
