/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.tenline.pinecone.platform.web.store.client.controllers.ModelController;
import com.tenline.pinecone.platform.web.store.client.services.ModelService;
import com.tenline.pinecone.platform.web.store.client.widgets.ApplicationViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.FriendViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.HomeViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.LoginViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.RegisterViewport;

/**
 * 
 * @author Bill
 *
 */
public class Store implements EntryPoint {
	
	public static final String CURRENT_OWNER = "current.owner";
	public static final String CURRENT_VIEWER = "current.viewer";
	
	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
		// Resources
		Registry.register(Images.class.getName(), GWT.create(Images.class));
		Registry.register(Messages.class.getName(), GWT.create(Messages.class));
		
		// RPC Services
		Registry.register(ModelService.class.getName(), GWT.create(ModelService.class));
		
		// Widgets
		Registry.register(ApplicationViewport.class.getName(), GWT.create(ApplicationViewport.class));
		Registry.register(FriendViewport.class.getName(), GWT.create(FriendViewport.class));
		Registry.register(HomeViewport.class.getName(), GWT.create(HomeViewport.class));
		Registry.register(LoginViewport.class.getName(), GWT.create(LoginViewport.class));
		Registry.register(RegisterViewport.class.getName(), GWT.create(RegisterViewport.class));
		
		// MVC Dispatcher Initialization
		Dispatcher dispatcher = Dispatcher.get();
		dispatcher.addController(new ModelController());
		
		// Initialize to Login Viewport
		LoginViewport view = Registry.get(LoginViewport.class.getName());
		view.updateToRootPanel();
	}
	
}
