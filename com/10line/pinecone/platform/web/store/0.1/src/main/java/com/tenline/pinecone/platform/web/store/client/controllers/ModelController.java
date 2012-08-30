/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BaseLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
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
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.web.store.client.events.ModelEvents;
import com.tenline.pinecone.platform.web.store.client.services.ModelService;
import com.tenline.pinecone.platform.web.store.client.services.ModelServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.ModelView;

/**
 * @author Bill
 *
 */
public class ModelController extends Controller {

	private ModelView view = new ModelView(this);
	private ModelServiceAsync service = Registry.get(ModelService.class.getName());
	private BeanModelReader reader = new BeanModelReader();
	
	/**
	 * 
	 */
	public ModelController() {
		// TODO Auto-generated constructor stub
		registerEventTypes(ModelEvents.REGISTER_USER);
		registerEventTypes(ModelEvents.REGISTER_CONSUMER);
		registerEventTypes(ModelEvents.INSTALL_APPLICATION);
		registerEventTypes(ModelEvents.INVITE_FRIEND);
		registerEventTypes(ModelEvents.LOGIN_USER);
		registerEventTypes(ModelEvents.LOGOUT_USER);
		registerEventTypes(ModelEvents.GET_ALL_USER);
		registerEventTypes(ModelEvents.GET_ALL_CONSUMER);
		registerEventTypes(ModelEvents.GET_APPLICATION_BY_USER);
		registerEventTypes(ModelEvents.GET_FRIEND_BY_RECEIVER);
		registerEventTypes(ModelEvents.GET_FRIEND_BY_SENDER);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class CreateProxy extends RpcProxy<Entity> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Entity> callback) {
			// TODO Auto-generated method stub
			service.create((Entity) loadConfig, callback);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
//	private class DeleteProxy extends RpcProxy<Boolean> {
//
//		@Override
//		protected void load(Object loadConfig, AsyncCallback<Boolean> callback) {
//			// TODO Auto-generated method stub
//			service.delete((Entity) loadConfig, callback);
//		}
//		
//	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
//	private class UpdateProxy extends RpcProxy<Entity> {
//
//		@Override
//		protected void load(Object loadConfig, AsyncCallback<Entity> callback) {
//			// TODO Auto-generated method stub
//			service.update((Entity) loadConfig, callback);
//		}
//		
//	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ShowProxy extends RpcProxy<Collection<Entity>> {

		@Override
		@SuppressWarnings("unchecked")
		protected void load(Object loadConfig, AsyncCallback<Collection<Entity>> callback) {
			// TODO Auto-generated method stub
			List<String> model = (List<String>) loadConfig;
			service.show(model.get(0), model.get(1), callback);
		}
		
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getType().equals(ModelEvents.REGISTER_USER) || 
				event.getType().equals(ModelEvents.REGISTER_CONSUMER) ||
				event.getType().equals(ModelEvents.INSTALL_APPLICATION) ||
				event.getType().equals(ModelEvents.INVITE_FRIEND)) {
				create(event, (BeanModel) event.getData());
			} else if (event.getType().equals(ModelEvents.LOGIN_USER) ||
					   event.getType().equals(ModelEvents.GET_ALL_USER) ||
					   event.getType().equals(ModelEvents.GET_ALL_CONSUMER) ||
					   event.getType().equals(ModelEvents.GET_APPLICATION_BY_USER) ||
					   event.getType().equals(ModelEvents.GET_FRIEND_BY_RECEIVER) ||
					   event.getType().equals(ModelEvents.GET_FRIEND_BY_SENDER)) {
				show(event, event.getData());
			} else if (event.getType().equals(ModelEvents.LOGOUT_USER)) {
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
		Loader<Entity> loader = new BaseLoader<Entity>(new CreateProxy());
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				Object data = loadEvent.getData();
				forwardToView(view, event.getType(), BeanModelLookup.get().getFactory(data.getClass()).createModel(data));
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
//	private void delete(final AppEvent event, BeanModel model) throws Exception {
//		Loader<Boolean> loader = new BaseLoader<Boolean>(new DeleteProxy());
//		loader.addLoadListener(new LoadListener() {
//			
//			@Override
//			public void loaderLoad(LoadEvent loadEvent) {
//				forwardToView(view, event.getType(), loadEvent.getData());
//			}
//			
//		});
//		loader.load(model.getBean());
//	}
	
	/**
	 * 
	 * @param event
	 * @param model
	 * @throws Exception
	 */
//	private void update(final AppEvent event, BeanModel model) throws Exception {
//		Loader<Entity> loader = new BaseLoader<Entity>(new UpdateProxy());
//		loader.addLoadListener(new LoadListener() {
//			
//			@Override
//			public void loaderLoad(LoadEvent loadEvent) {
//				Object data = loadEvent.getData();
//				forwardToView(view, event.getType(), BeanModelLookup.get().getFactory(data.getClass()).createModel(data));
//			}
//			
//		});
//		loader.load(model.getBean());
//	}
	
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
