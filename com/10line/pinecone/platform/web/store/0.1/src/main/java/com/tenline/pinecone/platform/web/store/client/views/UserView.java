/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.google.gwt.core.client.GWT;
import com.tenline.pinecone.platform.web.store.client.Store;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.HomeViewport;

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
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getType().equals(UserEvents.LOGIN)) {
				login(event);
			} else if (event.getType().equals(UserEvents.LOGOUT)) {
				logout();
			} else if (event.getType().equals(UserEvents.REGISTER)) {
				register();
			} else if (event.getType().equals(UserEvents.CHECK_EMAIL)) {
				
			} else if (event.getType().equals(UserEvents.SETTING)) {
				setting(event);
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
	private void login(AppEvent event) throws Exception {
		List<BeanModel> models = event.getData();
		if (models.size() == 1) {
			Registry.register(Store.CURRENT_USER, models.get(0));
			Registry.register(HomeViewport.class.getName(), GWT.create(HomeViewport.class));
			Dispatcher.get().dispatch(WidgetEvents.UPDATE_HOME_TO_PANEL);	
		} else {
			Dispatcher.get().dispatch(WidgetEvents.SHOW_LOGIN_ERROR_DIALOG);
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void logout() throws Exception {
		Registry.unregister(Store.CURRENT_USER);
		Dispatcher.get().dispatch(WidgetEvents.UPDATE_LOGIN_TO_PANEL);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void register() throws Exception {
		Dispatcher.get().dispatch(WidgetEvents.UPDATE_LOGIN_TO_PANEL);
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void setting(AppEvent event) throws Exception {
		Registry.register(Store.CURRENT_USER, event.getData());
	}

}
