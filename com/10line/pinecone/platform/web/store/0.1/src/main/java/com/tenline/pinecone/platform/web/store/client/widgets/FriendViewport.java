package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.TabPanel.TabPosition;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
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
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.services.FriendService;
import com.tenline.pinecone.platform.web.store.client.services.FriendServiceAsync;

public class FriendViewport extends AbstractViewport {
	
	
	public FriendViewport(){
		MainPanel mainPanel = new MainPanel();
		body.add(mainPanel,  new BorderLayoutData(LayoutRegion.CENTER));
		
	}

	
	
	private class MainPanel extends ContentPanel{
		
		private GroupingStore<BeanModel> friendStore = new GroupingStore<BeanModel>();
		private GroupingStore<BeanModel> userStore = new GroupingStore<BeanModel>();
		private ListStore<BeanModel> requestStore = new ListStore<BeanModel>();
		
		private BeanModelFactory friendFactory = BeanModelLookup.get().getFactory(Friend.class);
		private BeanModelFactory userFactory = BeanModelLookup.get().getFactory(User.class);
		private FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		
		private User curUser = null;
		
		private List<String> types = new ArrayList<String>();
		
		public MainPanel(){
			setLayout(new BorderLayout());
			
			LayoutContainer layoutContainer = new LayoutContainer();
			layoutContainer.setBorders(true);
			layoutContainer.setLayout(new BorderLayout());
			
			LayoutContainer layoutContainer_4 = new LayoutContainer();
			layoutContainer_4.setLayout(new FitLayout());
			
			Image image = ((Images) Registry.get(Images.class.getName())).logo().createImage();
			layoutContainer_4.add(image, new FitData(20, 8, 20, 8));
			layoutContainer.add(layoutContainer_4, new BorderLayoutData(LayoutRegion.WEST));
			add(layoutContainer, new BorderLayoutData(LayoutRegion.NORTH, 100.0f));

			TabPanel tabPanel = new TabPanel();
			tabPanel.setTabPosition(TabPosition.BOTTOM);

			/*
			 * 显示我的好友
			 */
			TabItem myFriendTabitem = new TabItem("My Friends");
			myFriendTabitem.setLayout(new FitLayout());

			ContentPanel myFriendContentpanel = new ContentPanel();
			myFriendContentpanel.setHeaderVisible(false);
			myFriendContentpanel.setHeading("New ContentPanel");
			myFriendContentpanel.setCollapsible(true);
			myFriendContentpanel.setLayout(new FitLayout());

			{
				List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

				ColumnConfig nameCC = new ColumnConfig("name", "Name", 150);
				configs.add(nameCC);
				GridCellRenderer<BeanModel> nameCellRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						User user = (User)model.get("friend");
						return user.getName();
					}
				};
				nameCC.setRenderer(nameCellRender);

				ColumnConfig mailCC = new ColumnConfig("email", "Email", 150);
				configs.add(mailCC);
				GridCellRenderer<BeanModel> mailCellRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						User user = (User)model.get("friend");
						return user.getEmail();
					}
				};
				mailCC.setRenderer(mailCellRender);

				ColumnConfig statusCC = new ColumnConfig("isAccept", "isAccept", 150);
				configs.add(statusCC);
				GridCellRenderer<BeanModel> statuslCellRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						return model.get("isDecided");
					}
				};
				statusCC.setRenderer(statuslCellRender);

				ColumnConfig deleteCC = new ColumnConfig("delete", "Delete?", 150);
				configs.add(deleteCC);
				GridCellRenderer<BeanModel> delCellRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						final Button btn = new Button("delete");
						btn.setToolTip("Delete the friend");
						final BeanModel lModel = model;

						btn.addListener(Events.Select,new Listener<ButtonEvent>() {

							@Override
							public void handleEvent(ButtonEvent be) {
								btn.setEnabled(false);
								friendStore.remove(lModel);
								friendStore.commitChanges();
								btn.setEnabled(true);
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
				Grid<BeanModel> grid = new Grid<BeanModel>(friendStore, cm);
				grid.setView(view);
				grid.setHideHeaders(false);
				grid.setStripeRows(true);
				myFriendContentpanel.add(grid);
				view.setGroupRenderer(new GridGroupRenderer() {
					public String render(GroupColumnData data) {
						types.add(data.group);
						return data.group + " (" + data.models.size() + "items)";
					}
				});
			}

			ToolBar toolBar = new ToolBar();

			Text text1 = new Text("condition:");
			toolBar.add(text1);
			myFriendContentpanel.setTopComponent(toolBar);
			myFriendTabitem.add(myFriendContentpanel);
			tabPanel.add(myFriendTabitem);

			StoreFilterField<BeanModel> filter = new StoreFilterField<BeanModel>() {

				@Override
				protected boolean doSelect(Store<BeanModel> store,
						BeanModel parent, BeanModel record, String property,
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
			filter.setEmptyText("Friend's name");
			toolBar.add(filter);

			/*
			 * 搜索好友
			 */
			TabItem searchTabitem = new TabItem("Search friends");
			searchTabitem.setLayout(new FitLayout());

			ContentPanel searchContentpanel = new ContentPanel();
			searchContentpanel.setHeaderVisible(false);
			searchContentpanel.setHeading("New ContentPanel");
			searchContentpanel.setCollapsible(true);
			searchContentpanel.setLayout(new FitLayout());
			{
				List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

				ColumnConfig nameCC = new ColumnConfig("name", "Name", 150);
				configs.add(nameCC);
				GridCellRenderer<BeanModel> nameCellRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						return model.get("name");
					}
				};
				nameCC.setRenderer(nameCellRender);

				ColumnConfig mailCC = new ColumnConfig("email", "Email", 150);
				configs.add(mailCC);
				GridCellRenderer<BeanModel> mailCellRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						return model.get("email");
					}
				};
				mailCC.setRenderer(mailCellRender);

				ColumnConfig configCC = new ColumnConfig("group", "group", 150);
				configs.add(configCC);

				ColumnConfig addCC = new ColumnConfig("add", "add", 150);
				configs.add(addCC);
				GridCellRenderer<BeanModel> addCellRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						final Button btn = new Button();
						btn.setText("Add friend");
						btn.setToolTip("Add friend");
						final BeanModel lModel = model;

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

										Friend f = new Friend();
										f.setDecided(false);
										f.setReceiver((User)(lModel.get("user")));
										f.setSender(curUser);
										friendService.create(f, new AsyncCallback<Friend>(){

											@Override
											public void onFailure(Throwable arg0) {
												arg0.printStackTrace();
												Info.display("Error",arg0.getMessage());
												btn.setEnabled(true);
											}

											@Override
											public void onSuccess(Friend arg0) {
//												AppEvent appEvent = new AppEvent(FriendViewEvents.LOAD_USER);
//												appEvent.setData("curUser", curUser);
//												Dispatcher.get().dispatch(appEvent);
												btn.setEnabled(true);
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
				EditorGrid<BeanModel> grid = new EditorGrid<BeanModel>(userStore, cm);
				grid.setHideHeaders(true);
				searchContentpanel.add(grid);
			}

			ToolBar toolBar_1 = new ToolBar();

			Text text = new Text("Condition:");
			toolBar_1.add(text);

			StoreFilterField<BeanModel> nameFilter = new StoreFilterField<BeanModel>() {
				@Override
				protected boolean doSelect(Store<BeanModel> store, BeanModel parent,
						BeanModel record, String property, String filter) {
					if(((String)record.get("name")).contains(filter)) {
						return true;
					} else {
						return false;
					}
				}

			};
			nameFilter.bind(userStore);
			toolBar_1.add(nameFilter);
			nameFilter.setEmptyText("Friend name");

			searchContentpanel.setTopComponent(toolBar_1);
			searchTabitem.add(searchContentpanel);
			tabPanel.add(searchTabitem);
			
			TabItem applyTabitem = new TabItem("Inviting message");
			applyTabitem.setLayout(new FitLayout());
			
			ContentPanel cntntpnlNewContentpanel = new ContentPanel();
			cntntpnlNewContentpanel.setHeaderVisible(false);
			cntntpnlNewContentpanel.setHeading("New ContentPanel");
			cntntpnlNewContentpanel.setLayout(new FitLayout());
			List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
			
			{
				ColumnConfig columnConfig = new ColumnConfig("invitor", "Invitor", 100);
				configs.add(columnConfig);
				GridCellRenderer<BeanModel> nameCellRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						User user = (User)model.get("invite");
						return user.getName();
					}
				};
				columnConfig.setRenderer(nameCellRender);
				
				ColumnConfig columnConfig_1 = new ColumnConfig("info", "info", 150);
				configs.add(columnConfig_1);
				GridCellRenderer<BeanModel> infoCellRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						User user = (User)model.get("invite");
						return "Received"+user.getName()+"'s invitation";
					}
				};
				columnConfig_1.setRenderer(infoCellRender);
				
				ColumnConfig columnConfig_2 = new ColumnConfig("yes", "", 50);
				configs.add(columnConfig_2);
				GridCellRenderer<BeanModel> yesCellRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						final Button btn = new Button("Accept");
						btn.setToolTip("Accept to be friend");
						final BeanModel lModel = model;

						btn.addListener(Events.Select,new Listener<ButtonEvent>() {

									@Override
									public void handleEvent(ButtonEvent be) {
										btn.setEnabled(false);
										Friend f = (Friend)lModel.get("friend");
										f.setDecided(true);
										friendService.update(f, new AsyncCallback<Friend>(){

											@Override
											public void onFailure(Throwable arg0) {
												arg0.printStackTrace();
												Info.display("Error",arg0.getMessage());
												btn.setEnabled(true);
											}

											@Override
											public void onSuccess(Friend arg0) {
//												AppEvent appEvent = new AppEvent(FriendViewEvents.INIT_FRIEND_VIEW);
//												appEvent.setData("curUser", curUser);
//												Dispatcher.get().dispatch(appEvent);
//												btn.setEnabled(true);
											}
										});
									}

								});
						return btn;
					}
				};
				columnConfig_2.setRenderer(yesCellRender);
				
				ColumnConfig columnConfig_3 = new ColumnConfig("no", "Rejection", 50);
				configs.add(columnConfig_3);
				GridCellRenderer<BeanModel> noCellRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						final Button btn = new Button("Reject");
						btn.setToolTip("Reject friend invitation!");
						final BeanModel lModel = model;

						btn.addListener(Events.Select,new Listener<ButtonEvent>() {

									@Override
									public void handleEvent(ButtonEvent be) {
										btn.setEnabled(false);
										Friend f = (Friend)lModel.get("friend");
										friendService.delete(f.getId(), new AsyncCallback<Boolean>(){

											@Override
											public void onFailure(Throwable arg0) {
												arg0.printStackTrace();
												Info.display("Error",arg0.getMessage());
												btn.setEnabled(true);
											}

											@Override
											public void onSuccess(Boolean arg0) {
//												AppEvent appEvent = new AppEvent(FriendViewEvents.LOAD_INVITATION);
//												appEvent.setData("curUser", curUser);
//												Dispatcher.get().dispatch(appEvent);
//												btn.setEnabled(true);
											}
										});
									}

								});
						return btn;
					}
				};
				columnConfig_3.setRenderer(noCellRender);
				
				ColumnModel cm = new ColumnModel(configs);
				Grid<BeanModel> grid = new Grid<BeanModel>(requestStore, cm);
				grid.setAutoExpandColumn("info");
//				grid.setHideHeaders(true);
				cntntpnlNewContentpanel.add(grid);
				applyTabitem.add(cntntpnlNewContentpanel);
				tabPanel.add(applyTabitem);
				
				LayoutContainer layoutContainer_2 = new LayoutContainer();
				layoutContainer_2.setLayout(new FitLayout());
				
				layoutContainer_2.add(tabPanel);
				add(layoutContainer_2, new BorderLayoutData(LayoutRegion.CENTER));
				layoutContainer_2.setBorders(false);
			}
		}
		
		public void loadInfo(User user){
			curUser = user;

		}
		
		public void loadFriends(Collection<Friend> friends) {
			friendStore.removeAll();
			
			//load user's friends
			friendStore.removeAll();
			for(Friend f:friends){
				BeanModel bm = friendFactory.createModel(f);
				if(f.getReceiver().getId().equals(curUser.getId())){
					bm.set("friend", f.getSender());
				}else{
					bm.set("friend", f.getReceiver());
				}
				friendStore.add(bm);
			}
			
			friendStore.commitChanges();
		}
		
		public void loadFriendInvite(Collection<Friend> friends){
			requestStore.removeAll();
			
			for(Friend f:friends){
				if(f.isDecided()){
					continue;
				}
				
				BeanModel bm = friendFactory.createModel(f);
				bm.set("invite", f.getSender());
				bm.set("friend", f);
				
				requestStore.add(bm);
			}
			
			requestStore.commitChanges();
		}
		
		public void loadAllUser(Collection<User> users){
			userStore.removeAll();
			for(User u:users){
				BeanModel bm = userFactory.createModel(u);
				bm.set("user", u);
				
				userStore.add(bm);
			}
			userStore.commitChanges();
		}
	}
}
