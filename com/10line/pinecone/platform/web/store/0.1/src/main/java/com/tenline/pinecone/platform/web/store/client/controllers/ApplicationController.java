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
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.web.store.client.Store;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
import com.tenline.pinecone.platform.web.store.client.services.ApplicationService;
import com.tenline.pinecone.platform.web.store.client.services.ApplicationServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.ApplicationView;

/**
 * @author Bill
 *
 */
public class ApplicationController extends Controller {

	private ApplicationView view = new ApplicationView(this);
	private ApplicationServiceAsync service = Registry.get(ApplicationService.class.getName());
	
	private BeanModelReader reader = Registry.get(BeanModelReader.class.getName());
	private BeanModelFactory factory = Registry.get(Store.APPLICATION_MODEL_FACTORY);
	
	/**
	 * 
	 */
	public ApplicationController() {
		// TODO Auto-generated constructor stub
		registerEventTypes(ApplicationEvents.INSTALL);
		registerEventTypes(ApplicationEvents.UNINSTALL);
		registerEventTypes(ApplicationEvents.SETTING);
		registerEventTypes(ApplicationEvents.GET_BY_OWNER);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class CreateProxy extends RpcProxy<Application> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Application> callback) {
			// TODO Auto-generated method stub
			service.create((Application) loadConfig, callback);
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
	private class UpdateProxy extends RpcProxy<Application> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Application> callback) {
			// TODO Auto-generated method stub
			service.update((Application) loadConfig, callback);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ShowByUserProxy extends RpcProxy<Collection<Application>> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Collection<Application>> callback) {
			// TODO Auto-generated method stub
			service.showByUser((String) loadConfig, callback);
		}
		
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			BeanModel model = event.getData();
			if (event.getType().equals(ApplicationEvents.INSTALL)) {
				create(event, model);
			} else if (event.getType().equals(ApplicationEvents.UNINSTALL)) {
				delete(event, model);
			} else if (event.getType().equals(ApplicationEvents.SETTING)) {
				update(event, model);
			} else if (event.getType().equals(ApplicationEvents.GET_BY_OWNER)) {
				showByUser(event, model);
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
		Loader<Application> loader = new BaseLoader<Application>(new CreateProxy());
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
		Loader<Application> loader = new BaseLoader<Application>(new UpdateProxy());
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
	private void showByUser(final AppEvent event, BeanModel model) throws Exception {
		ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(new ShowByUserProxy(), reader);
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
