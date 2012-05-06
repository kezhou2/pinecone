/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.services.UserService;
import com.tenline.pinecone.platform.web.store.client.services.UserServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.UserView;

/**
 * @author Bill
 *
 */
public class UserController extends Controller {

	private UserView view = new UserView(this);
	
	/**
	 * 
	 */
	public UserController() {
		// TODO Auto-generated constructor stub
		registerEventTypes(UserEvents.LOGIN);
		registerEventTypes(UserEvents.REGISTER);
		registerEventTypes(UserEvents.CHECK_EMAIL);
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getType().equals(UserEvents.LOGIN)) {
				login(event);
			} else if (event.getType().equals(UserEvents.REGISTER)) {
				register(event);
			} else if (event.getType().equals(UserEvents.CHECK_EMAIL)) {
				checkEmail(event);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void login(final AppEvent event) throws Exception {
		String email = event.getData("email").toString();
		String password = event.getData("password").toString();
		UserServiceAsync userService = Registry.get(UserService.class.getName());
		userService.show("email=='"+email+"'&&password=='"+password+"'", new AsyncCallback<Collection<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<User> result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void checkEmail(final AppEvent event) throws Exception {
		String email = event.getData("email").toString();
		UserServiceAsync userService = Registry.get(UserService.class.getName());
		userService.show("email=='"+email+"'", new AsyncCallback<Collection<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<User> result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void register(final AppEvent event) throws Exception {
		User user = new User();
		user.setEmail(event.getData("email").toString());
		user.setPassword(event.getData("password").toString());
		UserServiceAsync userService = Registry.get(UserService.class.getName());
		userService.create(user, new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(User result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result);
			}
			
		});
	}

}
