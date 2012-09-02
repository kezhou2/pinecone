/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
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
import com.tenline.pinecone.platform.web.store.client.controllers.ModelController;

/**
 * @author Bill
 *
 */
public class FriendViewport extends NavigatorViewport {

	public static String OWNER_INVITE_FRIEND = "owner.invite.friend";
	public static String OWNER_GET_ALL_OTHER_USERS = "owner.get.all.other.users";
	
	private ListStore<BeanModel> userGridStore = new ListStore<BeanModel>();
	
	/**
	 * 
	 */
	public FriendViewport() {
		super();
		// TODO Auto-generated constructor stub
		header.add(new HomeButton());
		header.add(new ApplicationButton());
		header.add(accountButton);
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
							inviteFriendDialog(model);
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
	 * @param receiver
	 */
	private void inviteFriendDialog(final BeanModel receiver) {
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
					BeanModel model = BeanModelLookup.get().getFactory(Friend.class).createModel(new Friend());
					model.set("sender", Registry.get(Store.CURRENT_OWNER)); model.set("receiver", receiver);
					ModelController.create(OWNER_INVITE_FRIEND, model, FriendViewport.this);
				}
			}
			
		});
		msgBox.show();
	}

	@Override
	public void handleViewCallback(AppEvent event) {
		// TODO Auto-generated method stub
		if (event.getData("type").equals(OWNER_GET_ALL_OTHER_USERS)) {
			List<BeanModel> users = event.getData("model");
			for (BeanModel user : users) {
				updateUserToGrid(user);	
			}
		}
	}

}
