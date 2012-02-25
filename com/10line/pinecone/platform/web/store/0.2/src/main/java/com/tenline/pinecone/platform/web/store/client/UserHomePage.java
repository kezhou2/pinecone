package com.tenline.pinecone.platform.web.store.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.ModelStringProvider;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.tenline.pinecone.platform.web.store.client.portal.CommonPortlet;
import com.tenline.pinecone.platform.web.store.client.window.FriendConfigWindow;
import com.tenline.pinecone.platform.web.store.client.window.SendMailWindow;
import com.tenline.pinecone.platform.web.store.shared.ApplicationInfo;
import com.tenline.pinecone.platform.web.store.shared.ConsumerInfo;
import com.tenline.pinecone.platform.web.store.shared.DeviceInfo;
import com.tenline.pinecone.platform.web.store.shared.FriendInfo;

public class UserHomePage extends ContentPanel implements PageRefreshInterface {
	private ContentPanel statusContainer;
	private ContentPanel mainContainer;
	/** 用于显示用户安装应用的Panel */
	private ContentPanel appContentPanel;
	/** 用于显示用户好友的Panel */
	private ContentPanel friendContentPanel;
	/** 用于显示应用的Portal */
	private Portal appPortal;
	/** 用户安装应用的Store */
	private TreeStore<ApplicationInfo> myAppStore;
	/** 显示用户已经安装应用的TreePanel */
	private TreePanel<ApplicationInfo> appTreePanel;

	/** 用于存储用户已安装应用的Portlet */
	private Set<CommonPortlet> portletSet;
	/** 用户好友的Store */
	private TreeStore<FriendInfo> myFriendStore;
	/** 显示用户已经安装应用的TreePanel */
	private TreePanel<FriendInfo> friendTreePanel;

	int count;
	int index;
	private LayoutContainer layoutContainer;
	private LayoutContainer layoutContainer_1;
	private LayoutContainer layoutContainer_2;
	private Image image;
	private Button button;

	private ContentPanel deviceContentPanel;
	/** 用户好友的Store */
	private TreeStore<DeviceInfo> myDevicesStore;
	/** 显示用户设备的TreePanel */
	private TreePanel<DeviceInfo> deviceTreePanel;
	private LayoutContainer layoutContainer_3;
	private Text nameText;
	private Text mailText;

	// private Text cityText;

	public void init() {
		myAppStore = new TreeStore<ApplicationInfo>();
		myFriendStore = new TreeStore<FriendInfo>();
		myDevicesStore = new TreeStore<DeviceInfo>();
		portletSet = new HashSet<CommonPortlet>();
		count = 0;
	}

	@SuppressWarnings("unused")
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

		BorderLayoutData bld_northContainer = new BorderLayoutData(
				LayoutRegion.NORTH, 270.0f);
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

		button = new Button(
				"<m-appstore-panel-button>添加应用</m-appstore-panel-button>");
		layoutContainer_1.add(button, new FitData(3, 20, 3, 20));
		button.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {

				Widget w = UserHomePage.this.getParent();
				if (w instanceof ContentPanel) {
					ContentPanel cp = (ContentPanel) w;
					cp.remove(UserHomePage.this);
					cp.layout(true);
					cp.add(EnvConfig.getAppPage(), new BorderLayoutData(
							LayoutRegion.CENTER));
					cp.layout(true);
				}
			}
		});

		layoutContainer_3 = new LayoutContainer();
		layoutContainer_3.setLayout(new FillLayout(Orientation.VERTICAL));

		nameText = new Text("<userInfoText>姓名：</userInfoText>");
		layoutContainer_3.add(nameText, new FillData(0, 0, 0, 10));

		// birthText = new Text("生日：");
		// layoutContainer_3.add(birthText, new FillData(0, 0, 0, 10));

		mailText = new Text("邮件：");
		layoutContainer_3.add(mailText, new FillData(0, 0, 0, 10));
		northContainer.add(layoutContainer_3);
		layoutContainer_3.setHeight("80");
		northContainer.add(layoutContainer_1, new RowData(Style.DEFAULT, -1.0,
				new Margins()));
		layoutContainer_1.setHeight("40");
		statusContainer.add(northContainer, bld_northContainer);

		ContentPanel menuContainer = new ContentPanel();
		menuContainer.setHeaderVisible(false);
		menuContainer.setHeading("New ContentPanel");
		menuContainer.setCollapsible(true);
		menuContainer.setLayout(new BorderLayout());
		statusContainer.add(menuContainer, new BorderLayoutData(
				LayoutRegion.CENTER));

		/**
		 * 页面左侧，菜单项，包括用户当前添加的应用以及用户的好友
		 */
		ContentPanel menuContainer_1 = new ContentPanel();
		menuContainer_1.setHeaderVisible(false);
		menuContainer_1.setHeading("New ContentPanel");
		menuContainer_1.setCollapsible(true);
		AccordionLayout al_menuContainer_1 = new AccordionLayout();
		menuContainer_1.setLayout(al_menuContainer_1);
		// 用户安装设备的显示Panel
		deviceContentPanel = new ContentPanel();
		deviceContentPanel
				.setHeading("<m-appstore-panel-title>我的设备</m-appstore-panel-title>");
		deviceContentPanel.setCollapsible(true);
		deviceContentPanel.setLayout(new FitLayout());

		deviceTreePanel = new TreePanel<DeviceInfo>(myDevicesStore);
		deviceContentPanel.add(deviceTreePanel);
		deviceTreePanel.setBorders(true);
		deviceTreePanel.setDisplayProperty("name");
		menuContainer_1.add(deviceContentPanel);

		ToolButton devMenuBtn = new ToolButton("x-tool-gear");
		deviceContentPanel.getHeader().addTool(devMenuBtn);
		devMenuBtn
				.addSelectionListener(new SelectionListener<IconButtonEvent>() {

					@Override
					public void componentSelected(IconButtonEvent ce) {

					}

				});
		devMenuBtn.setToolTip("设备管理控制台");
		deviceTreePanel.setLabelProvider(new ModelStringProvider<DeviceInfo>() {

			@Override
			public String getStringValue(DeviceInfo model, String property) {
				if (property.equals("name")) {
					return model.getDevice().getDriver().getName();
				}
				return null;
			}

		});

		// 用户安装应用的显示Panel
		appContentPanel = new ContentPanel();
		appContentPanel
				.setHeading("<m-appstore-panel-title>\u6211\u7684\u5E94\u7528</m-appstore-panel-title>");
		appContentPanel.setCollapsible(true);
		FitLayout fl_appContentPanel = new FitLayout();
		appContentPanel.setLayout(fl_appContentPanel);

		appTreePanel = new TreePanel<ApplicationInfo>(myAppStore);
		appTreePanel.sinkEvents(Event.ONDBLCLICK);
		appTreePanel.addListener(Events.OnDoubleClick,
				new Listener<BaseEvent>() {

					@Override
					public void handleEvent(BaseEvent be) {
						// TODO
						System.out.println("double clicked.");
					}

				});
		appContentPanel.add(appTreePanel);
		appTreePanel.setBorders(true);
		appTreePanel.setDisplayProperty("name");
		menuContainer_1.add(appContentPanel);

		ToolButton appMenuBtn = new ToolButton("x-tool-gear");
		appContentPanel.getHeader().addTool(appMenuBtn);
		appMenuBtn
				.addSelectionListener(new SelectionListener<IconButtonEvent>() {

					@Override
					public void componentSelected(IconButtonEvent ce) {
						// TODO
					}

				});
		appMenuBtn.setToolTip("应用管理控制台");
		appTreePanel
				.setLabelProvider(new ModelStringProvider<ApplicationInfo>() {

					@Override
					public String getStringValue(ApplicationInfo model,
							String property) {
						if (property.equals("name")) {
							return model.getConsumer().getName();
						}
						return null;
					}

				});
		{
			Menu appContextMenu = new Menu();
			MenuItem open = new MenuItem();
			open.setText("打开");
			open.addSelectionListener(new SelectionListener<MenuEvent>() {
				public void componentSelected(MenuEvent ce) {
					List<ApplicationInfo> selected = appTreePanel
							.getSelectionModel().getSelectedItems();
					for (ApplicationInfo ai : selected) {
						if (ai.equals("Open")) {
							continue;
						} else {
							openApplication(ai);
							break;
						}
					}
				}
			});
			appContextMenu.add(open);
			MenuItem close = new MenuItem();
			close.setText("关闭");
			close.addSelectionListener(new SelectionListener<MenuEvent>() {
				public void componentSelected(MenuEvent ce) {
					List<ApplicationInfo> selected = appTreePanel
							.getSelectionModel().getSelectedItems();
					for (ApplicationInfo ai : selected) {
						if (ai.equals("Close")) {
							continue;
						} else {
							for(CommonPortlet cp:portletSet){
								if(cp.getAppInfo() == ai){
									cp.close();
									break;
								}
							}
						}
					}
				}
			});
			appContextMenu.add(close);
			MenuItem remove = new MenuItem();
			remove.setText("删除");
			remove.addSelectionListener(new SelectionListener<MenuEvent>() {
				public void componentSelected(MenuEvent ce) {
					List<ApplicationInfo> selected = appTreePanel
							.getSelectionModel().getSelectedItems();
					for (final ApplicationInfo ai : selected) {
						if (ai.equals("Close")) {
							continue;
						} else {
							for(CommonPortlet cp:portletSet){
								if(cp.getAppInfo() == ai){
									cp.close();
									
									index--;
									break;
								}
							}
						}
						
						EnvConfig.getPineconeService().
							deleteAppInfo(EnvConfig.getLoginUser(), 
									ai, new AsyncCallback<Void>(){

										@Override
										public void onFailure(Throwable caught) {
											MessageBox.info("错误", "从后台服务删除用户应用失败", null);
										}

										@Override
										public void onSuccess(Void result) {
											myAppStore.remove(ai);
											myAppStore.commitChanges();
										}
								
						});
					}
					
				}
			});
			appContextMenu.add(remove);
			
			appTreePanel.setContextMenu(appContextMenu);
		}

		// 用户好友信息Panel
		friendContentPanel = new ContentPanel();
		friendContentPanel
				.setHeading("<m-appstore-panel-title>\u6211\u7684\u597D\u53CB</m-appstore-panel-title>");
		friendContentPanel.setCollapsible(true);
		FitLayout fl_friendContentPanel = new FitLayout();
		friendContentPanel.setLayout(fl_friendContentPanel);

		friendTreePanel = new TreePanel<FriendInfo>(myFriendStore);
		friendContentPanel.add(friendTreePanel);
		friendTreePanel.setBorders(true);
		friendTreePanel.setDisplayProperty("name");
		GridCellRenderer<FriendInfo> infoRender = new GridCellRenderer<FriendInfo>() {

			@Override
			public Object render(FriendInfo model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<FriendInfo> store, Grid<FriendInfo> grid) {
				// TODO Auto-generated method stub
				return null;
			}

		};
		friendTreePanel.setLabelProvider(new ModelStringProvider<FriendInfo>() {

			@Override
			public String getStringValue(FriendInfo model, String property) {
				if (model.getReceiver() != null && property.equals("name")) {
					if(model.getReceiver().getId().equals(EnvConfig.getLoginUser().getUser().getId())){
						if (model.getIsDecided()){
							return model.getSender().getName();
						}else{
							return model.getSender().getName()+"(未验证)";
						}
						
					}else{
						if (model.getIsDecided()){
							return model.getReceiver().getName();
						}else{
							return model.getReceiver().getName()+"(未验证)";
						}
					}
				}
				return null;
			}
		});
		{
			Menu friendContextMenu = new Menu();
			MenuItem send = new MenuItem();
			send.setText("发送消息");
			send.addSelectionListener(new SelectionListener<MenuEvent>() {
				public void componentSelected(MenuEvent ce) {
					List<FriendInfo> selected = friendTreePanel
							.getSelectionModel().getSelectedItems();
					for (FriendInfo fi : selected) {
						SendMailWindow mw = new SendMailWindow();
						mw.setContactInfo(fi);
						mw.show();
						mw.center();
					}
				}
			});
			friendContextMenu.add(send);

			friendTreePanel.setContextMenu(friendContextMenu);
		}

		ToolButton friMenuBtn = new ToolButton("x-tool-gear");
		friendContentPanel.getHeader().addTool(friMenuBtn);
		friMenuBtn
				.addSelectionListener(new SelectionListener<IconButtonEvent>() {

					@Override
					public void componentSelected(IconButtonEvent ce) {
						// FriendConfigWindow w = new FriendConfigWindow();
						FriendConfigWindow w = new FriendConfigWindow();
						w.show();
						w.center();
					}

				});
		friMenuBtn.setToolTip("好友管理控制台");

		menuContainer_1.add(friendContentPanel);
		BorderLayoutData bld_menuContainer_1 = new BorderLayoutData(
				LayoutRegion.CENTER);
		bld_menuContainer_1.setMargins(new Margins(2, 2, 2, 2));
		menuContainer.add(menuContainer_1, bld_menuContainer_1);

		add(statusContainer, new BorderLayoutData(LayoutRegion.WEST, 160.0f));

		/**
		 * 页面右侧显示应用的Portal
		 */
		mainContainer = new ContentPanel();
		mainContainer.setHeaderVisible(false);
		mainContainer.setHeading("New ContentPanel");
		mainContainer.setCollapsible(true);
		mainContainer.setLayout(new FitLayout());

		setPortalMode();
		BorderLayoutData bld_mainContainer = new BorderLayoutData(
				LayoutRegion.CENTER);
		bld_mainContainer.setMargins(new Margins(2, 2, 2, 2));
		add(mainContainer, bld_mainContainer);
	}

	public void setPortalMode() {
		mainContainer.removeAll();
		mainContainer.layout(true);
		appPortal = new Portal(2);
		appPortal.setColumnWidth(0, 0.5);
		appPortal.setColumnWidth(1, 0.5);
		mainContainer.add(appPortal);
		reset(null);
		mainContainer.layout(true);
	}

	public void setMaxMode(ApplicationInfo info) {
		mainContainer.removeAll();
		mainContainer.layout(true);
		MaxViewPage page = new MaxViewPage(info);
		mainContainer.add(page, new FitData(15));
		mainContainer.layout(true);
	}

	@Override
	public void reset(Object obj) {

		this.nameText.setText("<userInfoText>姓名："
				+ EnvConfig.getLoginUser().getName() + "</userInfoText>");
		this.mailText.setText("邮件：" + EnvConfig.getLoginUser().getEmail());
		// this.birthText.setText("城市："+this.birthText.getText());

		for (Portlet p : portletSet) {
			p.removeFromParent();
		}
		myAppStore.removeAll();
		index = 0;
		count = 0;

		count++;
		EnvConfig.getPineconeService().getAppInfo(EnvConfig.getLoginUser(),
				new AsyncCallback<List<ApplicationInfo>>() {

					@Override
					public void onFailure(Throwable caught) {
						MessageBox.info("错误", "访问后台获取用户的应用信息失败", null);
					}

					@Override
					public void onSuccess(List<ApplicationInfo> result) {
						count--;
						for (final ApplicationInfo ai : result) {
							count++;

							EnvConfig.getPineconeService().getConsumerById(
									ai.getConsumer().getId(),
									new AsyncCallback<ConsumerInfo>() {

										@Override
										public void onFailure(Throwable caught) {
											MessageBox.info("错误",
													"访问后台获取用户的应用信息失败", null);
										}

										@Override
										public void onSuccess(
												ConsumerInfo result) {
											count--;

											// 将用户拥有的应用信息添加到环境变量（单体）中
											// EnvConfig.getMyApplication().add(result);

											// 刷新用户安装应用列表
											ai.setConsumer(result.getConsumer());
											myAppStore.add(ai, false);
											myAppStore.commitChanges();
											// 用Portal显示用户的每个应用
											if(ai.getStatus().equals("Open")){
												openApplication(ai);
											}

											// 判断调用后台服务获取用户应用信息的操作是否结束
											if (count <= 0) {
												// TODO
											}
										}
									});
						}
					}
				});

		if (myFriendStore.getAllItems().size() <= 0) {
			EnvConfig.getPineconeService().getFriends(EnvConfig.getLoginUser(),
					new AsyncCallback<List<FriendInfo>>() {

						@Override
						public void onFailure(Throwable caught) {
							MessageBox.info("错误", "访问后台获取用户的好友信息失败", null);
						}

						@Override
						public void onSuccess(List<FriendInfo> result) {
							if (result != null) {
								myFriendStore.add(result, false);
								myFriendStore.commitChanges();
							}
						}

					});
		}

		if (myDevicesStore.getAllItems().size() <= 0) {
			EnvConfig.getPineconeService().getAllDevice(
					EnvConfig.getLoginUser(),
					new AsyncCallback<List<DeviceInfo>>() {

						@Override
						public void onFailure(Throwable caught) {
							MessageBox.info("错误", "访问后台获取用户的设备信息失败", null);
						}

						@Override
						public void onSuccess(List<DeviceInfo> result) {
							if (result != null) {
								myDevicesStore.add(result, false);
								myDevicesStore.commitChanges();
							}
						}

					});
		}
	}

	public void openApplication(ApplicationInfo appInfo) {
		CommonPortlet cp = new CommonPortlet();
		cp.open(appInfo);
		openPortlet(cp);
	}

	public void openPortlet(CommonPortlet portlet) {
		appPortal.add(portlet, index % 2);
		portletSet.add(portlet);
		index++;
	}

	public void closeCommonPortlet(CommonPortlet portlet) {
		for(Portlet p:portletSet){
			p.removeFromParent();
		}
		
		if (portletSet.contains(portlet)) {
			portletSet.remove(portlet);
		}
		
		index = 0;
		for (CommonPortlet p : portletSet) {
			openPortlet(p);
		}
	}

	public void closeAllPortlet() {
		for(Portlet p:portletSet){
			p.removeFromParent();
		}
		for (CommonPortlet p : portletSet) {
			portletSet.remove(p);
		}
		index = 0;
	}
}
