/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.FriendViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.HomeViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.LoginViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.RegisterViewport;

/**
 * @author Bill
 *
 */
public class WidgetView extends View {

	/**
	 * @param controller
	 */
	public WidgetView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(WidgetEvents.UPDATE_LOGIN_TO_PANEL)) {
				updateLoginToPanel(event);
			} else if (event.getType().equals(WidgetEvents.UPDATE_REGISTER_TO_PANEL)) {
				updateRegisterToPanel(event);
			}else if (event.getType().equals(WidgetEvents.UPDATE_USERHOME_TO_PANEL)) {
				updateUserHomeToPanel(event);
			}else if (event.getType().equals(WidgetEvents.UPDATE_FRIENDS_MANAGE_TO_PANEL)) {
				updateFriendManageToPanel(event);
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
	private void updateLoginToPanel(AppEvent event) throws Exception {
		RootPanel.get().clear();
		RootPanel.get().add((Widget) Registry.get(LoginViewport.class.getName()));
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void updateRegisterToPanel(AppEvent event) throws Exception {
		RootPanel.get().clear();
		RootPanel.get().add((Widget) Registry.get(RegisterViewport.class.getName()));
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void updateUserHomeToPanel(AppEvent event) throws Exception {
		RootPanel.get().clear();
		HomeViewport hv = Registry.get(HomeViewport.class.getName());
		RootPanel.get().add(hv);
	}
	
	private void updateFriendManageToPanel(AppEvent event) throws Exception{
		RootPanel.get().clear();
		FriendViewport fv = Registry.get(FriendViewport.class.getName());
		RootPanel.get().add(fv);
	}

}
