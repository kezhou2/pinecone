package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
import com.tenline.pinecone.platform.web.store.client.events.FriendEvents;
import com.tenline.pinecone.platform.web.store.client.events.MailEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class HomeViewport extends AbstractViewport{
	
	private MainPanel mainPanel;
	private UserToolBar userToolBar;
	private UserHeader userHeader;
	
	public HomeViewport() {
		super();
		body.setHeaderVisible(false);
		userToolBar = new UserToolBar(); 
		userHeader = new UserHeader();
		userToolBar.add(userHeader);
		body.setTopComponent(userToolBar);

		mainPanel = new MainPanel();
		body.add(mainPanel, new BorderLayoutData(LayoutRegion.CENTER));
		
		
		/**
		 * load information
		 */
		//load user's applications
		AppEvent appEvent = new AppEvent(ApplicationEvents.GET_BY_USER);
		Dispatcher.get().dispatch(appEvent);
		//load user's friends
		AppEvent friEvent = new AppEvent(FriendEvents.GET_BY_USER);
		Dispatcher.get().dispatch(friEvent);
		//load user's messages
		AppEvent msgEvent = new AppEvent(MailEvents.GET_UNREAD);
		Dispatcher.get().dispatch(msgEvent);
	}
	
	public void loadUserInfo(){
		User user = (User)Registry.get(User.class.getName());
		userHeader.loadUserInfo(user);
	}
	
	public void loadApps(Collection<Application> userApps){
		mainPanel.loadApps(userApps);
	}
	
	public void loadFriends(Collection<Friend> userFriends){
		mainPanel.loadFriends(userFriends);
	}
	
	private class UserToolBar extends ToolBar{
		
		public UserToolBar(){
			LayoutContainer logoLayoutContainer = new LayoutContainer();
			logoLayoutContainer.setLayout(new FitLayout());
			
			Image image = ((Images) Registry.get(Images.class.getName())).logo().createImage();
			logoLayoutContainer.add(image);
			add(logoLayoutContainer);
			logoLayoutContainer.setSize("184", "60");
			
			FillToolItem fillToolItem = new FillToolItem();
			add(fillToolItem);
		}
	}
	
	private class MainPanel extends ContentPanel{
		/**application count*/
		private int count;
		/**portal use to show application*/
		private Portal portal;
		/**portal column num*/
		private int portalColumn = 2;
		/**application user installed*/
		private Collection<Application> userApps;
		private Map<String,Consumer> appMap = new LinkedHashMap<String,Consumer>();
		/**use to generate Application BeanModel*/
		private BeanModelFactory applicationFactory = BeanModelLookup.get().getFactory(Application.class);
		/**use to generate friend BeanModel*/
		private BeanModelFactory friendFactory = BeanModelLookup.get().getFactory(Friend.class);
		
		private ListStore<BeanModel> appStore;
		private ListStore<BeanModel> friendStore;
		
		
		public MainPanel(){
			setHeaderVisible(false);
			appStore = new ListStore<BeanModel>();
			friendStore = new ListStore<BeanModel>();
			setLayout(new BorderLayout());
			
			LayoutContainer layoutContainer_1 = new LayoutContainer();
			layoutContainer_1.setLayout(new BorderLayout());
			
			LayoutContainer layoutContainer_2 = new LayoutContainer();
			layoutContainer_2.setLayout(new FillLayout(Orientation.VERTICAL));
			
			//application content panel
			ContentPanel appContentpanel = new ContentPanel();
			appContentpanel.setHeading("My applications");
			layoutContainer_2.add(appContentpanel);
			appContentpanel.setLayout(new FitLayout());
			List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
			
			ColumnConfig clmncnfgInfo = new ColumnConfig("id", "Info", 180);
			configs.add(clmncnfgInfo);
			GridCellRenderer<BeanModel> infoRender = new GridCellRenderer<BeanModel>() {

				@Override
				public Object render(BeanModel model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<BeanModel> store, Grid<BeanModel> grid) {
					ApplicationInfoPanel panel = new ApplicationInfoPanel(model);
					return panel;
				}
			};
			clmncnfgInfo.setRenderer(infoRender);
			
			Grid<BeanModel> grid = new Grid<BeanModel>(appStore, new ColumnModel(configs));
			grid.setAutoHeight(false);
			grid.setAutoWidth(false);
			grid.getView().setAutoFill(true);
			grid.setHideHeaders(true);
			appContentpanel.add(grid, new FitData(0, 0, 0, 0));
			grid.setBorders(true);
			
			//friend content panel
			ContentPanel friendContentpanel = new ContentPanel();
			friendContentpanel.setHeading("My friends");
			layoutContainer_2.add(friendContentpanel);
			friendContentpanel.setLayout(new FitLayout());
			List<ColumnConfig> configs_1 = new ArrayList<ColumnConfig>();
			
			ColumnConfig clmncnfgInfo_1 = new ColumnConfig("id", "Info", 180);
			configs_1.add(clmncnfgInfo_1);
			GridCellRenderer<BeanModel> infoRender_1 = new GridCellRenderer<BeanModel>() {

				@Override
				public Object render(BeanModel model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<BeanModel> store, Grid<BeanModel> grid) {
					FriendInfoPanel panel = new FriendInfoPanel(model);
					return panel;
				}
			};
			clmncnfgInfo_1.setRenderer(infoRender_1);
			
			ToolButton devMenuBtn = new ToolButton("x-tool-gear");
			friendContentpanel.getHeader().addTool(devMenuBtn);
			devMenuBtn.addSelectionListener(new SelectionListener<IconButtonEvent>() {
				@Override
				public void componentSelected(IconButtonEvent ce) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_FRIENDS_MANAGE_TO_PANEL);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			devMenuBtn.setToolTip("Configuration");
			
			
			Grid<BeanModel> grid_1 = new Grid<BeanModel>(friendStore, new ColumnModel(configs_1));
			grid_1.setAutoHeight(false);
			grid_1.setAutoWidth(false);
			grid_1.getView().setAutoFill(true);
			friendContentpanel.add(grid_1, new FitData(0, 0, 0, 0));
			grid_1.setBorders(true);
			grid_1.setHideHeaders(true);
			
			layoutContainer_1.add(layoutContainer_2, new BorderLayoutData(LayoutRegion.EAST, 250.0f));
			layoutContainer_2.setBorders(true);
			
			LayoutContainer layoutContainer_3 = new LayoutContainer();
			layoutContainer_3.setLayout(new FitLayout());
			
			ContentPanel cntntpnlNewContentpanel = new ContentPanel();
			cntntpnlNewContentpanel.setHeading("Dashboard");
			cntntpnlNewContentpanel.setLayout(new FitLayout());
			cntntpnlNewContentpanel.setHeaderVisible(false);
			
			portal = new Portal(portalColumn);
			cntntpnlNewContentpanel.add(portal);
			portal.setColumnWidth(0, 0.5);
			portal.setColumnWidth(1, 0.5);
			
			ToolBar toolBar = new ToolBar();
			
			Text txtDashboard = new Text("Dashboard");
			toolBar.add(txtDashboard);
			
			FillToolItem fillToolItem = new FillToolItem();
			toolBar.add(fillToolItem);
			
			Button btnGotoStore = new Button("Go to Store");
			toolBar.add(btnGotoStore);
			cntntpnlNewContentpanel.setTopComponent(toolBar);
			
			layoutContainer_3.add(cntntpnlNewContentpanel);
			layoutContainer_1.add(layoutContainer_3, new BorderLayoutData(LayoutRegion.CENTER));
			layoutContainer_3.setBorders(true);
			add(layoutContainer_1, new BorderLayoutData(LayoutRegion.CENTER));
			layoutContainer_1.setBorders(true);
		}
	
		public void loadApps(Collection<Application> userApps){
			int iCount = 0;
			for(Application app:userApps){
				
				if(app.getStatus().equals(Application.OPENED)){
					ConsumerPortlet p = new ConsumerPortlet(app);
					p.setHeading(app.getConsumer().getName());
					p.setUrl(app.getConsumer().getConnectURI());
					portal.add(p, iCount%portalColumn);
					iCount++;
				}
			}
			
			appStore.removeAll();
			for(Application app:userApps){
				BeanModel bm = applicationFactory.createModel(app);
				bm.set("consumer", app.getConsumer());
				appStore.add(bm);
			}
			appStore.commitChanges();
		}
		
		public void loadFriends(Collection<Friend> userFriends){
			
			friendStore.removeAll();
			for(Friend f:userFriends){
				
				BeanModel bm = friendFactory.createModel(f);
				if(f.getReceiver() == null){
					bm.set("friend", f.getSender());
				}else{
					bm.set("friend", f.getReceiver());
				}
				
				friendStore.add(bm);
			}
			friendStore.commitChanges();
		}

	}
	
	private class UserHeader extends ContentPanel {
		/**user name*/
		private Text nameTxt;
		
		public UserHeader() {
			setHeaderVisible(false);
			setSize("200", "60");
			setLayout(new BorderLayout());
			
			LayoutContainer portraitLayoutContainer = new LayoutContainer();
			portraitLayoutContainer.setLayout(new FitLayout());
			
			Image portraitImage = new Image((String) null);
			portraitLayoutContainer.add(portraitImage);
			add(portraitLayoutContainer, new BorderLayoutData(LayoutRegion.EAST, 60.0f));
			
			LayoutContainer layoutContainer_1 = new LayoutContainer();
			layoutContainer_1.setLayout(new FillLayout(Orientation.VERTICAL));
			
			LayoutContainer nameLayoutContainer = new LayoutContainer();
			HBoxLayout hbl_nameLayoutContainer = new HBoxLayout();
			hbl_nameLayoutContainer.setPack(BoxLayoutPack.END);
			hbl_nameLayoutContainer.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
			nameLayoutContainer.setLayout(hbl_nameLayoutContainer);
			
			nameTxt = new Text();
			nameLayoutContainer.add(nameTxt, new HBoxLayoutData(6, 3, 3, 3));
			nameTxt.setAutoWidth(true);
			nameTxt.setStyleAttribute("font-size","14px");
			nameTxt.setStyleAttribute("text-align", "right");
			nameTxt.setStyleAttribute("width", "auto !important");
			nameTxt.setStyleAttribute("overflow","visible !important");
			
			LayoutContainer configLayoutContainer = new LayoutContainer();
			HBoxLayout hbl_configLayoutContainer = new HBoxLayout();
			hbl_configLayoutContainer.setPack(BoxLayoutPack.END);
			hbl_configLayoutContainer.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
			configLayoutContainer.setLayout(hbl_configLayoutContainer);
			
			Button msgBtn = new Button("Message");
			nameLayoutContainer.add(msgBtn, new HBoxLayoutData(3, 3, 3, 3));
			layoutContainer_1.add(nameLayoutContainer);
			
			Button configBtn = new Button("Setting");
			configLayoutContainer.add(configBtn, new HBoxLayoutData(2, 2, 2, 2));
			
			Button btnLogout = new Button("logout");
			configLayoutContainer.add(btnLogout, new HBoxLayoutData(2, 2, 2, 2));
			layoutContainer_1.add(configLayoutContainer);
			add(layoutContainer_1, new BorderLayoutData(LayoutRegion.CENTER));
		}

		public void loadUserInfo(User user){
			if(user != null){
				nameTxt.setText(user.getName());
			}
		}
	}

}
