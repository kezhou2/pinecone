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
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.TabPanel.TabPosition;
import com.extjs.gxt.ui.client.widget.Text;
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
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.events.FriendEvents;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class FriendViewport extends AbstractViewport {
	
	private MainPanel mainPanel;
	
	public FriendViewport(){
		mainPanel = new MainPanel();
		body.add(mainPanel,  new BorderLayoutData(LayoutRegion.CENTER));
	}
	
	public void loadInfo(){
		//load friends
		AppEvent event = new AppEvent(FriendEvents.GET_BY_USER);
		Dispatcher.get().dispatch(event);
		
		//load invitations
		AppEvent event1 = new AppEvent(FriendEvents.GET_REQUESTS);
		Dispatcher.get().dispatch(event1);
		
		//load users
		AppEvent event2 = new AppEvent(UserEvents.GET_ALL_USER);
		Dispatcher.get().dispatch(event2);
	}
	
	public void loadFriends(Collection<Friend> friends) {
//		for(Friend f:friends){
//			System.out.println("@@@Type"+f.getType());
//			System.out.println("@@@receiver Name:"+f.getReceiver().getName());
//			System.out.println("@@@receiver Email:"+f.getReceiver().getEmail());
//			System.out.println("@@@receiver Password:"+f.getReceiver().getPassword());
//		}
		mainPanel.loadFriends(friends);
	}
	
	public void loadFriendInvite(Collection<Friend> invitations){
		mainPanel.loadFriendInvite(invitations);
	}
	
	public void loadAllUser(Collection<User> users){
		mainPanel.loadAllUser(users);
	}
	
	private class MainPanel extends ContentPanel{
		
		private GroupingStore<BeanModel> friendStore = new GroupingStore<BeanModel>();
		private GroupingStore<BeanModel> userStore = new GroupingStore<BeanModel>();
		private ListStore<BeanModel> requestStore = new ListStore<BeanModel>();
		
		private BeanModelFactory friendFactory = BeanModelLookup.get().getFactory(Friend.class);
		private BeanModelFactory userFactory = BeanModelLookup.get().getFactory(User.class);
		
		private List<String> types = new ArrayList<String>();
		
		public MainPanel(){
			this.setHeaderVisible(false);
			setLayout(new BorderLayout());

			TabPanel tabPanel = new TabPanel();
			tabPanel.setTabPosition(TabPosition.BOTTOM);

			/***********************************************
			 * show all friends
			 ***********************************************/
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
						System.out.println("FriendViewport name:"+user.getName());
						return user.getName();
//						return "dd";
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
						System.out.println(model.get("friend"));
						System.out.println("FriendViewport email:"+user.getEmail());
						return user.getEmail();
//						return "sd";
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
						System.out.println("FriendViewport isDecided:"+model.get("isDecided"));
						return model.get("isDecided");
//						return "sd";
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
								MessageBox.confirm("Question", "Are you sure?", new Listener<MessageBoxEvent>(){
									@Override
									public void handleEvent(MessageBoxEvent be) {
										Button btn =be.getButtonClicked();
										if(Dialog.YES.equalsIgnoreCase(btn.getItemId())){
											AppEvent event = new AppEvent(FriendEvents.DELETE);
											event.setData("id", lModel.get("id"));
											Dispatcher.get().dispatch(event);
										}
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
			Button btnGotoHome = new Button("Go to Home");
			btnGotoHome.addListener(Events.Select,new Listener<ButtonEvent>() {

				@Override
				public void handleEvent(ButtonEvent be) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_USERHOME_TO_PANEL);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			toolBar.add(btnGotoHome);
	
			FillToolItem fillToolItem = new FillToolItem();
			toolBar.add(fillToolItem);
			Text text1 = new Text("Condition:");
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

			/*********************************************************
			 * search friends
			 *********************************************************/
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
								FriendTypeWindow w = new FriendTypeWindow(new CallbackInter(){
									@Override
									public void error() {}

									@Override
									public void success(String type) {
										AppEvent appEvent = new AppEvent(FriendEvents.ADD);
										appEvent.setData("receiver", (User)(lModel.get("user")));
										appEvent.setData("type", type);
										Dispatcher.get().dispatch(appEvent);
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
			Button btnGotoHome_1 = new Button("Go to Home");
			btnGotoHome_1.addListener(Events.Select,new Listener<ButtonEvent>() {

				@Override
				public void handleEvent(ButtonEvent be) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_USERHOME_TO_PANEL);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			toolBar_1.add(btnGotoHome_1);
			
			FillToolItem fillToolItem_1 = new FillToolItem();
			toolBar_1.add(fillToolItem_1);
			Text text = new Text("Condition:");
			toolBar_1.add(text);

			StoreFilterField<BeanModel> nameFilter = new StoreFilterField<BeanModel>() {
				@Override
				protected boolean doSelect(Store<BeanModel> store, BeanModel parent,
						BeanModel record, String property, String filter) {
					
					System.out.println("Search filter:"+store.getModels().size());
//					System.out.println("&&&&*****user Name:"+record.get("name"));
//					System.out.println("&&&&*****user email:"+record.get("email"));
//					System.out.println("&&&&*****user password:"+record.get("password"));
//					return false;
					if(record.get("name") != null){
						if((record.get("name").toString()).contains(filter)) {
							return true;
						}
					}
					
					return false;
				}

			};
			nameFilter.bind(userStore);
			toolBar_1.add(nameFilter);
			nameFilter.setEmptyText("Friend name");

			searchContentpanel.setTopComponent(toolBar_1);
			searchTabitem.add(searchContentpanel);
			tabPanel.add(searchTabitem);
			/**************************************************
			 * show friend invitation
			 **************************************************/
			TabItem applyTabitem = new TabItem("Inviting message");
			applyTabitem.setLayout(new FitLayout());
			
			ContentPanel invitationContentpanel = new ContentPanel();
			invitationContentpanel.setHeaderVisible(false);
			invitationContentpanel.setHeading("New ContentPanel");
			invitationContentpanel.setLayout(new FitLayout());
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
								Friend f = (Friend)lModel.get("friend");

								AppEvent appEvent = new AppEvent(FriendEvents.SETTING);
								appEvent.setData("isDecided", true);
								appEvent.setData("friend", f);
								Dispatcher.get().dispatch(appEvent);
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
								Friend f = (Friend)lModel.get("friend");
								AppEvent appEvent = new AppEvent(FriendEvents.DELETE);
								appEvent.setData("id", f.getId());
								Dispatcher.get().dispatch(appEvent);
							}
						});
						
						return btn;
					}
				};
				columnConfig_3.setRenderer(noCellRender);
				
				ColumnModel cm = new ColumnModel(configs);
				Grid<BeanModel> grid = new Grid<BeanModel>(requestStore, cm);
				grid.setAutoExpandColumn("info");
				invitationContentpanel.add(grid);
				
				ToolBar toolBar_2 = new ToolBar();
				Button btnGotoHome_2 = new Button("Go to Home");
				btnGotoHome_2.addListener(Events.Select,new Listener<ButtonEvent>() {

					@Override
					public void handleEvent(ButtonEvent be) {
						AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_USERHOME_TO_PANEL);
						Dispatcher.get().dispatch(appEvent);
					}

				});
				toolBar_2.add(btnGotoHome_2);
				
				FillToolItem fillToolItem_2 = new FillToolItem();
				toolBar_2.add(fillToolItem_2);
				
				invitationContentpanel.setTopComponent(toolBar_2);
				
				applyTabitem.add(invitationContentpanel);
				tabPanel.add(applyTabitem);
				
			}
			LayoutContainer layoutContainer_2 = new LayoutContainer();
			layoutContainer_2.setLayout(new FitLayout());
				
			layoutContainer_2.add(tabPanel);
			add(layoutContainer_2, new BorderLayoutData(LayoutRegion.CENTER));
			layoutContainer_2.setBorders(false);

		}
		
		public void loadFriends(Collection<Friend> friends) {
			if(Registry.get(User.class.getName()) != null){
				User user = (User)Registry.get(User.class.getName());
				//load user's friends
				friendStore.removeAll();
				for(Friend f:friends){
//					System.out.println("@@@Type"+f.getType());
//					System.out.println("@@@receiver Name:"+f.getReceiver().getName());
//					System.out.println("@@@receiver Email:"+f.getReceiver().getEmail());
//					System.out.println("@@@receiver Password:"+f.getReceiver().getPassword());
					BeanModel bm = friendFactory.createModel(f);
//					System.out.println("&&&&&&Type:"+bm.get("type"));
					if(f.getReceiver().getId().equals(user.getId())){
						bm.set("friend", f.getSender());
//						BeanModel b = userFactory.createModel(f.getSender());
//						bm.set("friendBM", b);
					}else{
						bm.set("friend", f.getReceiver());
//						BeanModel b = userFactory.createModel(f.getSender());
//						bm.set("friendBM", b);
					}
					
//					System.out.println("@@@Type"+f.getType());
//					System.out.println("@@@receiver Name:"+f.getReceiver().getName());
//					System.out.println("@@@receiver Email:"+f.getReceiver().getEmail());
//					System.out.println("@@@receiver Password:"+f.getReceiver().getPassword());
//					System.out.println("*****receiver Name:"+((User)bm.get("friend")).getName());
//					System.out.println("*****receiver email:"+((User)bm.get("friend")).getEmail());
//					System.out.println("*****receiver password:"+((User)bm.get("friend")).getPassword());
					friendStore.add(bm);
				}
				
				friendStore.commitChanges();
			}
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
