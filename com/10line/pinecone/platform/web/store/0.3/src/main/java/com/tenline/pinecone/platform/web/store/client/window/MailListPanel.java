package com.tenline.pinecone.platform.web.store.client.window;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.tenline.pinecone.platform.web.store.shared.MailInfo;

public class MailListPanel extends ContentPanel {
	
	private Grid<MailInfo> grid;
	private ListStore<MailInfo> listStore = new ListStore<MailInfo>();
	public static String READ = "已读";
	public static String UNREAD = "未读";
	
	public MailListPanel() {
		setHeaderVisible(false);
		setLayout(new FitLayout());
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		
		GridCellRenderer<MailInfo> statusRender = new GridCellRenderer<MailInfo>() {
			@Override
			public Object render(MailInfo model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<MailInfo> store, Grid<MailInfo> grid) {
				if(model.getIsRead()){
					return READ;
				}else{
					return UNREAD;
				}
			}

		};
		ColumnConfig columnConfig = new ColumnConfig("isRead", "状态", 60);
		configs.add(columnConfig);
		columnConfig.setRenderer(statusRender);
		
		GridCellRenderer<MailInfo> senderRender = new GridCellRenderer<MailInfo>() {
			@Override
			public Object render(MailInfo model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<MailInfo> store, Grid<MailInfo> grid) {
				return model.getSender().getName();
			}

		};
		ColumnConfig clmncnfgNewColumn = new ColumnConfig("sender", "发件人", 80);
		configs.add(clmncnfgNewColumn);
		clmncnfgNewColumn.setRenderer(senderRender);
		
		GridCellRenderer<MailInfo> titleRender = new GridCellRenderer<MailInfo>() {
			@Override
			public Object render(MailInfo model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<MailInfo> store, Grid<MailInfo> grid) {
				return model.getTitle();
			}

		};
		ColumnConfig clmncnfgNewColumn_1 = new ColumnConfig("title", "邮件标题", 100);
		configs.add(clmncnfgNewColumn_1);
		clmncnfgNewColumn_1.setRenderer(titleRender);
		
		GridCellRenderer<MailInfo> contentRender = new GridCellRenderer<MailInfo>() {
			@Override
			public Object render(MailInfo model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<MailInfo> store, Grid<MailInfo> grid) {
				return Format.ellipse(model.getContent(),30);
			}
		};
		ColumnConfig clmncnfgNewColumn_2 = new ColumnConfig("content", "内容", 200);
		configs.add(clmncnfgNewColumn_2);
		clmncnfgNewColumn_2.setRenderer(contentRender);
		
		ColumnModel columnModel = new ColumnModel(configs); 
		grid = new Grid<MailInfo>(listStore, columnModel);
		grid.addListener(Events.RowDoubleClick, new Listener<GridEvent>() {
			public void handleEvent(GridEvent e) {
				
				if(e.getModel() instanceof MailInfo){
					MailReadWindow w = new MailReadWindow();
					w.setMail((MailInfo)e.getModel());
					w.show();
					w.center();
				}
				
			}
		});
		add(grid);
		grid.setBorders(true);
		
	}
	
	public void loadMails(List<MailInfo> list){
		listStore.removeAll();
		listStore.add(list);
		listStore.commitChanges();	
	}
}
