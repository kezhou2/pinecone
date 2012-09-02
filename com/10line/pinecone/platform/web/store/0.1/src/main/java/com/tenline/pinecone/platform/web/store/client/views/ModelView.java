/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.tenline.pinecone.platform.web.store.client.widgets.AbstractViewport;

/**
 * @author Bill
 *
 */
public class ModelView extends View {

	/**
	 * @param controller
	 */
	public ModelView(Controller controller) {
		super(controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		AbstractViewport view = event.getData("view");
		if (view != null) view.handleViewCallback(event);
	}

}
