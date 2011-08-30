package com.tenline.pinecone.fishshow.application.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.fishshow.application.client.service.RenrenApi;
import com.tenline.pinecone.fishshow.application.client.service.RenrenApiAsync;
import com.tenline.pinecone.fishshow.application.shared.renren.Friend;
import com.extjs.gxt.ui.client.event.GridEvent;

public class FriendsPanel extends ContentPanel {
	private String sessionKey = "";
	
	private Grid<Friend> grid;
	private ColumnModel columnModel;
	private ListStore<Friend> listStore;
	private Friend selFriend = null;
	
	public FriendsPanel() {
		setLayout(new BorderLayout());
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		
		ColumnConfig clmncnfgNewColumn = new ColumnConfig("headurl", "头像", 70);
		clmncnfgNewColumn.setRenderer(new GridCellRenderer<Friend>(){

			public Object render(Friend model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Friend> store, Grid<Friend> grid) {
				String url = model.getTinyurl_with_logo();
				return "<image src='"+url+"'/>";
			}
		});
		
		configs.add(clmncnfgNewColumn);
		
		ColumnConfig clmncnfgNewColumn_1 = new ColumnConfig("name", "姓名", 150);
		configs.add(clmncnfgNewColumn_1);
		
		listStore = new ListStore<Friend>();
		columnModel = new ColumnModel(configs); 
		
		List<ColumnConfig> configs_1 = new ArrayList<ColumnConfig>();
		
		ColumnConfig clmncnfgNewColumn_2 = new ColumnConfig("id", "New Column", 150);
		configs_1.add(clmncnfgNewColumn_2);
		
		ColumnConfig clmncnfgNewColumn_3 = new ColumnConfig("id1", "New Column", 150);
		configs_1.add(clmncnfgNewColumn_3);
		
		grid = new Grid<Friend>(listStore, columnModel);
		grid.addListener(Events.RowClick, new Listener<GridEvent>() {
			public void handleEvent(GridEvent e) {
				FriendsPanel.this.selFriend = grid.getSelectionModel().getSelectedItem();
			}
		});
		add(grid, new BorderLayoutData(LayoutRegion.CENTER));
		grid.setBorders(true);
		grid.setAutoExpandColumn("name");
		
		Button crtBtn = new Button("<img src='../img/communication.png' />");
		crtBtn.setSize(18, 18);
		Menu menu = new Menu();
		menu.setToolTip("我的朋友圈");
		
		MenuItem inviteMenuItem = new MenuItem("邀请好友加入");
		inviteMenuItem.setIcon(EnvConfig.ICONS.mail12());
		menu.add(inviteMenuItem);
		MenuItem msgMenuItem = new MenuItem("给好友发送消息");
		menu.add(msgMenuItem);
		msgMenuItem.setIcon(EnvConfig.ICONS.message12());
		
		crtBtn.setMenu(menu);
		
		this.getHeader().addTool(crtBtn);
		
//		Button mailBtn = new Button("<img src='../img/message.png' />");
//		mailBtn.setSize(20,20);
////		mailBtn.setHeight(20);
////		mailBtn.setWidth(20);
//		mailBtn.setToolTip("给好友发送消息");
//		mailBtn.addListener(Events.Select, new Listener<ButtonEvent>() {
//			public void handleEvent(ButtonEvent e) {
//				
//			}
//		});
//		this.getHeader().addTool(mailBtn);
//		
//		Button inviteBtn = new Button("<img src='../img/mail.png' />");
//		inviteBtn.setSize(16,16);
//		inviteBtn.setToolTip("邀请好友加入");
//		inviteBtn.addListener(Events.Select, new Listener<ButtonEvent>() {
//			public void handleEvent(ButtonEvent e) {
//				
//			}
//		});
//		this.getHeader().addTool(inviteBtn);
	}
	
	public void refresh(){
		
	}
	
	public void setContent(String sKey){
		this.sessionKey = sKey;
		
		RpcProxy<List<Friend>> proxy = new RpcProxy<List<Friend>>(){

			@Override
			protected void load(Object loadConfig,
					AsyncCallback<List<Friend>> callback) {
				RenrenApiAsync service = GWT.create(RenrenApi.class);
				service.getAllFriends(sessionKey, callback);
				
			}
		};
		
		ListLoader<BaseListLoadResult<Friend>> loader = new BaseListLoader<BaseListLoadResult<Friend>>(proxy);
		
		listStore = new ListStore<Friend>(loader);
		
		grid.reconfigure(listStore, columnModel);
		loader.load();
	}
}
