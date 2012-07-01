/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;
import com.tenline.pinecone.platform.web.store.client.views.WidgetView;

/**
 * @author Bill
 *
 */
public class WidgetController extends Controller {
	
	private WidgetView view = new WidgetView(this);

	/**
	 * 
	 */
	public WidgetController() {
		registerEventTypes(WidgetEvents.UPDATE_LOGIN_TO_PANEL);
		registerEventTypes(WidgetEvents.UPDATE_REGISTER_TO_PANEL);
		registerEventTypes(WidgetEvents.UPDATE_USERHOME_TO_PANEL);
		registerEventTypes(WidgetEvents.UPDATE_FRIENDS_MANAGE_TO_PANEL);
		registerEventTypes(WidgetEvents.UPDATE_SEND_MAIL_TO_PANEL);
		registerEventTypes(WidgetEvents.UPDATE_MAX_PORTLET_TO_PANEL);
		registerEventTypes(WidgetEvents.UPDATE_MAIL_LIST_TO_PANEL);
		registerEventTypes(WidgetEvents.UPDATE_APP_STORE_TO_PANEL);
	}

	@Override
	public void handleEvent(AppEvent event) {
		try{
			if (event.getType().equals(WidgetEvents.UPDATE_LOGIN_TO_PANEL)) {
				forwardToView(view, event);
			} else if (event.getType().equals(WidgetEvents.UPDATE_REGISTER_TO_PANEL)) {
				forwardToView(view, event);
			} else if (event.getType().equals(WidgetEvents.UPDATE_USERHOME_TO_PANEL)) {
				forwardToView(view, event);
			} else if (event.getType().equals(WidgetEvents.UPDATE_FRIENDS_MANAGE_TO_PANEL)) {
				forwardToView(view, event);
			} else if (event.getType().equals(WidgetEvents.UPDATE_SEND_MAIL_TO_PANEL)) {
				forwardToView(view, event);
			} else if (event.getType().equals(WidgetEvents.UPDATE_MAX_PORTLET_TO_PANEL)) {
				forwardToView(view, event);
			} else if (event.getType().equals(WidgetEvents.UPDATE_MAIL_LIST_TO_PANEL)) {
				forwardToView(view, event);
			} else if (event.getType().equals(WidgetEvents.UPDATE_APP_STORE_TO_PANEL)) {
				forwardToView(view, event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
