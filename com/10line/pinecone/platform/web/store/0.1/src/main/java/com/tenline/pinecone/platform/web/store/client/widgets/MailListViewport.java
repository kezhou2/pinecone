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
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.MailEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class MailListViewport extends AbstractViewport{
	
	private MainPanel mainPanel;
	
	public MailListViewport() {
		mainPanel = new MainPanel();
		body.add(mainPanel, new BorderLayoutData(LayoutRegion.CENTER));
	}
	
	public void loadInfo(){
		AppEvent event = new AppEvent(MailEvents.GET_UNREAD);
		Dispatcher.get().dispatch(event);
	}
	
	public void loadMails(Collection<Mail> inMails){
		mainPanel.loadMails(inMails);
	}
	
	private class MainPanel extends ContentPanel{
		
		private Grid<BeanModel> inboxGrid;
		private Grid<BeanModel> outboxGrid;
		private ListStore<BeanModel> inListStore = new ListStore<BeanModel>();
		private ListStore<BeanModel> outListStore = new ListStore<BeanModel>();
		
		/**use to generate Mail BeanModel*/
		private BeanModelFactory mailFactory = BeanModelLookup.get().getFactory(Mail.class);
		
		public MainPanel(){
			setHeaderVisible(false);
			setLayout(new FitLayout());
			
			TabPanel tabPanel = new TabPanel();
			
			//inbox
			TabItem tbtmInbox = new TabItem(((Messages) Registry.get(Messages.class.getName())).MailListViewport_tabitem_inbox());
			tbtmInbox.setLayout(new FitLayout());
			tabPanel.add(tbtmInbox);
			add(tabPanel);
			
			{
				List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
				
				GridCellRenderer<BeanModel> statusRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						return model.get("isRead");
					}
		
				};
				ColumnConfig columnConfig = new ColumnConfig("isRead", ((Messages) Registry.get(Messages.class.getName())).MailListViewport_column_status(), 60);
				configs.add(columnConfig);
				columnConfig.setRenderer(statusRender);
				
				GridCellRenderer<BeanModel> senderRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						User sender = (User)model.get("sender");
						return sender.getName();
					}
		
				};
				ColumnConfig clmncnfgNewColumn = new ColumnConfig("sender", ((Messages) Registry.get(Messages.class.getName())).MailListViewport_column_sender(), 80);
				configs.add(clmncnfgNewColumn);
				clmncnfgNewColumn.setRenderer(senderRender);
				
				GridCellRenderer<BeanModel> titleRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						return model.get("title");
					}
		
				};
				ColumnConfig clmncnfgNewColumn_1 = new ColumnConfig("title", ((Messages) Registry.get(Messages.class.getName())).MailListViewport_column_title(), 100);
				configs.add(clmncnfgNewColumn_1);
				clmncnfgNewColumn_1.setRenderer(titleRender);
				
				GridCellRenderer<BeanModel> contentRender = new GridCellRenderer<BeanModel>() {
					@Override
					public Object render(BeanModel model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<BeanModel> store, Grid<BeanModel> grid) {
						return Format.ellipse(model.get("content").toString(),30);
					}
				};
				ColumnConfig clmncnfgNewColumn_2 = new ColumnConfig("content", ((Messages) Registry.get(Messages.class.getName())).MailListViewport_column_content(), 200);
				configs.add(clmncnfgNewColumn_2);
				clmncnfgNewColumn_2.setRenderer(contentRender);
				
				ColumnModel columnModel = new ColumnModel(configs); 
				inboxGrid = new Grid<BeanModel>(inListStore, columnModel);
				inboxGrid.addListener(Events.RowDoubleClick, new Listener<GridEvent>() {
					public void handleEvent(GridEvent e) {	
					}
				});
				tbtmInbox.add(inboxGrid);
				inboxGrid.setBorders(true);
			}
			
			//send box
//			TabItem tbtmSendbox = new TabItem("Outbox");
//			tbtmSendbox.setLayout(new FitLayout());
//			tabPanel.add(tbtmSendbox);
//			
//			{
//				List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
//				
//				GridCellRenderer<BeanModel> senderRender = new GridCellRenderer<BeanModel>() {
//					@Override
//					public Object render(BeanModel model, String property,
//							ColumnData config, int rowIndex, int colIndex,
//							ListStore<BeanModel> store, Grid<BeanModel> grid) {
//						User receiver = (User)model.get("receiver");
//						return receiver.getName();
//					}
//
//				};
//				ColumnConfig clmncnfgNewColumn = new ColumnConfig("receiver", "收件人", 80);
//				configs.add(clmncnfgNewColumn);
//				clmncnfgNewColumn.setRenderer(senderRender);
//				
//				GridCellRenderer<BeanModel> titleRender = new GridCellRenderer<BeanModel>() {
//					@Override
//					public Object render(BeanModel model, String property,
//							ColumnData config, int rowIndex, int colIndex,
//							ListStore<BeanModel> store, Grid<BeanModel> grid) {
//						return model.get("title");
//					}
//
//				};
//				ColumnConfig clmncnfgNewColumn_1 = new ColumnConfig("title", "邮件标题", 100);
//				configs.add(clmncnfgNewColumn_1);
//				clmncnfgNewColumn_1.setRenderer(titleRender);
//				
//				GridCellRenderer<BeanModel> contentRender = new GridCellRenderer<BeanModel>() {
//					@Override
//					public Object render(BeanModel model, String property,
//							ColumnData config, int rowIndex, int colIndex,
//							ListStore<BeanModel> store, Grid<BeanModel> grid) {
//						return Format.ellipse(model.get("content").toString(),30);
//					}
//				};
//				ColumnConfig clmncnfgNewColumn_2 = new ColumnConfig("content", "内容", 200);
//				configs.add(clmncnfgNewColumn_2);
//				clmncnfgNewColumn_2.setRenderer(contentRender);
//				
//				ColumnModel columnModel = new ColumnModel(configs); 
//				outboxGrid = new Grid<BeanModel>(outListStore, columnModel);
//				outboxGrid.addListener(Events.RowDoubleClick, new Listener<GridEvent>() {
//					public void handleEvent(GridEvent e) {
//						
//					}
//				});
//				tbtmSendbox.add(outboxGrid);
//				outboxGrid.setBorders(true);
//			}
			
			
			//tool bar
			ToolBar toolBar = new ToolBar();
			
			Button btnBack = new Button(((Messages) Registry.get(Messages.class.getName())).MailListViewport_button_back());
			toolBar.add(btnBack);
			btnBack.addListener(Events.Select,new Listener<ButtonEvent>() {

				@Override
				public void handleEvent(ButtonEvent be) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_USERHOME_TO_PANEL);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			
			FillToolItem fillToolItem = new FillToolItem();
			toolBar.add(fillToolItem);
			
			Button btnSendbox = new Button(((Messages) Registry.get(Messages.class.getName())).MailListViewport_button_new());
			toolBar.add(btnSendbox);
			
			Button btnDelete = new Button(((Messages) Registry.get(Messages.class.getName())).MailListViewport_button_delete());
			toolBar.add(btnDelete);
			setTopComponent(toolBar);
		}
		
		public void loadMails(Collection<Mail> inMails){
			inListStore.removeAll();
			List<BeanModel> list = mailFactory.createModel(inMails);
			inListStore.add(list);
			inListStore.commitChanges();
		}
	}
	
}
