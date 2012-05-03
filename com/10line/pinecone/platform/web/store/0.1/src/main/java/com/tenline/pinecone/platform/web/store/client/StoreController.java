/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.services.UserService;
import com.tenline.pinecone.platform.web.store.client.services.UserServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.StoreView;

/**
 * @author Bill
 *
 */
public class StoreController extends Controller {
	
	private StoreView view;

	/**
	 * 
	 */
	public StoreController() {
		// TODO Auto-generated constructor stub
		registerEventTypes(StoreEvents.INIT_VIEW);
		registerEventTypes(StoreEvents.LOGIN_USER);
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		if (event.getType().equals(StoreEvents.INIT_VIEW)) {
			initView(event);
		} else if (event.getType().equals(StoreEvents.LOGIN_USER)) {
			loginUser(event);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	private void initView(AppEvent event) {
		view = new StoreView(this);
		forwardToView(view, event);
	}
	
	/**
	 * 
	 * @param event
	 */
	private void loginUser(final AppEvent event) {
		UserServiceAsync userService = Registry.get(UserService.class.getName());
		userService.login(event.getData("email").toString(), event.getData("password").toString(), new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(User result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result);
			}
			
		});     
	}

}
