/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.tenline.pinecone.platform.web.store.client.controllers.FriendViewController;
import com.tenline.pinecone.platform.web.store.client.controllers.HomeViewController;
import com.tenline.pinecone.platform.web.store.client.controllers.LoginViewController;
import com.tenline.pinecone.platform.web.store.client.controllers.RegistryViewController;
import com.tenline.pinecone.platform.web.store.client.events.LoginViewEvents;
import com.tenline.pinecone.platform.web.store.client.services.ApplicationService;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;
import com.tenline.pinecone.platform.web.store.client.services.FriendService;
import com.tenline.pinecone.platform.web.store.client.services.MailService;
import com.tenline.pinecone.platform.web.store.client.services.UserService;

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
		dispatcher.addController(new LoginViewController());
		dispatcher.addController(new HomeViewController());
		dispatcher.addController(new RegistryViewController());
		dispatcher.addController(new FriendViewController());
//		RootPanel.get().add(new LoginViewport());
//		RootPanel.get().add(new RegistryViewport());
		
		//initialize login page
		AppEvent appEvent = new AppEvent(LoginViewEvents.INIT_LOGIN);
		appEvent.setHistoryEvent(true);
		dispatcher.dispatch(appEvent);
	}
	
}
