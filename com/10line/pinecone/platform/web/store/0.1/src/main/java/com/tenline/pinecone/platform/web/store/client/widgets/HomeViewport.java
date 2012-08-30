/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.Store;

/**
 * @author Bill
 *
 */
public class HomeViewport extends AbstractViewport {
	
	private Button identity = new Button();
	private ApplicationConsole applicationConsole = new ApplicationConsole();
	private ListStore<BeanModel> applicationListStore = new ListStore<BeanModel>();
	private ListStore<BeanModel> friendListStore = new ListStore<BeanModel>();

	/**
	 * 
	 */
	public HomeViewport() {
		super();
		// TODO Auto-generated constructor stub
		header.add(identity);
		navigator.getMenu().add(new ApplicationItem());
		navigator.getMenu().add(new FriendItem());
		navigator.getMenu().add(new LogoutItem());
		header.add(navigator);
		LayoutContainer centerContainer = new LayoutContainer(new FitLayout());
		centerContainer.add(applicationConsole, new FitData(20));
		body.add(centerContainer, new BorderLayoutData(LayoutRegion.CENTER));
		LayoutContainer eastContainer = new LayoutContainer(new FillLayout(Orientation.VERTICAL));
		eastContainer.add(new ApplicationPanel(), new FillData(20, 20, 20, 10));
		eastContainer.add(new FriendPanel(), new FillData(0, 20, 20, 10));
		body.add(eastContainer, new BorderLayoutData(LayoutRegion.EAST, 250));
	}
	
	/**
	 * 
	 * @param text
	 * @param title
	 */
	public void updateIdentity(String text, String title) {
		identity.setText(text);
		identity.setTitle(title);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ApplicationPanel extends ContentPanel {
		
		private ApplicationPanel() {
			setHeading(((Messages) Registry.get(Messages.class.getName())).myApplications());
			setLayout(new FitLayout());
			add(new ApplicationListView());
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ApplicationListView extends ListView<BeanModel> {
		
		private ApplicationListView() {
			setDisplayProperty("name");
			applicationListStore.setModelComparer(new SimpleModelComparer<BeanModel>());
			setStore(applicationListStore);
			getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {

				@Override
				public void selectionChanged(SelectionChangedEvent<BeanModel> event) {
					// TODO Auto-generated method stub
					updateApplicationToConsole(event.getSelectedItem());
				}
				
			});
		}
		
	}
	
	/**
	 * 
	 * @param application
	 */
	public void updateApplicationToList(BeanModel application) {
		if (!applicationListStore.contains(application)) {
			applicationListStore.add(application);
			applicationListStore.commitChanges();
		}
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class FriendPanel extends ContentPanel {
		
		private FriendPanel() {
			setHeading(((Messages) Registry.get(Messages.class.getName())).myFriends());
			setLayout(new FitLayout());
			add(new FriendListView());
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class FriendListView extends ListView<BeanModel> {
		
		private FriendListView() {
			setDisplayProperty("name");
			friendListStore.setModelComparer(new SimpleModelComparer<BeanModel>());
			setStore(friendListStore);
			getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {

				@Override
				public void selectionChanged(SelectionChangedEvent<BeanModel> event) {
					// TODO Auto-generated method stub
					Registry.unregister(Store.CURRENT_USER);
					Registry.register(Store.CURRENT_USER, event.getSelectedItem());
				}
				
			});
		}
		
	}
	
	/**
	 * 
	 * @param friend
	 */
	public void updateFriendToList(BeanModel friend) {
		if (!friendListStore.contains(friend)) {
			friendListStore.add(friend);
			friendListStore.commitChanges();
		}
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ApplicationConsole extends ContentPanel {
		
		private ApplicationConsole() {
			setHeading(((Messages) Registry.get(Messages.class.getName())).applicationConsole());
		}
		
	}
	
	/**
	 * 
	 * @param application
	 */
	public void updateApplicationToConsole(BeanModel application) {
		applicationConsole.setUrl(application.get("connectURI").toString());
	}

}
