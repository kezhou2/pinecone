package com.tenline.pinecone.platform.web.store.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.tenline.pinecone.platform.web.store.client.model.AppInfo;
import com.tenline.pinecone.platform.web.store.client.model.ConsumerInfo;
import com.tenline.pinecone.platform.web.store.client.model.DeviceInfo;
import com.tenline.pinecone.platform.web.store.client.model.UserInfo;
import com.tenline.pinecone.platform.web.store.client.portal.CommonPortlet;

public class UserHomePage extends ContentPanel implements PageRefreshInterface{
	private ContentPanel statusContainer;
	private ContentPanel mainContainer;
	/**用于显示用户安装应用的Panel*/
	private ContentPanel appContentPanel;
	/**用于显示用户好友的Panel*/
	private ContentPanel friendContentPanel;
	/**用于显示应用的Portal*/
	private Portal appPortal;
	/**用户安装应用的Store*/
	private TreeStore<ConsumerInfo> myAppStore;
	/**显示用户已经安装应用的TreePanel*/
	private TreePanel<ConsumerInfo> appTreePanel;
	
	/**用于存储用户已安装应用的Portlet*/
	private Set<Portlet> portletSet;
	/**用户好友的Store*/
	private TreeStore<UserInfo> myRelationsStore;
	/**显示用户已经安装应用的TreePanel*/
	private TreePanel<UserInfo> friendTreePanel;
	
	int count;
	int index;
	private LayoutContainer layoutContainer;
	private LayoutContainer layoutContainer_1;
	private LayoutContainer layoutContainer_2;
	private Image image;
	private Button button;
	
	private ContentPanel deviceContentPanel;
	/**用户好友的Store*/
	private TreeStore<DeviceInfo> myDevicesStore;
	/**显示用户设备的TreePanel*/
	private TreePanel<DeviceInfo> deviceTreePanel;
	
	public void init(){
		myAppStore = new TreeStore<ConsumerInfo>();
		myRelationsStore = new TreeStore<UserInfo>();
		myDevicesStore = new TreeStore<DeviceInfo>();
		portletSet = new HashSet<Portlet>();
		count = 0;
	}
	public UserHomePage() {	
		init();
		
		setHeaderVisible(false);
		setHeading("New ContentPanel");
		setCollapsible(true);
		setLayout(new BorderLayout());
		
		/**
		 * 页面左侧的用户状态区
		 */
		statusContainer = new ContentPanel();
		statusContainer.setHeaderVisible(false);
		statusContainer.setHeading("New ContentPanel");
		statusContainer.setCollapsible(true);
		statusContainer.setLayout(new BorderLayout());
		/**
		 * 页面左侧，用户头像及欢迎提示
		 */
		ContentPanel northContainer = new ContentPanel();
		northContainer.setHeaderVisible(false);
		northContainer.setHeading("New ContentPanel");
		northContainer.setCollapsible(true);
		northContainer.setLayout(new RowLayout(Orientation.VERTICAL));
		
		
		BorderLayoutData bld_northContainer = new BorderLayoutData(LayoutRegion.NORTH, 200.0f);
		bld_northContainer.setMargins(new Margins(2, 2, 2, 2));
		
		layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new CenterLayout());
		
		layoutContainer_2 = new LayoutContainer();
		layoutContainer_2.setLayout(new FitLayout());
		
		image = new Image("../img/contact.jpg");
		layoutContainer_2.add(image);
		layoutContainer.add(layoutContainer_2);
		layoutContainer_2.setSize("128", "128");
		northContainer.add(layoutContainer);
		layoutContainer.setHeight("144");
		
		layoutContainer_1 = new LayoutContainer();
		layoutContainer_1.setLayout(new FitLayout());
		
		button = new Button("<m-appstore-panel-button>添加应用</m-appstore-panel-button>");
		layoutContainer_1.add(button, new FitData(3, 20, 3, 20));
		button.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				
				Widget w = UserHomePage.this.getParent();
				if(w instanceof ContentPanel){
					ContentPanel cp = (ContentPanel)w;
					cp.remove(UserHomePage.this);
					cp.layout(true);
					cp.add(EnvConfig.getAppPage(),new BorderLayoutData(LayoutRegion.CENTER));
					cp.layout(true);
				}
			}
		});
		northContainer.add(layoutContainer_1, new RowData(Style.DEFAULT, -1.0, new Margins()));
		layoutContainer_1.setHeight("40");
		statusContainer.add(northContainer, bld_northContainer);
		
		ContentPanel menuContainer = new ContentPanel();
		menuContainer.setHeaderVisible(false);
		menuContainer.setHeading("New ContentPanel");
		menuContainer.setCollapsible(true);
		menuContainer.setLayout(new BorderLayout());
		statusContainer.add(menuContainer, new BorderLayoutData(LayoutRegion.CENTER));
		
		/**
		 * 页面左侧，菜单项，包括用户当前添加的应用以及用户的好友
		 */
		ContentPanel menuContainer_1 = new ContentPanel();
		menuContainer_1.setHeaderVisible(false);
		menuContainer_1.setHeading("New ContentPanel");
		menuContainer_1.setCollapsible(true);
		AccordionLayout al_menuContainer_1 = new AccordionLayout();
		menuContainer_1.setLayout(al_menuContainer_1);
		//用户安装设备的显示Panel
		deviceContentPanel = new ContentPanel();
		deviceContentPanel.setHeading("<m-appstore-panel-title>我的设备</m-appstore-panel-title>");
		deviceContentPanel.setCollapsible(true);
		deviceContentPanel.setLayout(new FitLayout());
		
		deviceTreePanel = new TreePanel<DeviceInfo>(myDevicesStore);
		deviceContentPanel.add(deviceTreePanel);
		deviceTreePanel.setBorders(true);
		deviceTreePanel.setDisplayProperty("name");
		menuContainer_1.add(deviceContentPanel);
		
		ToolButton devMenuBtn = new ToolButton("x-tool-gear");
		deviceContentPanel.getHeader().addTool(devMenuBtn);
		devMenuBtn.addSelectionListener(new SelectionListener<IconButtonEvent>(){

			@Override
			public void componentSelected(IconButtonEvent ce) {
				
			}
			
		});
		
		//用户安装应用的显示Panel
		appContentPanel = new ContentPanel();
		appContentPanel.setHeading("<m-appstore-panel-title>\u6211\u7684\u5E94\u7528</m-appstore-panel-title>");
		appContentPanel.setCollapsible(true);
		FitLayout fl_appContentPanel = new FitLayout();
		appContentPanel.setLayout(fl_appContentPanel);
		
		appTreePanel = new TreePanel<ConsumerInfo>(myAppStore);
		appContentPanel.add(appTreePanel);
		appTreePanel.setBorders(true);
		appTreePanel.setDisplayProperty("displayName");
		menuContainer_1.add(appContentPanel);
		
		ToolButton appMenuBtn = new ToolButton("x-tool-gear");
		appContentPanel.getHeader().addTool(appMenuBtn);
		
		friendContentPanel = new ContentPanel();
		friendContentPanel.setHeading("<m-appstore-panel-title>\u6211\u7684\u597D\u53CB</m-appstore-panel-title>");
		friendContentPanel.setCollapsible(true);
		FitLayout fl_friendContentPanel = new FitLayout();
		friendContentPanel.setLayout(fl_friendContentPanel);
		
		friendTreePanel = new TreePanel<UserInfo>(myRelationsStore);
		friendContentPanel.add(friendTreePanel);
		friendTreePanel.setBorders(true);
		friendTreePanel.setDisplayProperty("name");
		
		ToolButton friMenuBtn = new ToolButton("x-tool-gear");
		friendContentPanel.getHeader().addTool(friMenuBtn);
		
		menuContainer_1.add(friendContentPanel);
		BorderLayoutData bld_menuContainer_1 = new BorderLayoutData(LayoutRegion.CENTER);
		bld_menuContainer_1.setMargins(new Margins(2, 2, 2, 2));
		menuContainer.add(menuContainer_1, bld_menuContainer_1);

		add(statusContainer, new BorderLayoutData(LayoutRegion.WEST,150.0f));
		
		/**
		 * 页面右侧显示应用的Portal
		 */
		mainContainer = new ContentPanel();
		mainContainer.setHeaderVisible(false);
		mainContainer.setHeading("New ContentPanel");
		mainContainer.setCollapsible(true);
		mainContainer.setLayout(new FitLayout());

		setPortalMode();
		BorderLayoutData bld_mainContainer = new BorderLayoutData(LayoutRegion.CENTER);
		bld_mainContainer.setMargins(new Margins(2, 2, 2, 2));
		add(mainContainer, bld_mainContainer);
	}
	
	public void remove(Portlet p){
		if(portletSet.contains(p)){
			portletSet.remove(p);
			p.removeFromParent();
		}
	}
	
	public void setPortalMode(){
		mainContainer.removeAll();
		mainContainer.layout(true);
		appPortal = new Portal(2);
		appPortal.setColumnWidth(0, 0.5);
		appPortal.setColumnWidth(1, 0.5);
		mainContainer.add(appPortal);
		reset(null);
		mainContainer.layout(true);
	}
	
	public void setMaxMode(AppInfo info){
		mainContainer.removeAll();
		mainContainer.layout(true);
		MaxViewPage page = new MaxViewPage(info);
		mainContainer.add(page,new FitData(15));
		mainContainer.layout(true);
	}
	@Override
	public void reset(Object obj) {
		
		for(Portlet p:portletSet){
			p.removeFromParent();
		}
		myAppStore.removeAll();
		index = 0;
		count = 0;
		
		count++;
		EnvConfig.getPineconeService().getAppInfo(
				EnvConfig.getLoginUser(), new AsyncCallback<List<AppInfo>>(){

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.info("错误", "访问后台获取用户的应用信息失败", null);
			}

			@Override
			public void onSuccess(List<AppInfo> result) {
				count--;
				for(final AppInfo ai:result){
					count++;
			
					EnvConfig.getPineconeService().getConsumerById(ai.getConsumerId(), new AsyncCallback<ConsumerInfo>(){

						@Override
						public void onFailure(Throwable caught) {
							MessageBox.info("错误", "访问后台获取用户的应用信息失败", null);
						}

						@Override
						public void onSuccess(ConsumerInfo result) {
							count--;
//							System.out.println("****\nEnvConfig.getPineconeService().getConsumerInfo:"+result.getDisplayName());
							
							//将用户拥有的应用信息添加到环境变量（单体）中
//							EnvConfig.getMyApplication().add(result);
							
							//刷新用户安装应用列表
							
							myAppStore.add(result, false);
							myAppStore.commitChanges();
							//用Portal显示用户的每个应用
							CommonPortlet cp = new CommonPortlet();
							cp.setAppInfo(ai);
							appPortal.add(cp, index%2);
							portletSet.add(cp);
							index++;
							
							//判断调用后台服务获取用户应用信息的操作是否结束
							if(count <= 0){
								//TODO
							}
						}	
					});
				}
			}		
		});
		
//		System.out.println("getUserRelation 1");
		if(myRelationsStore.getAllItems().size() <= 0){
//			System.out.println("getUserRelation 2");
			EnvConfig.getPineconeService().getUserRelation(
					EnvConfig.getLoginUser(), new AsyncCallback<List<UserInfo>>(){

				@Override
				public void onFailure(Throwable caught) {
					MessageBox.info("错误", "访问后台获取用户的好友信息失败", null);
				}

				@Override
				public void onSuccess(List<UserInfo> result) {
//					System.out.println("getUserRelation 3");
					if(result != null){
//						System.out.println("User Name:"+result.get(0).getName());
						myRelationsStore.add(result, false);
						myRelationsStore.commitChanges();
					}
				}
						
			});
		}
		
		if(myDevicesStore.getAllItems().size() <= 0){
			EnvConfig.getPineconeService().getAllDevice(EnvConfig.getLoginUser(), new AsyncCallback<List<DeviceInfo>>(){

				@Override
				public void onFailure(Throwable caught) {
					MessageBox.info("错误", "访问后台获取用户的设备信息失败", null);
				}

				@Override
				public void onSuccess(List<DeviceInfo> result) {
					if(result != null){
						myDevicesStore.add(result, false);
						myDevicesStore.commitChanges();
					}
				}
				
			});
		}
	}
}
