package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Text;
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
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.controllers.FriendController;
import com.tenline.pinecone.platform.web.store.client.controllers.UserController;
import com.tenline.pinecone.platform.web.store.client.events.FriendEvents;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class FriendViewport extends AbstractViewport {

	private MainPanel mainPanel;

	public FriendViewport() {
		mainPanel = new MainPanel();
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(10, 10, 10, 10));
		body.add(mainPanel, bld);
	}

	public void loadInfo() {
		// load friends
		AppEvent event = new AppEvent(FriendEvents.GET_BY_USER);
		Dispatcher.get().dispatch(event);

		// //load invitations
		// AppEvent event1 = new AppEvent(FriendEvents.GET_REQUESTS);
		// Dispatcher.get().dispatch(event1);
		//
		// //load users
		// AppEvent event2 = new AppEvent(UserEvents.GET_ALL_USER);
		// Dispatcher.get().dispatch(event2);
	}

	public void loadFriends(Collection<BeanModel> friends) {
		mainPanel.loadFriends(friends);

		// load invitations
		AppEvent event1 = new AppEvent(FriendEvents.GET_REQUESTS);
		Dispatcher.get().dispatch(event1);
	}

	public void loadFriendInvite(List<BeanModel> invitations) {
		mainPanel.loadFriendInvite(invitations);
		// load users
		AppEvent event2 = new AppEvent(UserEvents.GET_ALL_USER);
		Dispatcher.get().dispatch(event2);
	}

	public void loadAllUser(List<BeanModel> users) {
		mainPanel.loadAllUser(users);
	}

	private class MainPanel extends ContentPanel {

		private GroupingStore<BeanModel> friendStore = new GroupingStore<BeanModel>();
		private GroupingStore<BeanModel> userStore = new GroupingStore<BeanModel>();
		private ListStore<BeanModel> requestStore = new ListStore<BeanModel>();

		private List<String> types = new ArrayList<String>();
		
		private LayoutContainer container;
		private CardLayout layout = null;
		private ContentPanel myFriendContentpanel = null;
		private ContentPanel searchContentpanel = null;
		private ContentPanel invitationContentpanel= null;

		public MainPanel() {
			this.setHeaderVisible(false);
			setLayout(new BorderLayout());
			this.setBodyBorder(false);
			this.setBorders(false);
			this.setBodyStyleName("abstractviewport-background");

			LayoutContainer toolbarlc = new LayoutContainer();
			BorderLayoutData toolbarbld = new BorderLayoutData(LayoutRegion.WEST,100);
			toolbarbld.setMargins(new Margins(10, 10, 10, 10));
			this.add(toolbarlc, toolbarbld);
			toolbarlc.addStyleName("abstractviewport-background");
			
			VBoxLayout vbl_toolbarLayoutContainer = new VBoxLayout();
			vbl_toolbarLayoutContainer.setPack(BoxLayoutPack.START);
			vbl_toolbarLayoutContainer.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCHMAX);
			toolbarlc.setLayout(vbl_toolbarLayoutContainer);
			
			com.google.gwt.user.client.ui.Button btnGotoHome = 
				new com.google.gwt.user.client.ui.Button(
					((Messages) Registry.get(Messages.class.getName()))
							.HomeViewport_title());
			btnGotoHome.setHTML("<img class='btn-img-left' src='../images/icons/back.png'>"+
					((Messages) Registry.get(Messages.class.getName())).HomeViewport_title()+
					"</img>");
			btnGotoHome.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent event1 = new AppEvent(WidgetEvents.UPDATE_USERHOME_TO_PANEL);
					event1.setHistoryEvent(true);
					Dispatcher.get().dispatch(event1);
				}
			});
			btnGotoHome.setHeight("32px");
			btnGotoHome.setStyleName("abstractviewport-btn");
			toolbarlc.add(btnGotoHome);
			
			com.google.gwt.user.client.ui.Button btnMyFriend = 
				new com.google.gwt.user.client.ui.Button(
					((Messages) Registry.get(Messages.class.getName()))
							.FriendViewport_tabitem_myfriend());
			btnMyFriend.setHTML("<img class='btn-img-left' src='../images/icons/back.png'>"+
					((Messages) Registry.get(Messages.class.getName())).FriendViewport_tabitem_myfriend()+
					"</img>");
			btnMyFriend.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					if(!container.getItems().contains(createFriendsPanel())){
						container.add(createFriendsPanel(), new FitData(0,0,0,0));
					}
					
					layout.setActiveItem(createFriendsPanel());
				}
			});
			btnMyFriend.setHeight("32px");
			btnMyFriend.setStyleName("abstractviewport-btn");
			toolbarlc.add(btnMyFriend,new VBoxLayoutData(3, 0, 3, 0));
			
			com.google.gwt.user.client.ui.Button btnAllUser = 
				new com.google.gwt.user.client.ui.Button(
					((Messages) Registry.get(Messages.class.getName()))
							.FriendViewport_tabitem_searchfriend());
			btnAllUser.setHTML("<img class='btn-img-left' src='../images/icons/back.png'>"+
					((Messages) Registry.get(Messages.class.getName())).FriendViewport_tabitem_searchfriend()+
					"</img>");
			btnAllUser.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					if(!container.getItems().contains(createSearchUserPanel())){
						container.add(createSearchUserPanel(), new FitData(0,0,0,0));
					}
					
					layout.setActiveItem(createSearchUserPanel());
				}
			});
			btnAllUser.setHeight("32px");
			btnAllUser.setStyleName("abstractviewport-btn");
			toolbarlc.add(btnAllUser,new VBoxLayoutData(3, 0, 3, 0));
			
			com.google.gwt.user.client.ui.Button btnInvite = 
				new com.google.gwt.user.client.ui.Button(
					((Messages) Registry.get(Messages.class.getName()))
							.FriendViewport_tabitem_inviteMsg());
			btnInvite.setHTML("<img class='btn-img-left' src='../images/icons/back.png'>"+
					((Messages) Registry.get(Messages.class.getName())).FriendViewport_tabitem_inviteMsg()+
					"</img>");
			btnInvite.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					if(!container.getItems().contains(createInvitationPanel())){
						container.add(createInvitationPanel(), new FitData(0,0,0,0));
					}
					
					layout.setActiveItem(createInvitationPanel());
				}
			});
			btnInvite.setHeight("32px");
			btnInvite.setStyleName("abstractviewport-btn");
			toolbarlc.add(btnInvite,new VBoxLayoutData(3, 0, 3, 0));
			
			container = new LayoutContainer();
			container.setLayout(new BorderLayout());
			layout = new CardLayout();
			container.setLayout(layout);
			
			BorderLayoutData mainBld = new BorderLayoutData(LayoutRegion.CENTER);
			mainBld.setMargins(new Margins(10,10,10,10));
			this.add(container,mainBld);
			
			container.add(createFriendsPanel(), new FitData(0,0,0,0));
			createSearchUserPanel();
			createInvitationPanel();
			layout.setActiveItem(createFriendsPanel());
		}

		public ContentPanel createFriendsPanel() {
			
			String labelStyle = "color: #222;font-weight: bold;line-height:40px;";
			
			if(myFriendContentpanel == null){
				myFriendContentpanel = new ContentPanel();
				myFriendContentpanel.setHeaderVisible(false);
				myFriendContentpanel.setHeading("New ContentPanel");
				myFriendContentpanel.setCollapsible(true);
				myFriendContentpanel.setLayout(new FitLayout());
				myFriendContentpanel.setBodyBorder(false);
				myFriendContentpanel.setBorders(false);
				myFriendContentpanel.addStyleName("appstoreviewport-panel");
	
				{
					List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
	
					ColumnConfig nameCC = new ColumnConfig("name",
							((Messages) Registry.get(Messages.class.getName()))
									.FriendViewport_column_name(), 150);
					configs.add(nameCC);
					GridCellRenderer<BeanModel> nameCellRender = new GridCellRenderer<BeanModel>() {
						@Override
						public Object render(BeanModel model, String property,
								ColumnData config, int rowIndex, int colIndex,
								ListStore<BeanModel> store, Grid<BeanModel> grid) {
							User user = (User) model.get(FriendController.FRIEND_FRIEND);
							return user.getName();
						}
					};
					nameCC.setRenderer(nameCellRender);
					nameCC.setStyle(labelStyle);
	
					ColumnConfig mailCC = new ColumnConfig("email",
							((Messages) Registry.get(Messages.class.getName()))
									.FriendViewport_column_email(), 150);
					configs.add(mailCC);
					GridCellRenderer<BeanModel> mailCellRender = new GridCellRenderer<BeanModel>() {
						@Override
						public Object render(BeanModel model, String property,
								ColumnData config, int rowIndex, int colIndex,
								ListStore<BeanModel> store, Grid<BeanModel> grid) {
							User user = (User) model.get(FriendController.FRIEND_FRIEND);
							return user.getEmail();
						}
					};
					mailCC.setRenderer(mailCellRender);
					mailCC.setStyle(labelStyle);
	
					ColumnConfig statusCC = new ColumnConfig("isAccept",
							((Messages) Registry.get(Messages.class.getName()))
									.FriendViewport_column_isAccept(), 150);
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
					statusCC.setStyle(labelStyle);
	
					ColumnConfig deleteCC = new ColumnConfig("delete",
							((Messages) Registry.get(Messages.class.getName()))
									.FriendViewport_column_isDelete(), 150);
					configs.add(deleteCC);
					GridCellRenderer<BeanModel> delCellRender = new GridCellRenderer<BeanModel>() {
						@Override
						public Object render(BeanModel model, String property,
								ColumnData config, int rowIndex, int colIndex,
								ListStore<BeanModel> store, Grid<BeanModel> grid) {
							final Button btn = new Button(
									((Messages) Registry.get(Messages.class
											.getName()))
											.FriendViewport_button_Delete());
							btn.setTitle(((Messages) Registry.get(Messages.class
									.getName()))
									.FriendViewport_button_tooltip_Delete());
							btn.setStyleName("abstractviewport-btn");
							btn.setSize("60px", "30px");
							final BeanModel lModel = model;
	
							btn.addMouseUpHandler(new MouseUpHandler() {
								public void onMouseUp(MouseUpEvent e) {
											MessageBox.confirm(
													((Messages) Registry
															.get(Messages.class
																	.getName()))
															.FriendViewport_messagebox_title(),
													((Messages) Registry
															.get(Messages.class
																	.getName()))
															.FriendViewport_messagebox_info(),
													new Listener<MessageBoxEvent>() {
														@Override
														public void handleEvent(
																MessageBoxEvent be) {
															com.extjs.gxt.ui.client.widget.button.Button b = be
																	.getButtonClicked();
															if (Dialog.YES
																	.equalsIgnoreCase(b
																			.getItemId())) {
																AppEvent event = new AppEvent(
																		FriendEvents.DELETE);
																event.setData(
																		FriendController.FRIEND_INSTANCE,
																		lModel.get(FriendController.FRIEND_INSTANCE));
																event.setHistoryEvent(true);
																Dispatcher
																		.get()
																		.dispatch(
																				event);
															}
														}
													});
	
										}
	
									});
	
							return btn;
						}
					};
					deleteCC.setRenderer(delCellRender);
					deleteCC.setStyle(labelStyle);
	
					GroupingView view = new GroupingView();
					view.setForceFit(true);
					view.setShowGroupedColumn(false);
	
					final ColumnModel cm = new ColumnModel(configs);
					Grid<BeanModel> grid = new Grid<BeanModel>(friendStore, cm);
					grid.setView(view);
					grid.setHideHeaders(true);
					grid.setStripeRows(true);
					grid.setAutoExpandColumn("delete");
					
					myFriendContentpanel.add(grid);
					view.setGroupRenderer(new GridGroupRenderer() {
						public String render(GroupColumnData data) {
							types.add(data.group);
							return data.group + " (" + data.models.size()
									+ "items)";
						}
					});
				}
	
				ToolBar toolBar = new ToolBar();
				Text text = new Text(((Messages) Registry.get(Messages.class
						.getName())).FriendViewport_tabitem_myfriend());
				toolBar.add(text);
				toolBar.addStyleName("appstoreviewport-toolbar");
				text.addStyleName("friendviewport-toolbar-text");
				toolBar.setHeight("38px");
				toolBar.setBorders(false);
	
				FillToolItem fillToolItem = new FillToolItem();
				toolBar.add(fillToolItem);
				Text text1 = new Text(((Messages) Registry.get(Messages.class
						.getName())).FriendViewport_label_condition());
				toolBar.add(text1);
				text1.addStyleName("friendviewport-toolbar-text");
	
				myFriendContentpanel.setTopComponent(toolBar);
	
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
				filter.setEmptyText(((Messages) Registry.get(Messages.class
						.getName())).FriendViewport_emptytext());
				toolBar.add(filter);
			}
			
			return myFriendContentpanel;
		}

		public ContentPanel createSearchUserPanel() {
			
			String labelStyle = "color: #222;font-weight: bold;line-height:40px;";
			
			if(searchContentpanel == null){
				searchContentpanel = new ContentPanel();
				searchContentpanel.setHeaderVisible(false);
				searchContentpanel.setHeading("New ContentPanel");
				searchContentpanel.setCollapsible(true);
				searchContentpanel.setLayout(new FitLayout());
				searchContentpanel.setBodyBorder(false);
				searchContentpanel.setBorders(false);
				searchContentpanel.addStyleName("appstoreviewport-panel");
				{
					List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
	
					ColumnConfig nameCC = new ColumnConfig("name",
							((Messages) Registry.get(Messages.class.getName()))
									.FriendViewport_column_name(), 150);
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
					nameCC.setStyle(labelStyle);
	
					ColumnConfig mailCC = new ColumnConfig("email",
							((Messages) Registry.get(Messages.class.getName()))
									.FriendViewport_column_name(), 150);
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
					mailCC.setStyle(labelStyle);
	
					ColumnConfig configCC = new ColumnConfig("group",
							((Messages) Registry.get(Messages.class.getName()))
									.FriendViewport_column_group(), 150);
					configs.add(configCC);
	
					ColumnConfig addCC = new ColumnConfig("add",
							((Messages) Registry.get(Messages.class.getName()))
									.FriendViewport_column_add(), 150);
					configs.add(addCC);
					GridCellRenderer<BeanModel> addCellRender = new GridCellRenderer<BeanModel>() {
						@Override
						public Object render(BeanModel model, String property,
								ColumnData config, int rowIndex, int colIndex,
								ListStore<BeanModel> store, Grid<BeanModel> grid) {
							final Button btn = new Button();
							btn.setText(((Messages) Registry.get(Messages.class
									.getName())).FriendViewport_button_addFriend());
							btn.setTitle(((Messages) Registry.get(Messages.class
									.getName()))
									.FriendViewport_button_tooltip_addFriend());
							final BeanModel lModel = model;
							btn.setStyleName("abstractviewport-btn");
							btn.setSize("60px", "30px");
	
							btn.addMouseUpHandler(new MouseUpHandler() {
								@SuppressWarnings("rawtypes")
								public void onMouseUp(MouseUpEvent e) {
	
											FriendTypeWindow w = new FriendTypeWindow(
													new Listener() {
														@Override
														public void handleEvent(
																BaseEvent be) {
															AppEvent appEvent = new AppEvent(
																	FriendEvents.ADD);
															appEvent.setData(
																	FriendController.FRIEND_RECEIVER,
																	(User) (lModel.get(UserController.USER_INSTANCE)));
															appEvent.setData(
																	"type",
																	be.getSource()
																			.toString());
															appEvent.setHistoryEvent(true);
															Dispatcher
																	.get()
																	.dispatch(
																			appEvent);
														}
	
													});
	
											w.show();
											w.center();
										}
									});
							return btn;
						}
					};
					addCC.setRenderer(addCellRender);
					addCC.setStyle(labelStyle);
	
					ColumnModel cm = new ColumnModel(configs);
					Grid<BeanModel> grid = new Grid<BeanModel>(
							userStore, cm);
					grid.setStripeRows(true);
					grid.setHideHeaders(true);
					grid.setColumnReordering(true); 
					grid.setAutoExpandColumn("email");

					searchContentpanel.add(grid);
				}
	
				ToolBar toolBar_1 = new ToolBar();
				Text text = new Text(((Messages) Registry.get(Messages.class
						.getName())).FriendViewport_tabitem_searchfriend());
				toolBar_1.add(text);
				toolBar_1.addStyleName("appstoreviewport-toolbar");
				text.addStyleName("friendviewport-toolbar-text");
	
				FillToolItem fillToolItem_1 = new FillToolItem();
				toolBar_1.add(fillToolItem_1);
				Text text1 = new Text(((Messages) Registry.get(Messages.class
						.getName())).FriendViewport_label_condition());
				toolBar_1.add(text1);
				toolBar_1.setHeight("38px");
				text1.addStyleName("friendviewport-toolbar-text");
	
				StoreFilterField<BeanModel> nameFilter = new StoreFilterField<BeanModel>() {
					@Override
					protected boolean doSelect(Store<BeanModel> store,
							BeanModel parent, BeanModel record, String property,
							String filter) {
	
						if (record.get("name") != null) {
							if ((record.get("name").toString()).contains(filter)) {
								return true;
							}
						}
	
						return false;
					}
	
				};
				nameFilter.bind(userStore);
				toolBar_1.add(nameFilter);
				nameFilter.setEmptyText(((Messages) Registry.get(Messages.class
						.getName())).FriendViewport_emptytext());
	
				searchContentpanel.setTopComponent(toolBar_1);
			}
				
			return searchContentpanel;
		}

		public ContentPanel createInvitationPanel() {
			String labelStyle = "color: #222;font-weight: bold;line-height:40px;";
			
			if(invitationContentpanel == null){
				invitationContentpanel = new ContentPanel();
				invitationContentpanel.setHeaderVisible(false);
				invitationContentpanel.setHeading("New ContentPanel");
				invitationContentpanel.setLayout(new FitLayout());
				invitationContentpanel.setBodyBorder(false);
				invitationContentpanel.setBorders(false);
				invitationContentpanel.addStyleName("appstoreviewport-panel");
				List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
	
				{
					ColumnConfig columnConfig = new ColumnConfig("invitor",
							((Messages) Registry.get(Messages.class.getName()))
									.FriendViewport_column_invitor(), 100);
					configs.add(columnConfig);
					GridCellRenderer<BeanModel> nameCellRender = new GridCellRenderer<BeanModel>() {
						@Override
						public Object render(BeanModel model, String property,
								ColumnData config, int rowIndex, int colIndex,
								ListStore<BeanModel> store, Grid<BeanModel> grid) {
							User user = (User) model.get(FriendController.FRIEND_FRIEND);
							return user.getName();
						}
					};
					columnConfig.setRenderer(nameCellRender);
					columnConfig.setStyle(labelStyle);
	
					ColumnConfig columnConfig_1 = new ColumnConfig("info",
							((Messages) Registry.get(Messages.class.getName()))
									.FriendViewport_column_information(), 150);
					configs.add(columnConfig_1);
					GridCellRenderer<BeanModel> infoCellRender = new GridCellRenderer<BeanModel>() {
						@Override
						public Object render(BeanModel model, String property,
								ColumnData config, int rowIndex, int colIndex,
								ListStore<BeanModel> store, Grid<BeanModel> grid) {
							User user = (User) model.get(FriendController.FRIEND_FRIEND);
							return "Received" + user.getName() + "'s invitation";
						}
					};
					columnConfig_1.setRenderer(infoCellRender);
					columnConfig_1.setStyle(labelStyle);
	
					ColumnConfig columnConfig_2 = new ColumnConfig("yes", "", 100);
					configs.add(columnConfig_2);
					GridCellRenderer<BeanModel> yesCellRender = new GridCellRenderer<BeanModel>() {
						@Override
						public Object render(BeanModel model, String property,
								ColumnData config, int rowIndex, int colIndex,
								ListStore<BeanModel> store, Grid<BeanModel> grid) {
							final Button btn = new Button(
									((Messages) Registry.get(Messages.class
											.getName()))
											.FriendViewport_button_accept());
							btn.setTitle(((Messages) Registry.get(Messages.class
									.getName()))
									.FriendViewport_button_tooltip_accept());
							btn.setStyleName("abstractviewport-btn");
							btn.setSize("60px", "30px");
							final BeanModel lModel = model;
	
							btn.addMouseUpHandler(new MouseUpHandler() {
								public void onMouseUp(MouseUpEvent e) {
											Friend f = (Friend) lModel.get(FriendController.FRIEND_INSTANCE);
	
											AppEvent appEvent = new AppEvent(
													FriendEvents.SETTING);
											appEvent.setData("decided", true);
											appEvent.setData(FriendController.FRIEND_INSTANCE, f);
											appEvent.setHistoryEvent(true);
											Dispatcher.get().dispatch(appEvent);
										}
									});
							return btn;
						}
					};
					columnConfig_2.setRenderer(yesCellRender);
					columnConfig_2.setStyle(labelStyle);
	
					ColumnConfig columnConfig_3 = new ColumnConfig("no",
							((Messages) Registry.get(Messages.class.getName()))
									.FriendViewport_button_reject(), 100);
					configs.add(columnConfig_3);
					GridCellRenderer<BeanModel> noCellRender = new GridCellRenderer<BeanModel>() {
						@Override
						public Object render(BeanModel model, String property,
								ColumnData config, int rowIndex, int colIndex,
								ListStore<BeanModel> store, Grid<BeanModel> grid) {
							final Button btn = new Button(
									((Messages) Registry.get(Messages.class
											.getName()))
											.FriendViewport_button_reject());
							btn.setTitle(((Messages) Registry.get(Messages.class
									.getName()))
									.FriendViewport_button_tooltip_reject());
							btn.setStyleName("abstractviewport-btn");
							btn.setSize("60px", "30px");
							final BeanModel lModel = model;
	
							btn.addMouseUpHandler(new MouseUpHandler() {
								public void onMouseUp(MouseUpEvent e) {
											Friend f = (Friend) lModel
													.get(FriendController.FRIEND_INSTANCE);
											AppEvent appEvent = new AppEvent(
													FriendEvents.DELETE);
											appEvent.setData("id", f.getId());
											appEvent.setHistoryEvent(true);
											Dispatcher.get().dispatch(appEvent);
										}
									});
	
							return btn;
						}
					};
					columnConfig_3.setRenderer(noCellRender);
					columnConfig_3.setStyle(labelStyle);
				}
	
				ColumnModel cm = new ColumnModel(configs);
				Grid<BeanModel> grid = new Grid<BeanModel>(requestStore, cm);
				grid.setAutoExpandColumn("info");
				grid.setStripeRows(true);
				grid.setHideHeaders(true);
//				grid.setColumnReordering(true);

				invitationContentpanel.add(grid);
	
				ToolBar toolBar_2 = new ToolBar();
				Text text = new Text(((Messages) Registry.get(Messages.class.getName())).FriendViewport_tabitem_inviteMsg());
				toolBar_2.add(text);
				toolBar_2.setHeight("38px");
				toolBar_2.addStyleName("appstoreviewport-toolbar");
				text.addStyleName("friendviewport-toolbar-text");
	
				FillToolItem fillToolItem_2 = new FillToolItem();
				toolBar_2.add(fillToolItem_2);
	
				invitationContentpanel.setTopComponent(toolBar_2);
			}
			return invitationContentpanel;

		}

		public void loadFriends(Collection<BeanModel> friends) {
			friendStore.removeAll();
			System.out.println("FriendViewport friendSize:"+friends.size());
			friendStore.add(new ArrayList<BeanModel>(friends));
			friendStore.commitChanges();
		}

		public void loadFriendInvite(List<BeanModel> friends) {
			System.out.println("FriendViewport loadFriendInvite size:"+friends.size());
			requestStore.removeAll();
			requestStore.add(friends);
			requestStore.commitChanges();
		}

		public void loadAllUser(List<BeanModel> users) {
			userStore.removeAll();
			userStore.add(users);
			userStore.commitChanges();
		}
	}
}
