/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.web.store.client.controllers.ApplicationController;
import com.tenline.pinecone.platform.web.store.client.controllers.ConsumerController;
import com.tenline.pinecone.platform.web.store.client.controllers.FriendController;
import com.tenline.pinecone.platform.web.store.client.controllers.MailController;
import com.tenline.pinecone.platform.web.store.client.controllers.UserController;
import com.tenline.pinecone.platform.web.store.client.services.ApplicationService;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;
import com.tenline.pinecone.platform.web.store.client.services.FriendService;
import com.tenline.pinecone.platform.web.store.client.services.MailService;
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
		Registry.register(ApplicationService.class.getName(), GWT.create(ApplicationService.class));
		Registry.register(ConsumerService.class.getName(), GWT.create(ConsumerService.class));
		Registry.register(FriendService.class.getName(), GWT.create(FriendService.class));
		Registry.register(MailService.class.getName(), GWT.create(MailService.class));
		Registry.register(UserService.class.getName(), GWT.create(UserService.class));
		Dispatcher dispatcher = Dispatcher.get();
		dispatcher.addController(new ApplicationController());
		dispatcher.addController(new ConsumerController());
		dispatcher.addController(new FriendController());
		dispatcher.addController(new MailController());
		dispatcher.addController(new UserController());
		RootPanel.get().add(new HomeViewport());
	}
	
}
