/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.Store;
import com.tenline.pinecone.platform.web.store.client.events.ModelEvents;

/**
 * @author Bill
 *
 */
public class FriendViewport extends AbstractViewport {

	private ListStore<BeanModel> userGridStore = new ListStore<BeanModel>();
	
	/**
	 * 
	 */
	public FriendViewport() {
		super();
		// TODO Auto-generated constructor stub
		navigator.getMenu().add(new ApplicationItem());
		navigator.getMenu().add(new HomeItem());
		navigator.getMenu().add(new LogoutItem());
		header.add(navigator);
		LayoutContainer centerContainer = new LayoutContainer(new FitLayout());
		centerContainer.add(new UserPanel(), new FitData(20));
		body.add(centerContainer, new BorderLayoutData(LayoutRegion.CENTER));
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class UserPanel extends ContentPanel {
		
		private UserPanel() {
			setHeading(((Messages) Registry.get(Messages.class.getName())).user());
			setLayout(new FitLayout());
			ArrayList<ColumnConfig> config = new ArrayList<ColumnConfig>();
			config.add(new ColumnConfig("name", ((Messages) Registry.get(Messages.class.getName())).name(), 100));
			config.add(new ColumnConfig("email", ((Messages) Registry.get(Messages.class.getName())).account(), 100));
			config.add(new ColumnConfig("addToFriend", 100));
			config.get(2).setRenderer(new GridCellRenderer<BeanModel>() {

				@Override
				public Object render(final BeanModel model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<BeanModel> store, Grid<BeanModel> grid) {
					// TODO Auto-generated method stub
					return new Button(((Messages) Registry.get(Messages.class.getName())).addToFriend(), new SelectionListener<ButtonEvent>() {

						@Override
						public void componentSelected(ButtonEvent event) {
							// TODO Auto-generated method stub
							BeanModel friend = BeanModelLookup.get().getFactory(Friend.class).createModel(new Friend());
							friend.set("sender", Registry.get(Store.CURRENT_USER));
							friend.set("receiver", model.getBean());
							addFriendDialog(friend);
						}
						
					});
				}
				
			});
			add(new UserGrid(new ColumnModel(config)));
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class UserGrid extends Grid<BeanModel> {
		
		private UserGrid(ColumnModel model) {
			super(userGridStore, model);
			userGridStore.setModelComparer(new SimpleModelComparer<BeanModel>());
			getView().setAutoFill(true);
		}
		
	}
	
	/**
	 * 
	 * @param user
	 */
	public void updateUserToGrid(BeanModel user) {
		if (!userGridStore.contains(user)) {
			userGridStore.add(user);
			userGridStore.commitChanges();
		}
	}
	
	/**
	 * 
	 * @param model
	 */
	private void addFriendDialog(final BeanModel model) {
		MessageBox msgBox = new MessageBox();
		msgBox.setMessage(((Messages) Registry.get(Messages.class.getName())).addToFriendMessage());
		msgBox.setTitle(((Messages) Registry.get(Messages.class.getName())).addToFriendTitle());
		msgBox.setButtons(MessageBox.OKCANCEL);
		msgBox.setIcon(MessageBox.QUESTION);
		msgBox.addCallback(new Listener<MessageBoxEvent>() {

			@Override
			public void handleEvent(MessageBoxEvent msgEvent) {
				// TODO Auto-generated method stub
				if (msgEvent.getButtonClicked().getText().equals(GXT.MESSAGES.messageBox_ok())) {
					Dispatcher.get().dispatch(ModelEvents.INVITE_FRIEND, model);
				}
			}
			
		});
		msgBox.show();
	}

}
