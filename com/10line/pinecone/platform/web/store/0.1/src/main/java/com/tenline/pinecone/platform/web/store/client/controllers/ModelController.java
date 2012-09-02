/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.ArrayList;
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
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.web.store.client.events.ModelEvents;
import com.tenline.pinecone.platform.web.store.client.services.ModelService;
import com.tenline.pinecone.platform.web.store.client.services.ModelServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.ModelView;
import com.tenline.pinecone.platform.web.store.client.widgets.AbstractViewport;

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
		registerEventTypes(ModelEvents.CREATE);
		registerEventTypes(ModelEvents.DELETE);
		registerEventTypes(ModelEvents.UPDATE);
		registerEventTypes(ModelEvents.SHOW);
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
	private class DeleteProxy extends RpcProxy<Boolean> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Boolean> callback) {
			// TODO Auto-generated method stub
			service.delete((Entity) loadConfig, callback);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class UpdateProxy extends RpcProxy<Entity> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Entity> callback) {
			// TODO Auto-generated method stub
			service.update((Entity) loadConfig, callback);
		}
		
	}
	
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
			if (event.getType().equals(ModelEvents.CREATE)) {
				create(event, (BeanModel) event.getData("model"));
			} else if (event.getType().equals(ModelEvents.DELETE)) {
				delete(event, (BeanModel) event.getData("model"));
			} else if (event.getType().equals(ModelEvents.UPDATE)) {
				update(event, (BeanModel) event.getData("model"));
			} else if (event.getType().equals(ModelEvents.SHOW)) {
				show(event, event.getData("model"));
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
				event.setData("model", BeanModelLookup.get().getFactory(data.getClass()).createModel(data));
				forwardToView(view, event);
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
	private void delete(final AppEvent event, BeanModel model) throws Exception {
		Loader<Boolean> loader = new BaseLoader<Boolean>(new DeleteProxy());
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				event.setData("model", loadEvent.getData());
				forwardToView(view, event);
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
		Loader<Entity> loader = new BaseLoader<Entity>(new UpdateProxy());
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				Object data = loadEvent.getData();
				event.setData("model", BeanModelLookup.get().getFactory(data.getClass()).createModel(data));
				forwardToView(view, event);
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
				event.setData("model", result.getData());
				forwardToView(view, event);
			}
			
		});
		loader.load(loadConfig);
	}
	
	/**
	 * 
	 * @param type
	 * @param model
	 * @param view
	 */
	public static void create(String type, BeanModel model, AbstractViewport view) {
		AppEvent event = new AppEvent(ModelEvents.CREATE);
		event.setData("type", type);
		event.setData("model", model);
		event.setData("view", view);
		Dispatcher.get().dispatch(event);
	}
	
	/**
	 * 
	 * @param type
	 * @param model
	 * @param view
	 */
	public static void show(String type, ArrayList<String> model, AbstractViewport view) {
		AppEvent event = new AppEvent(ModelEvents.SHOW);
		event.setData("type", type);
		event.setData("model", model);
		event.setData("view", view);
		Dispatcher.get().dispatch(event);
	}
	
}
