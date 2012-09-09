/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
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

	/**
	 * 
	 */
	public CategoryController() {
		registerEventTypes(CategoryEvents.GET_ALL);
	}

	@Override
	public void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(CategoryEvents.GET_ALL)) {
				getAll(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void getAll(final AppEvent event) throws Exception {
		service.show("all", new AsyncCallback<Collection<Category>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<Category> result) {
				forwardToView(view, event.getType(), result);
			}
			
		});
	}

}
