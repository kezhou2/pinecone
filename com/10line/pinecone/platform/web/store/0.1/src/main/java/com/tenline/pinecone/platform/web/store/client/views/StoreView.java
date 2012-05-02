/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.web.store.client.StoreEvents;

/**
 * @author Bill
 *
 */
public class StoreView extends View {

	/**
	 * @param controller
	 */
	public StoreView(Controller controller) {
		super(controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		if (event.getType().equals(StoreEvents.INIT_VIEW)) {
			RootPanel.get().add(new MainViewport());
		}
	}

}
