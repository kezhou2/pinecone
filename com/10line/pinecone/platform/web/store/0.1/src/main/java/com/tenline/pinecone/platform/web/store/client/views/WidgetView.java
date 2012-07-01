/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;
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
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getType().equals(WidgetEvents.UPDATE_LOGIN_TO_PANEL)) {
				updateLoginToPanel();
			} else if (event.getType().equals(WidgetEvents.UPDATE_REGISTER_TO_PANEL)) {
				updateRegisterToPanel();
			} else if (event.getType().equals(WidgetEvents.UPDATE_HOME_TO_PANEL)) {
				updateHomeToPanel();
			} else if (event.getType().equals(WidgetEvents.SHOW_LOGIN_ERROR_DIALOG)) {
				showLoginErrorDialog();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void updateLoginToPanel() throws Exception {
		RootPanel.get().clear();
		RootPanel.get().add((Widget) Registry.get(LoginViewport.class.getName()));
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void updateRegisterToPanel() throws Exception {
		RootPanel.get().clear();
		RootPanel.get().add((Widget) Registry.get(RegisterViewport.class.getName()));
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void updateHomeToPanel() throws Exception {
		RootPanel.get().clear();
		RootPanel.get().add((Widget) Registry.get(HomeViewport.class.getName()));
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void showLoginErrorDialog() throws Exception {
		RootPanel.get().clear();
		String title = ((Messages) Registry.get(Messages.class.getName())).loginErrorTitle();
		String msg = ((Messages) Registry.get(Messages.class.getName())).loginErrorMessage();
		MessageBox.info(title, msg, new Listener<MessageBoxEvent>() {

			@Override
			public void handleEvent(MessageBoxEvent event) {
				// TODO Auto-generated method stub
				Dispatcher.get().dispatch(WidgetEvents.UPDATE_LOGIN_TO_PANEL);
			}
			
		});
	}

}
