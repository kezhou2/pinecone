/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.CheckBoxListView;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.Store;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
import com.tenline.pinecone.platform.web.store.client.events.FriendEvents;

/**
 * @author Bill
 *
 */
public class HomeViewport extends AbstractViewport {
	
	private ListStore<BeanModel> appListStore = new ListStore<BeanModel>();
	private ListStore<BeanModel> friendListStore = new ListStore<BeanModel>();

	/**
	 * 
	 */
	public HomeViewport() {
		super();
		// TODO Auto-generated constructor stub
		LayoutContainer centerContainer = new LayoutContainer(new FitLayout());
		centerContainer.add(new AppConsole(), new FitData(20));
		body.add(centerContainer, new BorderLayoutData(LayoutRegion.CENTER));
		LayoutContainer eastContainer = new LayoutContainer(new FillLayout(Orientation.VERTICAL));
		eastContainer.add(new AppPanel(), new FillData(20, 20, 20, 10));
		eastContainer.add(new FriendPanel(), new FillData(0, 20, 20, 10));
		body.add(eastContainer, new BorderLayoutData(LayoutRegion.EAST, 250));
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class AppPanel extends ContentPanel {
		
		private AppPanel() {
			setHeading(((Messages) Registry.get(Messages.class.getName())).myApplications());
			setLayout(new FitLayout());
			add(new AppListView());
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class AppListView extends CheckBoxListView<BeanModel> {
		
		private AppListView() {
			setDisplayProperty("name");
			setStore(appListStore);
			Dispatcher.get().dispatch(ApplicationEvents.GET_BY_OWNER, Registry.get(Store.CURRENT_USER));
		}
		
	}
	
	/**
	 * 
	 * @param application
	 */
	public void updateAppToList(BeanModel application) {
		if (!appListStore.contains(application)) {
			appListStore.add(application);
			appListStore.commitChanges();
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
			setDisplayProperty("id");
			setStore(friendListStore);
			Dispatcher.get().dispatch(FriendEvents.GET_BY_SENDER, Registry.get(Store.CURRENT_USER));
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
	private class AppConsole extends ContentPanel {
		
		private AppConsole() {
			setHeaderVisible(false);
			setLayout(new FitLayout());
			setTopComponent(new AppToolBar());
			add(new AppPortal());
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class AppToolBar extends ToolBar {
		
		private AppToolBar() {
			add(new FillToolItem());
			add(new Button(((Messages) Registry.get(Messages.class.getName())).application()));
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class AppPortal extends Portal {
		
		private static final int COLUMNS = 2;
		
		private AppPortal() {
			super(COLUMNS);
			for (int i=0; i<COLUMNS; i++) {
				setColumnWidth(i, 1 / COLUMNS);	
			}
		}
		
	}

}
