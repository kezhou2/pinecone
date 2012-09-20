/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.web.store.client.controllers.ApplicationController;
import com.tenline.pinecone.platform.web.store.client.controllers.CategoryController;
import com.tenline.pinecone.platform.web.store.client.controllers.ConsumerController;
import com.tenline.pinecone.platform.web.store.client.controllers.FriendController;
import com.tenline.pinecone.platform.web.store.client.controllers.MailController;
import com.tenline.pinecone.platform.web.store.client.controllers.UserController;
import com.tenline.pinecone.platform.web.store.client.controllers.WidgetController;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;
import com.tenline.pinecone.platform.web.store.client.services.ApplicationService;
import com.tenline.pinecone.platform.web.store.client.services.CategoryService;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;
import com.tenline.pinecone.platform.web.store.client.services.FriendService;
import com.tenline.pinecone.platform.web.store.client.services.MailService;
import com.tenline.pinecone.platform.web.store.client.services.UserService;
import com.tenline.pinecone.platform.web.store.client.widgets.AppStoreViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.ConsumerRegistryViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.ConsumerShowViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.FriendViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.HomeViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.LoginViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.MailListViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.ReadMailViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.RegisterViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.SendMailViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.SettingViewport;

/**
 * 
 * @author Bill
 *
 */
public class Store implements EntryPoint {
	
	@Override
	public void onModuleLoad() {
		//register RPC services
		Registry.register(Images.class.getName(), GWT.create(Images.class));
		Registry.register(Messages.class.getName(), GWT.create(Messages.class));
		Registry.register(ApplicationService.class.getName(), GWT.create(ApplicationService.class));
		Registry.register(ConsumerService.class.getName(), GWT.create(ConsumerService.class));
		Registry.register(FriendService.class.getName(), GWT.create(FriendService.class));
		Registry.register(MailService.class.getName(), GWT.create(MailService.class));
		Registry.register(UserService.class.getName(), GWT.create(UserService.class));
		Registry.register(CategoryService.class.getName(), GWT.create(CategoryService.class));
		//register widgets
		Registry.register(LoginViewport.class.getName(), new LoginViewport());
		Registry.register(RegisterViewport.class.getName(), new RegisterViewport());
		Registry.register(HomeViewport.class.getName(), new HomeViewport());
		Registry.register(FriendViewport.class.getName(), new FriendViewport());
		Registry.register(SendMailViewport.class.getName(), new SendMailViewport());
		Registry.register(ConsumerShowViewport.class.getName(), new ConsumerShowViewport());
		Registry.register(MailListViewport.class.getName(), new MailListViewport());
		Registry.register(AppStoreViewport.class.getName(), new AppStoreViewport());
		Registry.register(ConsumerRegistryViewport.class.getName(), new ConsumerRegistryViewport());
		Registry.register(SettingViewport.class.getName(), new SettingViewport());
		Registry.register(ReadMailViewport.class.getName(), new ReadMailViewport());
		
		//set user application
		Registry.register("MyApps", new ArrayList<Application>());
		
		//set controller
		Dispatcher dispatcher = Dispatcher.get();
		dispatcher.addController(new WidgetController());
		dispatcher.addController(new ApplicationController());
		dispatcher.addController(new CategoryController());
		dispatcher.addController(new ConsumerController());
		dispatcher.addController(new FriendController());
		dispatcher.addController(new MailController());
		dispatcher.addController(new UserController());
		
		//initialize login page
		AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_LOGIN_TO_PANEL);
		appEvent.setHistoryEvent(true);
		dispatcher.dispatch(appEvent);
	}
}
