/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
		Registry.register(UserService.class.getName(), GWT.create(UserService.class));
		Dispatcher dispatcher = Dispatcher.get();
		dispatcher.addController(new StoreController());
		dispatcher.dispatch(StoreEvents.INIT_VIEW);
	}
	
}
