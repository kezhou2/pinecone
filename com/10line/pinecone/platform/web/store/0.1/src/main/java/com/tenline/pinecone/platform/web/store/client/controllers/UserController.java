/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.services.UserService;
import com.tenline.pinecone.platform.web.store.client.services.UserServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.UserView;
import com.tenline.pinecone.platform.web.store.client.widgets.AbstractViewport;

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
		registerEventTypes(UserEvents.GET_ALL_USER);
	}

	@Override
	public void handleEvent(AppEvent event) {
		try {
			mask();
			
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
			} else if (event.getType().equals(UserEvents.GET_ALL_USER)) {
				getAllUsers(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
			unmask();
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
				unmask();
			}

			@Override
			public void onSuccess(Collection<User> result) {
				unmask();
				if (result.size() == 1) {
					Registry.register(User.class.getName(), result.toArray()[0]);
					forwardToView(view, event.getType(), result);
				}else{
					MessageBox mb = MessageBox.alert(((Messages) Registry.get(Messages.class.getName())).LoginViewport_messagebox_title(),
							((Messages) Registry.get(Messages.class.getName())).LoginViewport_messagebox_info(), null);
					mb.getDialog().toFront();
					mb.show();
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
		unmask();
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
				unmask();
			}

			@Override
			public void onSuccess(Collection<User> result) {
				unmask();
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
		user.setName(event.getData("name").toString());
		service.create(user, new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(User result) {
				unmask();
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
		String email = event.getData("email");
		User user = Registry.get(User.class.getName());
		if (avatar != null) user.setAvatar(avatar);
		if (name != null) user.setName(name);
		if (password != null) user.setPassword(password);
		if (phone != null) user.setPhone(phone);
		if (email != null) user.setEmail(email);
		service.update(user, new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(User result) {
				unmask();
				Registry.register(User.class.getName(), result);
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
	private void getAllUsers(final AppEvent event) throws Exception{
		User user = (User)Registry.get(User.class.getName());
		service.show("id!='"+user.getId()+"'", new AsyncCallback<Collection<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Collection<User> result) {
				unmask();
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	/**
	 * unmask the viewport
	 */
	private void unmask(){
		if(RootPanel.get().getWidgetCount() > 0){
			AbstractViewport av = (AbstractViewport)(RootPanel.get().getWidget(0));
			av.unmask();
		}
	}
	/**
	 * mask the viewport
	 */
	private void mask(){
		if(RootPanel.get().getWidgetCount() > 0){
			AbstractViewport av = (AbstractViewport)(RootPanel.get().getWidget(0));
			av.mask(((Messages) Registry.get(Messages.class.getName())).loadingInfo());
		}
	}
}
