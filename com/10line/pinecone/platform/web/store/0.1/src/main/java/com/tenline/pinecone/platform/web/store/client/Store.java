/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.web.store.client.controllers.UserController;
import com.tenline.pinecone.platform.web.store.client.services.UserService;
import com.tenline.pinecone.platform.web.store.client.widgets.HomeViewport;

/**
 * 
 * @author Bill
 *
 */
public class Store implements EntryPoint {
	
	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		Registry.register(Images.class.getName(), GWT.create(Images.class));
		Registry.register(Messages.class.getName(), GWT.create(Messages.class));
		Registry.register(UserService.class.getName(), GWT.create(UserService.class));
		Dispatcher dispatcher = Dispatcher.get();
		dispatcher.addController(new UserController());
		RootPanel.get().add(new HomeViewport());
	}
	
}
