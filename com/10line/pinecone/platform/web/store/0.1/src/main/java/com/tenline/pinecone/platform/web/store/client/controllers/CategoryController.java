/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.web.store.client.events.CategoryEvents;
import com.tenline.pinecone.platform.web.store.client.services.CategoryService;
import com.tenline.pinecone.platform.web.store.client.services.CategoryServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.CategoryView;

/**
 * @author Bill
 *
 */
public class CategoryController extends Controller {
	
	private CategoryView view = new CategoryView(this);
	private CategoryServiceAsync service = Registry.get(CategoryService.class.getName());
	
	private BeanModelReader reader = Registry.get(BeanModelReader.class.getName());
	
	/**
	 * 
	 */
	public CategoryController() {
		// TODO Auto-generated constructor stub
		registerEventTypes(CategoryEvents.GET_ALL);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ShowProxy extends RpcProxy<Collection<Category>> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Collection<Category>> callback) {
			// TODO Auto-generated method stub
			service.show((String) loadConfig, callback);
		}
		
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getType().equals(CategoryEvents.GET_ALL)) {
				show(event, "all");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
