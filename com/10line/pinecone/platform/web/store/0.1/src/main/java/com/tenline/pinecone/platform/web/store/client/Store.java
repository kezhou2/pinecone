/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.controllers.ApplicationController;
import com.tenline.pinecone.platform.web.store.client.controllers.ConsumerController;
import com.tenline.pinecone.platform.web.store.client.controllers.FriendController;
import com.tenline.pinecone.platform.web.store.client.controllers.MailController;
import com.tenline.pinecone.platform.web.store.client.controllers.UserController;
import com.tenline.pinecone.platform.web.store.client.controllers.WidgetController;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;
import com.tenline.pinecone.platform.web.store.client.services.ApplicationService;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;
import com.tenline.pinecone.platform.web.store.client.services.FriendService;
import com.tenline.pinecone.platform.web.store.client.services.MailService;
import com.tenline.pinecone.platform.web.store.client.services.UserService;
import com.tenline.pinecone.platform.web.store.client.widgets.LoginViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.RegisterViewport;

/**
 * 
 * @author Bill
 *
 */
public class Store implements EntryPoint {
	
	public static final String CURRENT_USER = "current.user";
	
	public static final String APPLICATION_MODEL_FACTORY = "application.model.factory";
	public static final String CONSUMER_MODEL_FACTORY = "consumer.model.factory";
	public static final String FRIEND_MODEL_FACTORY = "friend.model.factory";
	public static final String MAIL_MODEL_FACTORY = "mail.model.factory";
	public static final String USER_MODEL_FACTORY = "user.model.factory";
	
	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
		// Resources
		Registry.register(Images.class.getName(), GWT.create(Images.class));
		Registry.register(Messages.class.getName(), GWT.create(Messages.class));
		
		// RPC Services
		Registry.register(ApplicationService.class.getName(), GWT.create(ApplicationService.class));
		Registry.register(ConsumerService.class.getName(), GWT.create(ConsumerService.class));
		Registry.register(FriendService.class.getName(), GWT.create(FriendService.class));
		Registry.register(MailService.class.getName(), GWT.create(MailService.class));
		Registry.register(UserService.class.getName(), GWT.create(UserService.class));
		
		// Widgets
		Registry.register(LoginViewport.class.getName(), GWT.create(LoginViewport.class));
		Registry.register(RegisterViewport.class.getName(), GWT.create(RegisterViewport.class));
		
		// Bean Model Helpers
		Registry.register(APPLICATION_MODEL_FACTORY, BeanModelLookup.get().getFactory(Application.class));
		Registry.register(CONSUMER_MODEL_FACTORY, BeanModelLookup.get().getFactory(Consumer.class));
		Registry.register(FRIEND_MODEL_FACTORY, BeanModelLookup.get().getFactory(Friend.class));
		Registry.register(MAIL_MODEL_FACTORY, BeanModelLookup.get().getFactory(Mail.class));
		Registry.register(USER_MODEL_FACTORY, BeanModelLookup.get().getFactory(User.class));
		Registry.register(BeanModelReader.class.getName(), GWT.create(BeanModelReader.class));
		
		// MVC Dispatcher Initialization
		Dispatcher dispatcher = Dispatcher.get();
		dispatcher.addController(new ApplicationController());
		dispatcher.addController(new ConsumerController());
		dispatcher.addController(new FriendController());
		dispatcher.addController(new MailController());
		dispatcher.addController(new UserController());
		dispatcher.addController(new WidgetController());
		dispatcher.dispatch(WidgetEvents.UPDATE_LOGIN_TO_PANEL);
	}
	
}
