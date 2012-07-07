/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.FriendViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.LoginViewport;

/**
 * @author Bill
 *
 */
public class UserView extends View {

	/**
	 * @param controller
	 */
	public UserView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(UserEvents.LOGIN)) {
				login(event);
			}else if (event.getType().equals(UserEvents.LOGOUT)) {
				logout(event);
			}else if (event.getType().equals(UserEvents.REGISTER)) {
				register(event);
			} else if (event.getType().equals(UserEvents.CHECK_EMAIL)) {
				
			} else if (event.getType().equals(UserEvents.GET_ALL_USER)) {
				getAllUser(event);
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
	private void login(AppEvent event) throws Exception {
		AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_USERHOME_TO_PANEL);
		Dispatcher.get().dispatch(appEvent);
	}
	
	private void logout(AppEvent event) throws Exception {
		LoginViewport lv = (LoginViewport)Registry.get(LoginViewport.class.getName());
		lv.logout();
		AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_LOGIN_TO_PANEL);
		Dispatcher.get().dispatch(appEvent);
	}                                                   
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void register(AppEvent event) throws Exception {
		Dispatcher.get().dispatch(new AppEvent(WidgetEvents.UPDATE_LOGIN_TO_PANEL));
	}
	
	@SuppressWarnings("unchecked")
	private void getAllUser(AppEvent event) throws Exception{
		Collection<User> users = (Collection<User>)event.getData();
		FriendViewport friendView = (FriendViewport)Registry.get(FriendViewport.class.getName());
		friendView.loadAllUser(users);
	}
	
	private void setting(AppEvent event) throws Exception{
		MessageBox.info("Info", "Setting successfuly!", null);
	}

}
