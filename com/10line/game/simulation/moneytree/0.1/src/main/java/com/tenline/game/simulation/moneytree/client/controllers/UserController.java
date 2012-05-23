/**
 * 
 */
package com.tenline.game.simulation.moneytree.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.game.simulation.moneytree.client.events.UserEvents;
import com.tenline.game.simulation.moneytree.client.services.UserService;
import com.tenline.game.simulation.moneytree.client.services.UserServiceAsync;
import com.tenline.game.simulation.moneytree.client.views.UserView;
import com.tenline.pinecone.platform.model.User;

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
		// TODO Auto-generated constructor stub
		registerEventTypes(UserEvents.GET);
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getType().equals(UserEvents.GET)) {
				get(event);
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
	private void get(final AppEvent event) throws Exception {
		service.show("name=='"+event.getData("name").toString()+"'", new AsyncCallback<Collection<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<User> result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result.toArray()[0]);
			}
			
		});
	}

}
