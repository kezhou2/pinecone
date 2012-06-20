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
	private UserServiceAsync service = Registry.get(UserService.class.getName());
	
	/**
	 * 
	 */
	public UserController() {
		registerEventTypes(UserEvents.LOGIN);
		registerEventTypes(UserEvents.LOGOUT);
		registerEventTypes(UserEvents.REGISTER);
		registerEventTypes(UserEvents.CHECK_EMAIL);
		registerEventTypes(UserEvents.SETTING);
	}

	@Override
	public void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(UserEvents.LOGIN)) {
				login(event);
			} else if (event.getType().equals(UserEvents.LOGOUT)) {
				logout(event);
			} else if (event.getType().equals(UserEvents.REGISTER)) {
				register(event);
			} else if (event.getType().equals(UserEvents.CHECK_EMAIL)) {
				checkEmail(event);
			} else if (event.getType().equals(UserEvents.SETTING)) {
				setting(event);
			}
		} catch (Exception e) {
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
		service.show("email=='"+email+"'&&password=='"+password+"'", new AsyncCallback<Collection<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<User> result) {
				if (result.size() == 1) {
					Registry.register(User.class.getName(), result.toArray()[0]);
					forwardToView(view, event.getType(), result);
				}
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void logout(AppEvent event) throws Exception {
		forwardToView(view, event.getType(), Registry.get(User.class.getName()));
		Registry.unregister(User.class.getName());
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void checkEmail(final AppEvent event) throws Exception {
		String email = event.getData("email").toString();
		service.show("email=='"+email+"'", new AsyncCallback<Collection<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<User> result) {
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
		service.create(user, new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(User result) {
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void setting(final AppEvent event) throws Exception {
		byte[] avatar = event.getData("avatar");
		String name = event.getData("name");
		String password = event.getData("password");
		String phone = event.getData("phone");
		User user = Registry.get(User.class.getName());
		if (avatar != null) user.setAvatar(avatar);
		if (name != null) user.setName(name);
		if (password != null) user.setPassword(password);
		if (phone != null) user.setPhone(phone);
		service.update(user, new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(User result) {
				Registry.register(User.class.getName(), result);
				forwardToView(view, event.getType(), result);
			}
			
		});
	}

}
