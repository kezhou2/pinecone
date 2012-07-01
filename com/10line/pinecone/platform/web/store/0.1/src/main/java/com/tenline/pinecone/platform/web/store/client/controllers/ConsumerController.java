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
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.web.store.client.Store;
import com.tenline.pinecone.platform.web.store.client.events.ConsumerEvents;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.ConsumerView;

/**
 * @author Bill
 *
 */
public class ConsumerController extends Controller {

	private ConsumerView view = new ConsumerView(this);
	private ConsumerServiceAsync service = Registry.get(ConsumerService.class.getName());
	
	private BeanModelReader reader = Registry.get(BeanModelReader.class.getName());
	private BeanModelFactory factory = Registry.get(Store.CONSUMER_MODEL_FACTORY);
	
	/**
	 * 
	 */
	public ConsumerController() {
		// TODO Auto-generated constructor stub
		registerEventTypes(ConsumerEvents.REGISTER);
		registerEventTypes(ConsumerEvents.UNREGISTER);
		registerEventTypes(ConsumerEvents.SETTING);
		registerEventTypes(ConsumerEvents.GET_BY_CATEGORY);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class CreateProxy extends RpcProxy<Consumer> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Consumer> callback) {
			// TODO Auto-generated method stub
			service.create((Consumer) loadConfig, callback);
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
			service.delete((String) loadConfig, callback);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class UpdateProxy extends RpcProxy<Consumer> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Consumer> callback) {
			// TODO Auto-generated method stub
			service.update((Consumer) loadConfig, callback);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ShowByCategoryProxy extends RpcProxy<Collection<Consumer>> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Collection<Consumer>> callback) {
			// TODO Auto-generated method stub
			service.showByCategory((String) loadConfig, callback);
		}
		
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			BeanModel model = event.getData();
			if (event.getType().equals(ConsumerEvents.REGISTER)) {
				create(event, model);
			} else if (event.getType().equals(ConsumerEvents.UNREGISTER)) {
				delete(event, model);
			} else if (event.getType().equals(ConsumerEvents.SETTING)) {
				update(event, model);
			} else if (event.getType().equals(ConsumerEvents.GET_BY_CATEGORY)) {
				showByCategory(event, model);
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
		Loader<Consumer> loader = new BaseLoader<Consumer>(new CreateProxy());
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
	private void delete(final AppEvent event, BeanModel model) throws Exception {
		Loader<Boolean> loader = new BaseLoader<Boolean>(new DeleteProxy());
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				forwardToView(view, event.getType(), loadEvent.getData());
			}
			
		});
		loader.load("id=='" + model.get("id") + "'");
	}
	
	/**
	 * 
	 * @param event
	 * @param model
	 * @throws Exception
	 */
	private void update(final AppEvent event, BeanModel model) throws Exception {
		Loader<Consumer> loader = new BaseLoader<Consumer>(new UpdateProxy());
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
	private void showByCategory(final AppEvent event, BeanModel model) throws Exception {
		ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(new ShowByCategoryProxy(), reader);
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				BaseListLoadResult<BeanModel> result = loadEvent.getData();
				forwardToView(view, event.getType(), result.getData());
			}
			
		});
		loader.load("id=='" + model.get("id") + "'");
	}
	
}
