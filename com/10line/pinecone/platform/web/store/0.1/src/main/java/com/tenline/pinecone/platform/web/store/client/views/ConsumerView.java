/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.tenline.pinecone.platform.web.store.client.events.ConsumerEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

/**
 * @author Bill
 *
 */
public class ConsumerView extends View {

	/**
	 * @param controller
	 */
	public ConsumerView(Controller controller) {
		super(controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getType().equals(ConsumerEvents.REGISTER)) {
				register();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void register() throws Exception {
		Dispatcher.get().dispatch(WidgetEvents.UPDATE_LOGIN_TO_PANEL);
	}

}
