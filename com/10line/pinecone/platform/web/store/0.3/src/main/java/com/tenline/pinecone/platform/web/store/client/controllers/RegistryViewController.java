package com.tenline.pinecone.platform.web.store.client.controllers;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.events.LoginViewEvents;
import com.tenline.pinecone.platform.web.store.client.events.RegistryViewEvents;
import com.tenline.pinecone.platform.web.store.client.services.UserService;
import com.tenline.pinecone.platform.web.store.client.services.UserServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.RegistryView;

public class RegistryViewController extends Controller {

	private RegistryView View = new RegistryView(this);
	
	public RegistryViewController() {
		registerEventTypes(RegistryViewEvents.CREATE_USER);
		registerEventTypes(RegistryViewEvents.CANCEL);
	}
	
	@Override
	public void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(RegistryViewEvents.CREATE_USER)) {
				createUser(event);
			}else if(event.getType().equals(RegistryViewEvents.CANCEL)) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createUser(AppEvent event){
		System.out.println("createUser");
		User user = new User();
		user.setName((String)event.getData("name"));
		user.setPassword((String)event.getData("password"));
		user.setEmail((String)event.getData("email"));
		
		UserServiceAsync service = Registry.get(UserService.class.getName());
		service.create(user, new AsyncCallback<User>(){

			@Override
			public void onFailure(Throwable arg0) {
				arg0.printStackTrace();
				Info.display("Error", arg0.getMessage());
			}

			@Override
			public void onSuccess(User arg0) {
				System.out.println("Success in registering user.");
				Info.display("Confirmation","Success in registering user.");
				AppEvent appEvent = new AppEvent(LoginViewEvents.INIT_LOGIN);
				Dispatcher.get().dispatch(appEvent);
			}
			
		});
	}

}
