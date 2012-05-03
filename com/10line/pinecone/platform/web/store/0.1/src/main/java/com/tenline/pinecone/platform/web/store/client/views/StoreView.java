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
	
	private MainViewport viewport;

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
			initView();
		} else if (event.getType().equals(StoreEvents.LOGIN_USER)) {
			loginUser(event);
		}
	}
	
	/**
	 * 
	 */
	private void initView() {
		viewport = new MainViewport();
		RootPanel.get().add(viewport);
	}
	
	/**
	 * 
	 * @param event
	 */
	private void loginUser(AppEvent event) {
		if (event.getData() != null) {
			
		}
	}

}
