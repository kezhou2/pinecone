/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.web.store.client.events.CategoryEvents;
import com.tenline.pinecone.platform.web.store.client.events.FriendEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.ConsumerRegistryViewport;

/**
 * @author Bill
 *
 */
public class CategoryView extends View {

	/**
	 * @param controller
	 */
	public CategoryView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(CategoryEvents.GET_ALL)) {
				onGetAll(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetAll(AppEvent event){
		ConsumerRegistryViewport crv = (ConsumerRegistryViewport)Registry.get(ConsumerRegistryViewport.class.getName());
		Collection<Category> categories = (Collection<Category>)event.getData();
		crv.loadCategories(categories);
	}
}
