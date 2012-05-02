/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.tenline.pinecone.platform.web.store.client.views.StoreView;

/**
 * @author Bill
 *
 */
public class StoreController extends Controller {
	
	private StoreView view;

	/**
	 * 
	 */
	public StoreController() {
		// TODO Auto-generated constructor stub
		registerEventTypes(StoreEvents.INIT_VIEW);
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		if (event.getType().equals(StoreEvents.INIT_VIEW)) {
			view = new StoreView(this);
			forwardToView(view, event);
		}
	}

}
