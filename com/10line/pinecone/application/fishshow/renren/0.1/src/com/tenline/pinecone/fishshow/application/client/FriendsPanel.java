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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.fishshow.application.shared.renren.Friend;

public class FriendsPanel extends ContentPanel {
	private String sessionKey = "";
	
	private Grid<Friend> grid;
	private ColumnModel columnModel;
	private ListStore<Friend> listStore;
	
	public FriendsPanel() {
		setLayout(new BorderLayout());
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		
		ColumnConfig clmncnfgNewColumn = new ColumnConfig("headurl", "头像", 70);
		clmncnfgNewColumn.setRenderer(new GridCellRenderer<Friend>(){
			@Override
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
		add(grid, new BorderLayoutData(LayoutRegion.CENTER));
		grid.setBorders(true);
		grid.setAutoExpandColumn("name");
		
		ToolButton tBtn = new ToolButton("x-tool-gear");
		tBtn.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				
			}
		});
		this.getHeader().addTool(tBtn);
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
