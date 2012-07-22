package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
import com.tenline.pinecone.platform.web.store.client.events.FriendEvents;
import com.tenline.pinecone.platform.web.store.client.events.MailEvents;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class HomeViewport extends AbstractViewport{
	
	private MainPanel mainPanel;
	private UserHeader userHeader;
	
	public HomeViewport() {
		super();

		body.setHeaderVisible(false);
		
		userHeader = new UserHeader();
		header.add(userHeader, new BorderLayoutData(LayoutRegion.EAST,200));

		mainPanel = new MainPanel();
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(3,3,3,3));
		body.add(mainPanel, bld);
	}
	/**
	 * load information
	 */
	public void loadInfo(){
		User user = (User)Registry.get(User.class.getName());
		userHeader.loadUserInfo(user);
		//load user's applications
		AppEvent appEvent = new AppEvent(ApplicationEvents.GET_BY_USER);
		Dispatcher.get().dispatch(appEvent);
	}
	
	public void loadApps(Collection<BeanModel> userApps){
		mainPanel.loadApps(userApps);
		
		//load user's friends
		AppEvent friEvent = new AppEvent(FriendEvents.GET_BY_USER);
		Dispatcher.get().dispatch(friEvent);
	}
	
	public void loadFriends(Collection<BeanModel> userFriends){
		mainPanel.loadFriends(userFriends);
		//load user's messages
		AppEvent msgEvent = new AppEvent(MailEvents.GET_UNREAD_COUNT);
		Dispatcher.get().dispatch(msgEvent);
	}
	
	public void loadMails(int count){
		userHeader.loadMails(count);
	}
	
	private class MainPanel extends ContentPanel{
		/**portal use to show application*/
		private Portal portal;
		/**portal column num*/
		private int portalColumn = 2;
		
		
		/**Store to store application bean*/
		private ListStore<BeanModel> appStore;
		/**Store to store friend bean*/
		private ListStore<BeanModel> friendStore;
		
		public MainPanel(){
			setHeaderVisible(false);
			this.setBodyBorder(false);
			this.setBorders(false);
			appStore = new ListStore<BeanModel>();
			friendStore = new ListStore<BeanModel>();
			setLayout(new BorderLayout());
			
			LayoutContainer mainLayoutContainer = new LayoutContainer();
			mainLayoutContainer.setLayout(new BorderLayout());
			mainLayoutContainer.setBorders(false);
			
			LayoutContainer eastLayoutContainer = new LayoutContainer();
			eastLayoutContainer.setLayout(new FillLayout(Orientation.VERTICAL));
			eastLayoutContainer.setBorders(false);
			
			//application content panel			 
			eastLayoutContainer.add(createAppContentPanel(), new FillData(0, 0, 5, 0));
			//friend content panel
			eastLayoutContainer.add(createFriendContentPanel(), new FillData(0, 0, 5, 0));
			
			BorderLayoutData bld = new BorderLayoutData(LayoutRegion.EAST, 250.0f);
			bld.setMargins(new Margins(0,0,0,5));
			mainLayoutContainer.add(eastLayoutContainer, bld);
			//portal content panel
			mainLayoutContainer.add(createPortalContentPanel(), new BorderLayoutData(LayoutRegion.CENTER));
			
			add(mainLayoutContainer, new BorderLayoutData(LayoutRegion.CENTER));
		}
		
		public ContentPanel createPortalContentPanel(){

			ContentPanel portalContentpanel = new ContentPanel();
			portalContentpanel.setHeading(((Messages) Registry.get(Messages.class.getName())).HomeViewport_portal_title());
			portalContentpanel.setLayout(new FitLayout());
			portalContentpanel.setHeaderVisible(false);
			portalContentpanel.setBorders(false);
			portalContentpanel.setBodyBorder(false);
			
			portal = new Portal(portalColumn);
			portalContentpanel.add(portal);
			portal.setColumnWidth(0, 0.5);
			portal.setColumnWidth(1, 0.5);
			
			ToolBar toolBar = new ToolBar();
			toolBar.setHeight("38px");
			
			Text txtDashboard = new Text(((Messages) Registry.get(Messages.class.getName())).HomeViewport_portal_title());
			toolBar.add(txtDashboard);
			txtDashboard.setStyleAttribute("font-size", "18px");
			txtDashboard.setStyleAttribute("font-weight", "bold");
			txtDashboard.setStyleAttribute("line-height", "18px");
			txtDashboard.setStyleAttribute("color","#4D5762");
			txtDashboard.setStyleAttribute("position","relative");
			txtDashboard.setStyleAttribute("word-spacing","-0.1em");
			
			FillToolItem fillToolItem = new FillToolItem();
			toolBar.add(fillToolItem);
			
			Button btnGotoStore = new Button();
			btnGotoStore.addListener(Events.Select,new Listener<ButtonEvent>() {

				@Override
				public void handleEvent(ButtonEvent be) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_APP_STORE_TO_PANEL);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			toolBar.add(btnGotoStore);
//			btnGotoStore.setIcon(((Images)Registry.get(Images.class.getName())).store());
			btnGotoStore.setText(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_title());
//			btnGotoStore.addStyleName("btn-blue");
			btnGotoStore.setHeight("32px");
			portalContentpanel.setTopComponent(toolBar);
						
			return portalContentpanel;
		}
		
		public ContentPanel createFriendContentPanel(){
			ContentPanel friendContentpanel = new ContentPanel();
			friendContentpanel.setHeaderVisible(false);
			friendContentpanel.setBodyBorder(false);
			friendContentpanel.setBorders(false);
			
			ToolBar friendContentPanelToolBar  = new ToolBar();
			friendContentPanelToolBar.setHeight("34px");
			Text friendTitleText = new Text(((Messages) Registry.get(Messages.class.getName())).HomeViewport_header_myfriend());
			friendTitleText.setStyleAttribute("font-size", "12px");
			friendTitleText.setStyleAttribute("font-weight", "bold");
			friendTitleText.setStyleAttribute("line-height", "12px");
			friendTitleText.setStyleAttribute("color","#4D5762");
			friendTitleText.setStyleAttribute("position","relative");
			friendTitleText.setStyleAttribute("word-spacing","-0.1em");
			
			friendContentPanelToolBar.add(friendTitleText);
			FillToolItem appFillToolItem = new FillToolItem();
			friendContentPanelToolBar.add(appFillToolItem);
			
			Button devMenuBtn = new Button(((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_friendconfig());
			friendContentPanelToolBar.add(devMenuBtn);
			devMenuBtn.addListener(Events.Select,new Listener<ButtonEvent>() {
				@Override
				public void handleEvent(ButtonEvent be) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_FRIENDS_MANAGE_TO_PANEL);
					Dispatcher.get().dispatch(appEvent);
				}
			});
			devMenuBtn.setToolTip(((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_tooltip_friendconfig());
			devMenuBtn.setHeight("30px");
			
			friendContentpanel.setTopComponent(friendContentPanelToolBar);
			
			friendContentpanel.setLayout(new FitLayout());
			List<ColumnConfig> configs_1 = new ArrayList<ColumnConfig>();
			
			ColumnConfig clmncnfgInfo_1 = new ColumnConfig("id", "", 180);
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
			
			Grid<BeanModel> grid_1 = new Grid<BeanModel>(friendStore, new ColumnModel(configs_1));
			grid_1.setAutoHeight(false);
			grid_1.setAutoWidth(false);
			grid_1.getView().setAutoFill(true);
			friendContentpanel.add(grid_1, new FitData(0, 0, 0, 0));
			grid_1.setBorders(true);
			grid_1.setHideHeaders(true);
			grid_1.setStripeRows(true);
			
			return friendContentpanel;
		}
		
		public ContentPanel createAppContentPanel(){
			ContentPanel appContentpanel = new ContentPanel();
			appContentpanel.setHeaderVisible(false);
			appContentpanel.setBodyBorder(false);
			appContentpanel.setBorders(false);
			appContentpanel.getBody();
			
			ToolBar appContentPanelToolBar  = new ToolBar();
			appContentPanelToolBar.setHeight("34px");
			Text appTitleText = new Text(((Messages) Registry.get(Messages.class.getName())).HomeViewport_header_myapp());
			appTitleText.setStyleAttribute("font-size", "12px");
			appTitleText.setStyleAttribute("font-weight", "bold");
			appTitleText.setStyleAttribute("line-height", "12px");
			appTitleText.setStyleAttribute("color","#4D5762");
			appTitleText.setStyleAttribute("position","relative");
			appTitleText.setStyleAttribute("word-spacing","-0.1em");
			appContentPanelToolBar.add(appTitleText);
			FillToolItem appFillToolItem = new FillToolItem();
			appContentPanelToolBar.add(appFillToolItem);
			
			Button devMenuBtn = new Button(((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_appconfig());
			appContentPanelToolBar.add(devMenuBtn);
			devMenuBtn.addListener(Events.Select,new Listener<ButtonEvent>() {

				@Override
				public void handleEvent(ButtonEvent be) {
					
				}

			});
			devMenuBtn.setToolTip(((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_tooltip_appconfig());
			devMenuBtn.setHeight("30px");
			appContentpanel.setTopComponent(appContentPanelToolBar);
			
			appContentpanel.setLayout(new FitLayout());
			List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
			
			ColumnConfig clmncnfgInfo = new ColumnConfig("id", "", 180);
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
			grid.setStripeRows(true);
			
			return appContentpanel;
		}
	
		public void loadApps(Collection<BeanModel> userApps){
			try{
				int iCount = 0;
				for(LayoutContainer lc:portal.getItems()){
					lc.removeAll();
				}
				
				
				for(BeanModel bm:userApps){
					
					if(bm.get("status").equals(Application.OPENED)){
						ConsumerPortlet p = new ConsumerPortlet((Application)bm.get("thisapplication"));
						Consumer con = (Consumer)bm.get("thisconsumer");
						p.setHeading(con.getName());
						p.setUrl(con.getConnectURI());
						portal.add(p, iCount%portalColumn);
						iCount++;
					}
				}
				
				appStore.removeAll();
				appStore.add(new ArrayList<BeanModel>(userApps));
				appStore.commitChanges();
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		public void loadFriends(Collection<BeanModel> userFriends){
			friendStore.removeAll();
			friendStore.add(new ArrayList<BeanModel>(userFriends));
			friendStore.commitChanges();
		}

	}
	
	private class UserHeader extends ContentPanel {
		/**user name*/
		private Text nameTxt;
		/**mails button*/
		private Button msgBtn;
		
		public UserHeader() {
			setHeaderVisible(false);
			setSize("200", "50");
			setLayout(new BorderLayout());
			this.setBodyBorder(false);
			this.setBorders(false);
			setBodyStyle("background-color: transparent");
			
			LayoutContainer portraitLayoutContainer = new LayoutContainer();
			portraitLayoutContainer.setLayout(new FitLayout());
			
			Image portraitImage = ((Images)Registry.get(Images.class.getName())).user().createImage();
			portraitLayoutContainer.add(portraitImage);
			add(portraitLayoutContainer, new BorderLayoutData(LayoutRegion.EAST, 50.0f));
			portraitLayoutContainer.setBorders(true);
			
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
			
			msgBtn = new Button();
			msgBtn.setIcon(((Images)Registry.get(Images.class.getName())).email());
			nameLayoutContainer.add(msgBtn, new HBoxLayoutData(3, 3, 3, 3));
			layoutContainer_1.add(nameLayoutContainer);
			msgBtn.addListener(Events.Select,new Listener<ButtonEvent>() {

				@Override
				public void handleEvent(ButtonEvent be) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_MAIL_LIST_TO_PANEL);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			
			com.google.gwt.user.client.ui.Button configBtn = new com.google.gwt.user.client.ui.Button(((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_setting());
			configLayoutContainer.add(configBtn, new HBoxLayoutData(2, 2, 2, 2));
			configBtn.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_SETTING_TO_PANEL);
					Dispatcher.get().dispatch(appEvent);
				}
			});
			configBtn.setStyleName("btn-blue");
			
			com.google.gwt.user.client.ui.Button btnLogout = new com.google.gwt.user.client.ui.Button(((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_logout());
			btnLogout.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent appEvent = new AppEvent(UserEvents.LOGOUT);
					Dispatcher.get().dispatch(appEvent);
				}
			});
			btnLogout.addStyleName("btn-blue");
			configLayoutContainer.add(btnLogout, new HBoxLayoutData(2, 2, 2, 2));
			layoutContainer_1.add(configLayoutContainer);
			add(layoutContainer_1, new BorderLayoutData(LayoutRegion.CENTER));
		}

		public void loadUserInfo(User user){
			if(user != null){
				nameTxt.setText(user.getName());
			}
		}
		
		public void loadMails(int mailCount){
			msgBtn.setToolTip("You've got "+mailCount+" mails");
		}
	}

}