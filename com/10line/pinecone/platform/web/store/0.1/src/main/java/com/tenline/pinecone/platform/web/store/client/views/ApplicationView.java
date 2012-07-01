/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.HomeViewport;

/**
 * @author Bill
 *
 */
public class ApplicationView extends View {

	/**
	 * @param controller
	 */
	public ApplicationView(Controller controller) {
		super(controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getType().equals(ApplicationEvents.GET_BY_OWNER)) {
				getByOwner(event);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void getByOwner(AppEvent event) throws Exception {
		HomeViewport viewport = Registry.get(HomeViewport.class.getName());
		List<BeanModel> applications = event.getData();
		for (BeanModel application : applications) {
			viewport.updateAppToList((BeanModel) application.get("consumer"));
		}
	}

}
