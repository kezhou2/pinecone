/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

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
			} else if (event.getType().equals(UserEvents.REGISTER)) {
				register(event);
			} else if (event.getType().equals(UserEvents.CHECK_EMAIL)) {
				
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
		
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void register(AppEvent event) throws Exception {
		Dispatcher.get().dispatch(new AppEvent(WidgetEvents.UPDATE_LOGIN_TO_PANEL));
	}

}
