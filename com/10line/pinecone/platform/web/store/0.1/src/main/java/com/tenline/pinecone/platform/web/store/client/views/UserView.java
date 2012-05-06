/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;

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
		if (event.getType().equals(UserEvents.LOGIN)) {
			
		} else if (event.getType().equals(UserEvents.REGISTER)) {
			
		} else if (event.getType().equals(UserEvents.CHECK_EMAIL)) {
			
		}
	}

}
