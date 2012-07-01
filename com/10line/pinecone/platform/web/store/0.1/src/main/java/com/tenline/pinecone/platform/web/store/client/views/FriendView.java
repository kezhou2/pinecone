/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.tenline.pinecone.platform.web.store.client.Store;
import com.tenline.pinecone.platform.web.store.client.events.FriendEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.HomeViewport;

/**
 * @author Bill
 *
 */
public class FriendView extends View {

	/**
	 * @param controller
	 */
	public FriendView(Controller controller) {
		super(controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getType().equals(FriendEvents.GET_BY_SENDER)) {
				updateFriendToList(event, "receiver");
				Dispatcher.get().dispatch(FriendEvents.GET_BY_RECEIVER, Registry.get(Store.CURRENT_USER));
			} else if (event.getType().equals(FriendEvents.GET_BY_RECEIVER)) {
				updateFriendToList(event, "sender");
			} else if (event.getType().equals(FriendEvents.GET_INVITATIONS)) {
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param event
	 * @param property
	 * @throws Exception
	 */
	private void updateFriendToList(AppEvent event, String property) throws Exception {
		HomeViewport viewport = Registry.get(HomeViewport.class.getName());
		List<BeanModel> friends = event.getData();
		for (BeanModel friend : friends) {
			if (friend.get("decided").equals(true)) {
				viewport.updateFriendToList((BeanModel) friend.get(property));
			}
		}
	}

}
