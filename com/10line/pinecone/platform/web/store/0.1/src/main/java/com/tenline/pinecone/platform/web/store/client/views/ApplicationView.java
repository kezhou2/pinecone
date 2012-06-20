/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.tenline.pinecone.platform.model.Application;
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
	}

	@Override
	protected void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(ApplicationEvents.GET_BY_USER)) {
				loadApplications(event);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadApplications(AppEvent event) throws Exception{
		HomeViewport view = (HomeViewport)Registry.get(HomeViewport.class.getName());
		Collection<Application> userApps = (Collection<Application>)event.getData();
		view.loadApps(userApps);
	}

}
