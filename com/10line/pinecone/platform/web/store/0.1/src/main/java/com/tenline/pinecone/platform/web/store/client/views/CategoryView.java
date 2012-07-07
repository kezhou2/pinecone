/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.web.store.client.events.CategoryEvents;
import com.tenline.pinecone.platform.web.store.client.events.FriendEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.AppStoreViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.ConsumerRegistryViewport;

/**
 * @author Bill
 *
 */
public class CategoryView extends View {

	private BeanModelFactory categoryFactory = BeanModelLookup.get().getFactory(Category.class);
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
		System.out.println("CategoryView onGetAll");
		
		Collection<Category> cols = (Collection<Category>)event.getData();
		List<BeanModel> categories = new ArrayList<BeanModel>();
		for(Category c:cols){
			BeanModel bm = categoryFactory.createModel(c);
			bm.set("category", c);
			categories.add(bm);
		}
		
		ConsumerRegistryViewport crv = (ConsumerRegistryViewport)Registry.get(ConsumerRegistryViewport.class.getName());
		crv.loadCategories(categories);
		
		List<BaseModelData> categories_1 = new ArrayList<BaseModelData>();
		BaseModelData bmd = new BaseModelData();
		bmd.set("domain", "all");
		categories_1.add(bmd);
		categories_1.addAll(categories);
		AppStoreViewport asv = (AppStoreViewport)Registry.get(AppStoreViewport.class.getName());
		asv.setCategories(categories_1);
	}
}
