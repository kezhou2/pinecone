package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.controllers.ApplicationController;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
import com.tenline.pinecone.platform.web.store.client.events.FriendEvents;
import com.tenline.pinecone.platform.web.store.client.events.MailEvents;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class FriendPageViewport extends AbstractViewport {
	private MainPanel mainPanel;
	private UserHeader userHeader;
	
	public FriendPageViewport() {
		super();

		body.setHeaderVisible(false);
		
		userHeader = new UserHeader();
		header.add(userHeader, new BorderLayoutData(LayoutRegion.EAST,200));

		mainPanel = new MainPanel();
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(10,10,10,10));
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
			mainLayoutContainer.addStyleName("abstractviewport-background");
			
			LayoutContainer eastLayoutContainer = new LayoutContainer();
			eastLayoutContainer.addStyleName("abstractviewport-background");
			eastLayoutContainer.setLayout(new BorderLayout());
			LayoutContainer toolbarLayoutContainer = new LayoutContainer();
			BorderLayoutData toolbarBld = new BorderLayoutData(LayoutRegion.NORTH, 40.0f);
			toolbarBld.setMargins(new Margins(0,0,0,0));
			eastLayoutContainer.add(toolbarLayoutContainer,toolbarBld);
			
			HBoxLayout hbl_toolbarLayoutContainer = new HBoxLayout();
			hbl_toolbarLayoutContainer.setPack(BoxLayoutPack.CENTER);
			hbl_toolbarLayoutContainer.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
			toolbarLayoutContainer.setLayout(hbl_toolbarLayoutContainer);
			
			Button settingBtn = new Button();
			settingBtn.setTitle(((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_setting());
			toolbarLayoutContainer.add(settingBtn, new HBoxLayoutData(2, 2, 2, 2));
			settingBtn.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_SETTING_TO_PANEL);
					appEvent.setHistoryEvent(true);
					Dispatcher.get().dispatch(appEvent);
				}
			});
			settingBtn.setSize("60px", "30px");
			settingBtn.setStyleName("abstractviewport-btn");
			settingBtn.setHTML("<img class='btn-img-center' src='../images/icons/settings.png' />");
			
			Button logoutBtn = new Button();
			logoutBtn.setTitle(((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_logout());
			logoutBtn.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent appEvent = new AppEvent(UserEvents.LOGOUT);
					Dispatcher.get().dispatch(appEvent);
				}
			});
			toolbarLayoutContainer.add(logoutBtn, new HBoxLayoutData(2, 2, 2, 2));
			logoutBtn.setSize("60px", "30px");
			logoutBtn.setStyleName("abstractviewport-btn");
			logoutBtn.setHTML("<img class='btn-img-center' src='../images/icons/sign-out.png' />");
			
			LayoutContainer userInfoLayoutContainer = new LayoutContainer();
			userInfoLayoutContainer.setLayout(new FillLayout(Orientation.VERTICAL));
			userInfoLayoutContainer.setBorders(false);
			BorderLayoutData userInfoPanelBld = new BorderLayoutData(LayoutRegion.CENTER);
			userInfoPanelBld.setMargins(new Margins(0,0,0,0));
			eastLayoutContainer.add(userInfoLayoutContainer,userInfoPanelBld);
			
			//application content panel			 
			userInfoLayoutContainer.add(createAppContentPanel(), new FillData(0, 5, 5, 0));
			//friend content panel
			userInfoLayoutContainer.add(createFriendContentPanel(), new FillData(0, 5, 5, 0));
			
			BorderLayoutData eastBld = new BorderLayoutData(LayoutRegion.EAST, 250.0f);
			eastBld.setMargins(new Margins(0,0,0,5));
			mainLayoutContainer.add(eastLayoutContainer, eastBld);
			//portal content panel
			BorderLayoutData centerBld = new BorderLayoutData(LayoutRegion.CENTER);
			centerBld.setMargins(new Margins(0,10,5,0));
			mainLayoutContainer.add(createPortalContentPanel(), centerBld);

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
			toolBar.addStyleName("homeviewport-toolbar");
			
			Text txtDashboard = new Text(((Messages) Registry.get(Messages.class.getName())).HomeViewport_portal_title());
			toolBar.add(txtDashboard);
			txtDashboard.addStyleName("homeviewport-title-big");
			
			FillToolItem fillToolItem = new FillToolItem();
			toolBar.add(fillToolItem);
			
			LayoutContainer btnLc = new LayoutContainer();
			btnLc.setLayout(new FillLayout());
			Button btnGotoStore = new Button();
			btnGotoStore.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_APP_STORE_TO_PANEL);
					appEvent.setHistoryEvent(true);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			btnLc.add(btnGotoStore);
			btnGotoStore.setHTML(
					"<img class='btn-img-right' src='../images/icons/forward.png'>"+
					((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_appstore()+
					"</img>"
					);
			btnGotoStore.setTitle(((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_tooltip_appstore());
			
			toolBar.add(btnLc);
			btnGotoStore.setHeight("32px");
			btnGotoStore.setWidth("100px");
			btnGotoStore.setStyleName("abstractviewport-btn");
			portalContentpanel.setTopComponent(toolBar);
			portalContentpanel.addStyleName("homeviewport-panel");
						
			return portalContentpanel;
		}
		
		public ContentPanel createFriendContentPanel(){
			ContentPanel friendContentpanel = new ContentPanel();
			friendContentpanel.setHeaderVisible(false);
			friendContentpanel.setBodyBorder(false);
			friendContentpanel.setBorders(false);
			
			ToolBar friendContentPanelToolBar  = new ToolBar();
			friendContentPanelToolBar.setHeight("38px");
			Text friendTitleText = new Text(((Messages) Registry.get(Messages.class.getName())).HomeViewport_header_myfriend());
			friendTitleText.addStyleName("homeviewport-title-small");
			
			friendContentPanelToolBar.add(friendTitleText);
			FillToolItem appFillToolItem = new FillToolItem();
			friendContentPanelToolBar.add(appFillToolItem);
			friendContentPanelToolBar.addStyleName("homeviewport-toolbar");
			
			LayoutContainer btnLc = new LayoutContainer();
			btnLc.setLayout(new FillLayout());
			Button devMenuBtn = new Button(((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_friendconfig());
			friendContentPanelToolBar.add(btnLc);
			devMenuBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_FRIENDS_MANAGE_TO_PANEL);
					appEvent.setHistoryEvent(true);
					Dispatcher.get().dispatch(appEvent);
				}
			});
			devMenuBtn.setHTML("<img class='btn-img-center' src='../images/icons/customers.png' />");
			devMenuBtn.setTitle(((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_tooltip_friendconfig());
			devMenuBtn.setHeight("30px");
			devMenuBtn.setWidth("30px");
			devMenuBtn.setStyleName("abstractviewport-btn");
			btnLc.add(devMenuBtn);
			
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
			grid_1.setHideHeaders(true);
			grid_1.setStripeRows(true);
			friendContentpanel.addStyleName("homeviewport-panel");
			
			return friendContentpanel;
		}
		
		public ContentPanel createAppContentPanel(){
			ContentPanel appContentpanel = new ContentPanel();
			appContentpanel.setHeaderVisible(false);
			appContentpanel.setBodyBorder(false);
			appContentpanel.setBorders(false);
			
			ToolBar appContentPanelToolBar  = new ToolBar();
			appContentPanelToolBar.setHeight("38px");
			Text appTitleText = new Text(((Messages) Registry.get(Messages.class.getName())).HomeViewport_header_myapp());
			appTitleText.addStyleName("homeviewport-title-small");
			appContentPanelToolBar.add(appTitleText);
			FillToolItem appFillToolItem = new FillToolItem();
			appContentPanelToolBar.add(appFillToolItem);
			appContentPanelToolBar.addStyleName("homeviewport-toolbar");
			
			LayoutContainer btnLc = new LayoutContainer();
			btnLc.setLayout(new FillLayout());
			Button devMenuBtn = new Button(((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_appconfig());
			appContentPanelToolBar.add(btnLc);
			devMenuBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					
				}

			});
			devMenuBtn.setStyleName("abstractviewport-btn");
			devMenuBtn.setHTML("<img class='btn-img-center' src='../images/icons/config.png' />");
			devMenuBtn.setTitle(((Messages) Registry.get(Messages.class.getName())).HomeViewport_button_tooltip_appconfig());
			devMenuBtn.setHeight("30px");
			devMenuBtn.setWidth("30px");
			btnLc.add(devMenuBtn);
			
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
			grid.setStripeRows(true);
			appContentpanel.addStyleName("homeviewport-panel");
			
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
						ConsumerPortlet p = new ConsumerPortlet((Application)bm.get(ApplicationController.APP_INSTANCE));
						Consumer con = (Consumer)bm.get(ApplicationController.APP_CONSUMER);
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
		/**avatar image*/
		private Image portraitImage;
		
		public UserHeader() {
			setHeaderVisible(false);
			setSize("200", "50");
			setLayout(new BorderLayout());
			this.setBodyBorder(false);
			this.setBorders(false);
			setBodyStyle("background-color: transparent");
			
			LayoutContainer portraitLayoutContainer = new LayoutContainer();
			portraitLayoutContainer.setLayout(new FitLayout());
			
			portraitImage = ((Images)Registry.get(Images.class.getName())).user().createImage();
			portraitLayoutContainer.add(portraitImage);
			BorderLayoutData portraitBld = new BorderLayoutData(LayoutRegion.EAST, 50.0f);
			portraitBld.setMargins(new Margins(2,10,2,5));
			add(portraitLayoutContainer, portraitBld);
			portraitLayoutContainer.setBorders(true);
			portraitLayoutContainer.addStyleName("homeviewport-portraitImg");
			
			LayoutContainer layoutContainer_1 = new LayoutContainer();
			layoutContainer_1.setLayout(new FillLayout(Orientation.VERTICAL));
			
			LayoutContainer nameLayoutContainer = new LayoutContainer();
			nameLayoutContainer.setLayout(new FitLayout());
//			HBoxLayout hbl_nameLayoutContainer = new HBoxLayout();
//			hbl_nameLayoutContainer.setPack(BoxLayoutPack.END);
//			hbl_nameLayoutContainer.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
//			nameLayoutContainer.setLayout(hbl_nameLayoutContainer);
			
			nameTxt = new Text();
//			nameLayoutContainer.add(nameTxt, new HBoxLayoutData(6, 3, 3, 3));
			nameLayoutContainer.add(nameTxt);
			nameTxt.setAutoWidth(true);
			nameTxt.addStyleName("homeviewport-welcome-text");
			
			LayoutContainer configLayoutContainer = new LayoutContainer();
			HBoxLayout hbl_configLayoutContainer = new HBoxLayout();
			hbl_configLayoutContainer.setPack(BoxLayoutPack.END);
			hbl_configLayoutContainer.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
			configLayoutContainer.setLayout(hbl_configLayoutContainer);
			
			msgBtn = new Button(((Messages) Registry.get(Messages.class.getName())).HomeViewport_label_msgbox());
			nameLayoutContainer.add(msgBtn, new HBoxLayoutData(3, 3, 3, 3));
			layoutContainer_1.add(nameLayoutContainer);
			msgBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_MAIL_LIST_TO_PANEL);
					appEvent.setHistoryEvent(true);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			msgBtn.setStyleName("homeviewport-btn-text");
			configLayoutContainer.add(msgBtn, new HBoxLayoutData(0, 2, 2, 2));
			msgBtn.setTitle(((Messages) Registry.get(Messages.class.getName())).HomeViewport_label_youReceived()+
					" 0 "+
					((Messages) Registry.get(Messages.class.getName())).HomeViewport_label_messages());
			
			layoutContainer_1.add(configLayoutContainer);
			add(layoutContainer_1, new BorderLayoutData(LayoutRegion.CENTER));
		}

		public void loadUserInfo(User user){
			if(user != null){
				nameTxt.setText(((Messages) Registry.get(Messages.class.getName())).HomeViewport_label_welcomeBack()+user.getName());
				if(user.getAvatar()!=null){
//					String base64 = Base64Utils.toBase64(user.getAvatar());
//					base64 = base64.replace('$', '+');
//					base64 = base64.replace('_', '/');
//					portraitImage.setUrl(base64);
				}
			}
		}
		
		public void loadMails(int mailCount){
			msgBtn.setTitle(((Messages) Registry.get(Messages.class.getName())).HomeViewport_label_youReceived()+" "+
					mailCount+" "+
					((Messages) Registry.get(Messages.class.getName())).HomeViewport_label_messages());
		}
	}
}
