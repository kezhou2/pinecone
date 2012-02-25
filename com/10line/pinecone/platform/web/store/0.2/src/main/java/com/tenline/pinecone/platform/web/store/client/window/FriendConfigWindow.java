package com.tenline.pinecone.platform.web.store.client.window;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.TabPanel.TabPosition;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.IconButton;
import com.extjs.gxt.ui.client.widget.form.StoreFilterField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.web.store.client.CallbackInter;
import com.tenline.pinecone.platform.web.store.client.EnvConfig;
import com.tenline.pinecone.platform.web.store.shared.FriendInfo;
import com.tenline.pinecone.platform.web.store.shared.UserInfo;

public class FriendConfigWindow extends Window {

	private GroupingStore<FriendInfo> friendStore = new GroupingStore<FriendInfo>();
	private GroupingStore<UserInfo> userStore = new GroupingStore<UserInfo>();
	private ListStore<FriendInfo> requestStore = new ListStore<FriendInfo>();

	private List<String> types = new ArrayList<String>(); 

	public FriendConfigWindow(){
		init();
	}
	
	public void init() {
		setHeading("好友");
		setSize("600", "480");
		setLayout(new FitLayout());

		TabPanel tabPanel = new TabPanel();
		tabPanel.setTabPosition(TabPosition.BOTTOM);

		/*
		 * 显示我的好友
		 */
		TabItem myFriendTabitem = new TabItem("我的好友");
		myFriendTabitem.setLayout(new FitLayout());

		ContentPanel myFriendContentpanel = new ContentPanel();
		myFriendContentpanel.setHeaderVisible(false);
		myFriendContentpanel.setHeading("New ContentPanel");
		myFriendContentpanel.setCollapsible(true);
		myFriendContentpanel.setLayout(new FitLayout());

		{
			List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

			ColumnConfig nameCC = new ColumnConfig("name", "姓名", 150);
			configs.add(nameCC);
			GridCellRenderer<FriendInfo> nameCellRender = new GridCellRenderer<FriendInfo>() {
				@Override
				public Object render(FriendInfo model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<FriendInfo> store, Grid<FriendInfo> grid) {
					if (model.getSender() != null) {
						if (model
								.getSender()
								.getId()
								.equals(EnvConfig.getLoginUser().getUser()
										.getId())) {
							return model.getReceiver().getName();
						} else {
							return model.getSender().getName();
						}
					}
					return null;
				}
			};
			nameCC.setRenderer(nameCellRender);

			ColumnConfig mailCC = new ColumnConfig("email", "邮件", 150);
			configs.add(mailCC);
			GridCellRenderer<FriendInfo> mailCellRender = new GridCellRenderer<FriendInfo>() {
				@Override
				public Object render(FriendInfo model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<FriendInfo> store, Grid<FriendInfo> grid) {
					if (model.getSender() != null) {
						if (model
								.getSender()
								.getId()
								.equals(EnvConfig.getLoginUser().getUser()
										.getId())) {
							return model.getReceiver().getEmail();
						} else {
							return model.getSender().getEmail();
						}
					}
					return null;
				}
			};
			mailCC.setRenderer(mailCellRender);

			ColumnConfig statusCC = new ColumnConfig("isAccept", "", 150);
			configs.add(statusCC);
			GridCellRenderer<FriendInfo> statuslCellRender = new GridCellRenderer<FriendInfo>() {
				@Override
				public Object render(FriendInfo model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<FriendInfo> store, Grid<FriendInfo> grid) {
					if (model.getIsDecided()) {
						return "通过认证";
					} else {
						return "未通过认证";
					}
				}
			};
			statusCC.setRenderer(statuslCellRender);

			ColumnConfig deleteCC = new ColumnConfig("delete", "", 150);
			configs.add(deleteCC);
			GridCellRenderer<FriendInfo> delCellRender = new GridCellRenderer<FriendInfo>() {
				@Override
				public Object render(FriendInfo model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<FriendInfo> store, Grid<FriendInfo> grid) {
					final IconButton btn = new IconButton("delete_friend");
					btn.setToolTip("删除好友");
					final FriendInfo lModel = model;

					btn.addListener(Events.Select,
							new Listener<IconButtonEvent>() {

								@Override
								public void handleEvent(IconButtonEvent be) {
									btn.setEnabled(false);
									EnvConfig
											.getPineconeService()
											.deleteFriend(
													EnvConfig.getLoginUser(),
													lModel,
													new AsyncCallback<Boolean>() {

														@Override
														public void onFailure(
																Throwable caught) {
															MessageBox
																	.info("错误",
																			"调用后台服务删除好友失败",
																			null);
															btn.setEnabled(true);
														}

														@Override
														public void onSuccess(
																Boolean result) {
															friendStore
																	.remove(lModel);
															friendStore
																	.commitChanges();
															btn.setEnabled(true);
														}

													});
								}

							});
					return btn;
				}
			};
			deleteCC.setRenderer(delCellRender);

			GroupingView view = new GroupingView();
			view.setForceFit(true);
			view.setShowGroupedColumn(false);

			final ColumnModel cm = new ColumnModel(configs);
			Grid<FriendInfo> grid = new Grid<FriendInfo>(friendStore, cm);
			grid.setView(view);
			grid.setHideHeaders(true);
			grid.setStripeRows(true);
			myFriendContentpanel.add(grid);
			view.setGroupRenderer(new GridGroupRenderer() {
				public String render(GroupColumnData data) {
					types.add(data.group);
					return data.group + " (" + data.models.size() + "项)";
				}
			});
		}

		ToolBar toolBar = new ToolBar();

//		FillToolItem fillToolItem = new FillToolItem();
//		toolBar.add(fillToolItem);
		Text text1 = new Text("筛选条件：");
		toolBar.add(text1);
		myFriendContentpanel.setTopComponent(toolBar);
		myFriendTabitem.add(myFriendContentpanel);
		tabPanel.add(myFriendTabitem);

		StoreFilterField<FriendInfo> filter = new StoreFilterField<FriendInfo>() {

			@Override
			protected boolean doSelect(Store<FriendInfo> store,
					FriendInfo parent, FriendInfo record, String property,
					String filter) {
				for (String key : record.getPropertyNames()) {
					if (record.get(key).toString().contains(filter)) {
						return true;
					}
				}
				return false;
			}

		};
		filter.bind(friendStore);
		filter.setEmptyText("姓名");
		toolBar.add(filter);

		/*
		 * 搜索好友
		 */
		TabItem searchTabitem = new TabItem("搜索好友");
		searchTabitem.setLayout(new FitLayout());

		ContentPanel searchContentpanel = new ContentPanel();
		searchContentpanel.setHeaderVisible(false);
		searchContentpanel.setHeading("New ContentPanel");
		searchContentpanel.setCollapsible(true);
		searchContentpanel.setLayout(new FitLayout());
		{
			List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

			ColumnConfig nameCC = new ColumnConfig("name", "姓名", 150);
			configs.add(nameCC);
			GridCellRenderer<UserInfo> nameCellRender = new GridCellRenderer<UserInfo>() {
				@Override
				public Object render(UserInfo model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<UserInfo> store, Grid<UserInfo> grid) {

					return model.getName();
				}
			};
			nameCC.setRenderer(nameCellRender);

			ColumnConfig mailCC = new ColumnConfig("email", "邮件", 150);
			configs.add(mailCC);
			GridCellRenderer<UserInfo> mailCellRender = new GridCellRenderer<UserInfo>() {
				@Override
				public Object render(UserInfo model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<UserInfo> store, Grid<UserInfo> grid) {
					return model.getEmail();
				}
			};
			mailCC.setRenderer(mailCellRender);

			ColumnConfig configCC = new ColumnConfig("group", "", 150);
			configs.add(configCC);

			ColumnConfig addCC = new ColumnConfig("add", "", 150);
			configs.add(addCC);
			GridCellRenderer<UserInfo> addCellRender = new GridCellRenderer<UserInfo>() {
				@Override
				public Object render(UserInfo model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<UserInfo> store, Grid<UserInfo> grid) {
					final Button btn = new Button();
					btn.setText("加为好友");
					btn.setToolTip("加为好友");
					final UserInfo lModel = model;

					String id = EnvConfig.getLoginUser().getUser().getId();

					for (FriendInfo fi : friendStore.getModels()) {
						if (fi.getSender().getId().equals(id)) {
							if (fi.getReceiver().getId()
									.equals(model.getUser().getId())) {
								btn.setEnabled(false);
							}
						} else {
							if (fi.getSender().getId()
									.equals(model.getUser().getId())) {
								btn.setEnabled(false);
							}
						}
					}

					btn.addListener(Events.Select, new Listener<ButtonEvent>() {
						@Override
						public void handleEvent(ButtonEvent be) {					
							btn.setEnabled(false);
							FriendTypeWindow w = new FriendTypeWindow(new CallbackInter(){
								@Override
								public void error() {}

								@Override
								public void success(String type) {
									System.out.println("call friend type win.");
									EnvConfig.getPineconeService().addFriend(
											EnvConfig.getLoginUser(), lModel,
											type,
											new AsyncCallback<FriendInfo>() {
							
												@Override
												public void onFailure(Throwable caught) {
													MessageBox.info("错误",
															"调用后台服务添加好友失败", null);
													btn.setEnabled(true);
												}
							
												@Override
												public void onSuccess(FriendInfo result) {
													loadFriends();
													btn.setEnabled(false);
												}
							
											});
								}

								@Override
								public void cancel() {}
								
							});
							w.show();
							w.center();
						}
					});
					return btn;
				}
			};
			addCC.setRenderer(addCellRender);

			ColumnModel cm = new ColumnModel(configs);
			EditorGrid<UserInfo> grid = new EditorGrid<UserInfo>(userStore, cm);
			grid.setHideHeaders(true);
			searchContentpanel.add(grid);
		}

		ToolBar toolBar_1 = new ToolBar();

		Text text = new Text("筛选条件：");
		toolBar_1.add(text);

		StoreFilterField<UserInfo> nameFilter = new StoreFilterField<UserInfo>() {
			@Override
			protected boolean doSelect(Store<UserInfo> store, UserInfo parent,
					UserInfo record, String property, String filter) {
				if (record.getName().contains(filter)) {
					return true;
				} else {
					return false;
				}
			}

		};
		nameFilter.bind(userStore);
		toolBar_1.add(nameFilter);
		nameFilter.setEmptyText("姓名");

		searchContentpanel.setTopComponent(toolBar_1);
		searchTabitem.add(searchContentpanel);
		tabPanel.add(searchTabitem);
		
		TabItem applyTabitem = new TabItem("邀请消息");
		applyTabitem.setLayout(new FitLayout());
		
		ContentPanel cntntpnlNewContentpanel = new ContentPanel();
		cntntpnlNewContentpanel.setHeaderVisible(false);
		cntntpnlNewContentpanel.setHeading("New ContentPanel");
		cntntpnlNewContentpanel.setLayout(new FitLayout());
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		
		{
			ColumnConfig columnConfig = new ColumnConfig("invitor", "", 100);
			configs.add(columnConfig);
			GridCellRenderer<FriendInfo> nameCellRender = new GridCellRenderer<FriendInfo>() {
				@Override
				public Object render(FriendInfo model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<FriendInfo> store, Grid<FriendInfo> grid) {

					return model.getSender().getName();
				}
			};
			columnConfig.setRenderer(nameCellRender);
			
			ColumnConfig columnConfig_1 = new ColumnConfig("info", "", 150);
			configs.add(columnConfig_1);
			GridCellRenderer<FriendInfo> infoCellRender = new GridCellRenderer<FriendInfo>() {
				@Override
				public Object render(FriendInfo model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<FriendInfo> store, Grid<FriendInfo> grid) {

					return "收到来至"+model.getSender().getName()+"的好友请求";
				}
			};
			columnConfig_1.setRenderer(infoCellRender);
			
			ColumnConfig columnConfig_2 = new ColumnConfig("yes", "", 50);
			configs.add(columnConfig_2);
			GridCellRenderer<FriendInfo> yesCellRender = new GridCellRenderer<FriendInfo>() {
				@Override
				public Object render(FriendInfo model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<FriendInfo> store, Grid<FriendInfo> grid) {
					final Button btn = new Button("同意");
					btn.setToolTip("同意好友请求");
					final FriendInfo lModel = model;

					btn.addListener(Events.Select,new Listener<ButtonEvent>() {

								@Override
								public void handleEvent(ButtonEvent be) {
									btn.setEnabled(false);
									EnvConfig.getPineconeService().acceptFriendInvite(lModel, new AsyncCallback<Void>() {

										@Override
										public void onFailure(Throwable caught) {
											MessageBox.info("错误","调用后台服务接受好友失败",null);
											btn.setEnabled(true);
										}

										@Override
										public void onSuccess(Void result) {
											MessageBox.info("消息","同意好友申请",null);
											loadFriendInvite();
											loadFriends();
											btn.setEnabled(true);
										}

									});
								}

							});
					return btn;
				}
			};
			columnConfig_2.setRenderer(yesCellRender);
			
			ColumnConfig columnConfig_3 = new ColumnConfig("no", "", 50);
			configs.add(columnConfig_3);
			GridCellRenderer<FriendInfo> noCellRender = new GridCellRenderer<FriendInfo>() {
				@Override
				public Object render(FriendInfo model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<FriendInfo> store, Grid<FriendInfo> grid) {
					final Button btn = new Button("拒绝");
					btn.setToolTip("拒绝好友请求");
					final FriendInfo lModel = model;

					btn.addListener(Events.Select,new Listener<ButtonEvent>() {

								@Override
								public void handleEvent(ButtonEvent be) {
									btn.setEnabled(false);
									EnvConfig.getPineconeService().deleteFriend(EnvConfig.getLoginUser(), lModel, new AsyncCallback<Boolean>() {

										@Override
										public void onFailure(Throwable caught) {
											MessageBox.info("错误","调用后台服务删除好友失败",null);
											btn.setEnabled(true);
										}

										@Override
										public void onSuccess(Boolean result) {
											if(result){
												loadFriendInvite();
												loadFriends();
											}
											btn.setEnabled(true);
										}

									});
								}

							});
					return btn;
				}
			};
			columnConfig_3.setRenderer(noCellRender);
			
			ColumnModel cm = new ColumnModel(configs);
			Grid<FriendInfo> grid = new Grid<FriendInfo>(requestStore, cm);
			grid.setAutoExpandColumn("info");
//			grid.setHideHeaders(true);
			cntntpnlNewContentpanel.add(grid);
			applyTabitem.add(cntntpnlNewContentpanel);
			tabPanel.add(applyTabitem);
			add(tabPanel);
		}

		loadFriends();
		loadFriendInvite();

	}

	public void loadFriends() {
		EnvConfig.getPineconeService().getFriends(EnvConfig.getLoginUser(),
				new AsyncCallback<List<FriendInfo>>() {

					@Override
					public void onFailure(Throwable caught) {
						MessageBox.info("错误", "调用后台服务获取用户好友信息失败", null);
					}

					@Override
					public void onSuccess(List<FriendInfo> result) {
						friendStore.removeAll();
						friendStore.add(result);
						friendStore.groupBy("type", true);
						friendStore.commitChanges();

						EnvConfig.getPineconeService().getUsersByPage1(
								EnvConfig.getLoginUser(), 0, 100,
								new AsyncCallback<List<UserInfo>>() {

									@Override
									public void onFailure(Throwable caught) {
										MessageBox.info("错误",
												"调用后台服务获取所有用户信息失败", null);

									}

									@Override
									public void onSuccess(List<UserInfo> result) {
										userStore.removeAll();
										userStore.add(result);
										userStore.commitChanges();
									}

								});
					}

				});
	}
	
	public void loadFriendInvite(){
		EnvConfig.getPineconeService().getFriends(EnvConfig.getLoginUser(),
				new AsyncCallback<List<FriendInfo>>() {

					@Override
					public void onFailure(Throwable caught) {
						MessageBox.info("错误", "调用后台服务获取用户好友信息失败", null);
						
					}

					@Override
					public void onSuccess(List<FriendInfo> result) {
						requestStore.removeAll();
						for(FriendInfo fi:result){
							if(!fi.getIsDecided()){
								requestStore.add(fi);
							}
						}
						requestStore.commitChanges();
					}
			
		});
	}
}
