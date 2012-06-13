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
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Viewport;
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
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.FriendViewEvents;

public class HomeViewport extends Viewport{
	/**current user info*/
	private User curUser;
	/**application count*/
	private int count;
	/**portal use to show application*/
	private Portal portal;
	/**portal column num*/
	private int portalColumn = 2;
	/**application user installed*/
	private Collection<Application> userApps;
	private Map<String,Consumer> appMap = new LinkedHashMap<String,Consumer>();
	/**percentage*/
	private double percentage;
	private int iCount;
	/**use to generate Application BeanModel*/
	private BeanModelFactory applicationFactory = BeanModelLookup.get().getFactory(Application.class);
	/**use to generate friend BeanModel*/
	private BeanModelFactory friendFactory = BeanModelLookup.get().getFactory(Friend.class);
	
	private ListStore<BeanModel> appStore;
	private ListStore<BeanModel> friendStore;
	/**message num text*/
	private Text msgNumText;
	
	public HomeViewport(User user) {
		curUser = user;
		appStore = new ListStore<BeanModel>();
		friendStore = new ListStore<BeanModel>();
		
		setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setBorders(true);
		layoutContainer.setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer_4 = new LayoutContainer();
		layoutContainer_4.setLayout(new FitLayout());
		
		Image image = ((Images) Registry.get(Images.class.getName())).logo().createImage();
		layoutContainer_4.add(image, new FitData(35, 8, 35, 8));
		layoutContainer.add(layoutContainer_4, new BorderLayoutData(LayoutRegion.WEST));
		
		LayoutContainer layoutContainer_5 = new LayoutContainer();
		layoutContainer_5.setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer_6 = new LayoutContainer();
		layoutContainer_6.setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer_7 = new LayoutContainer();
		HBoxLayout hbl_layoutContainer_7 = new HBoxLayout();
		hbl_layoutContainer_7.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
		hbl_layoutContainer_7.setPack(BoxLayoutPack.END);
		layoutContainer_7.setLayout(hbl_layoutContainer_7);
		
		com.google.gwt.user.client.ui.Button btnSetting = new com.google.gwt.user.client.ui.Button("Setting");
		layoutContainer_7.add(btnSetting);
		
		com.google.gwt.user.client.ui.Button btnNewButton = new com.google.gwt.user.client.ui.Button("Logout");
		layoutContainer_7.add(btnNewButton, new HBoxLayoutData(0, 0, 0, 5));
		layoutContainer_6.add(layoutContainer_7, new BorderLayoutData(LayoutRegion.SOUTH, 40.0f));
		
		LayoutContainer layoutContainer_8 = new LayoutContainer();
		layoutContainer_8.setLayout(new FitLayout());
		
		Image image_1 = new Image((String) null);
		layoutContainer_8.add(image_1, new FitData(11, 8, 11, 8));
		layoutContainer_6.add(layoutContainer_8, new BorderLayoutData(LayoutRegion.EAST, 80.0f));
		
		LayoutContainer layoutContainer_9 = new LayoutContainer();
		layoutContainer_9.setLayout(new RowLayout(Orientation.VERTICAL));
		BorderLayoutData bld_layoutContainer_9 = new BorderLayoutData(LayoutRegion.CENTER);
		bld_layoutContainer_9.setMargins(new Margins(11, 0, 11, 0));
		
		LayoutContainer layoutContainer_10 = new LayoutContainer();
		HBoxLayout hbl_layoutContainer_10 = new HBoxLayout();
		layoutContainer_10.setLayout(hbl_layoutContainer_10);
		
		Text txtWelcomeBack = new Text(((Messages) Registry.get(Messages.class.getName())).homeView_welcomeBack());
		layoutContainer_10.add(txtWelcomeBack);
		
		Text txtUsername = new Text(user.getName());
		layoutContainer_10.add(txtUsername);
		layoutContainer_9.add(layoutContainer_10);
		
		LayoutContainer layoutContainer_11 = new LayoutContainer();
		HBoxLayout hbl_layoutContainer_11 = new HBoxLayout();
		layoutContainer_11.setLayout(hbl_layoutContainer_11);
		
		Text txtYouReceived = new Text(((Messages) Registry.get(Messages.class.getName())).homeView_youReceived());
		layoutContainer_11.add(txtYouReceived);
		
		msgNumText = new Text("0");
		layoutContainer_11.add(msgNumText);
		
		com.google.gwt.user.client.ui.Button btnMessages = new com.google.gwt.user.client.ui.Button(((Messages) Registry.get(Messages.class.getName())).homeView_messages());
		layoutContainer_11.add(btnMessages);
		layoutContainer_9.add(layoutContainer_11);
		layoutContainer_6.add(layoutContainer_9, bld_layoutContainer_9);
		layoutContainer_5.add(layoutContainer_6, new BorderLayoutData(LayoutRegion.EAST, 300.0f));
		layoutContainer.add(layoutContainer_5, new BorderLayoutData(LayoutRegion.CENTER));
		layoutContainer_5.setBorders(true);
		add(layoutContainer, new BorderLayoutData(LayoutRegion.NORTH, 130.0f));
		
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
				AppEvent appEvent = new AppEvent(FriendViewEvents.INIT_FRIEND_VIEW);
				appEvent.setData("currentUser", curUser);
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
//			System.out.println("User r name:"+f.getReceiver().getName());
//			System.out.println("User r id:"+f.getReceiver().getId());
//			System.out.println("User s name:"+f.getSender().getName());
//			System.out.println("User s id:"+f.getSender().getId());
			
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
	
	public void loadMessage(Collection<Mail> userMails){
		msgNumText.setText(""+userMails.size());
	}

}
