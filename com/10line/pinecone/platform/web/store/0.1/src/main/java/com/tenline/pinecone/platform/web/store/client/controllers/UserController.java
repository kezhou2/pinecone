/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BaseLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Store;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.services.UserService;
import com.tenline.pinecone.platform.web.store.client.services.UserServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.UserView;

/**
 * @author Bill
 *
 */
public class UserController extends Controller {

	private UserView view = new UserView(this);
	private UserServiceAsync service = Registry.get(UserService.class.getName());
	
	private BeanModelReader reader = Registry.get(BeanModelReader.class.getName());
	private BeanModelFactory factory = Registry.get(Store.USER_MODEL_FACTORY);
	
	/**
	 * 
	 */
	public UserController() {
		// TODO Auto-generated constructor stub
		registerEventTypes(UserEvents.REGISTER);
		registerEventTypes(UserEvents.SETTING);
		registerEventTypes(UserEvents.CHECK_EMAIL);
		registerEventTypes(UserEvents.LOGIN);
		registerEventTypes(UserEvents.LOGOUT);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class CreateProxy extends RpcProxy<User> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<User> callback) {
			// TODO Auto-generated method stub
			service.create((User) loadConfig, callback);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class UpdateProxy extends RpcProxy<User> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<User> callback) {
			// TODO Auto-generated method stub
			service.update((User) loadConfig, callback);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ShowProxy extends RpcProxy<Collection<User>> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Collection<User>> callback) {
			// TODO Auto-generated method stub
			service.show((String) loadConfig, callback);
		}
		
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			BeanModel model = event.getData();
			if (event.getType().equals(UserEvents.REGISTER)) {
				create(event, model);
			} else if (event.getType().equals(UserEvents.SETTING)) {
				update(event, model);
			} else if (event.getType().equals(UserEvents.CHECK_EMAIL)) {
				show(event, "email=='" + model.get("email") + "'");
			} else if (event.getType().equals(UserEvents.LOGIN)) {
				show(event, "email=='" + model.get("email") + "'&&password=='" + model.get("password") + "'");
			} else if (event.getType().equals(UserEvents.LOGOUT)) {
				forwardToView(view, event);
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param event
	 * @param model
	 * @throws Exception
	 */
	private void create(final AppEvent event, BeanModel model) throws Exception {
		Loader<User> loader = new BaseLoader<User>(new CreateProxy());
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				forwardToView(view, event.getType(), factory.createModel(loadEvent.getData()));
			}
			
		});
		loader.load(model.getBean());
	}
	
	/**
	 * 
	 * @param event
	 * @param model
	 * @throws Exception
	 */
	private void update(final AppEvent event, BeanModel model) throws Exception {
		Loader<User> loader = new BaseLoader<User>(new UpdateProxy());
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				forwardToView(view, event.getType(), factory.createModel(loadEvent.getData()));
			}
			
		});
		loader.load(model.getBean());
	}
	
	/**
	 * 
	 * @param event
	 * @param loadConfig
	 * @throws Exception
	 */
	private void show(final AppEvent event, Object loadConfig) throws Exception {
		ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(new ShowProxy(), reader);
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				BaseListLoadResult<BeanModel> result = loadEvent.getData();
				forwardToView(view, event.getType(), result.getData());
			}
			
		});
		loader.load(loadConfig);
	}
	
}
