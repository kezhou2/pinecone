package com.tenline.pinecone.platform.web.store.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.StoreFilterField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.tenline.pinecone.platform.web.store.client.model.AppInfo;
import com.tenline.pinecone.platform.web.store.client.model.ConsumerInfo;

public class AllAppPage extends ContentPanel {

	private ListStore<ConsumerInfo> store = new ListStore<ConsumerInfo>();
	private Grid<ConsumerInfo> grid;
	private List<ColumnConfig> configs;
	
	private List<ConsumerInfo> allApps;
	private List<AppInfo> myApps;
	
	private int count;

	@SuppressWarnings("unused")
	public AllAppPage() {
		setHeaderVisible(false);
		setLayout(new BorderLayout());

		ToolBar tb = new ToolBar();
		Button button = new Button("<m-appstore-panel-title>返回</m-appstore-panel-title>");
		button.setHeight(40);
		button.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {

				Widget w = AllAppPage.this.getParent();
				if (w instanceof ContentPanel) {
					ContentPanel cp = (ContentPanel) w;
					cp.remove(AllAppPage.this);
					cp.layout(true);
					cp.add(EnvConfig.getHomePage(), new BorderLayoutData(
							LayoutRegion.CENTER));
					EnvConfig.getHomePage().reset(null);
					cp.layout(true);
				}
			}
		});
		tb.add(button);

		SeparatorToolItem separatorToolItem = new SeparatorToolItem();
		tb.add(separatorToolItem);
		separatorToolItem.setHeight("");

		Button catalogButton = new Button("<m-appstore-panel-title>应用类型</m-appstore-panel-title>");
		catalogButton.setHeight(40);
		tb.add(catalogButton);

		Menu menu = new Menu();
		catalogButton.setMenu(menu);
		
		FillToolItem fillToolItem = new FillToolItem();
		tb.add(fillToolItem);
		StoreFilterField<ConsumerInfo> filter = new StoreFilterField<ConsumerInfo>(){

			@Override
			protected boolean doSelect(Store<ConsumerInfo> store,
					ConsumerInfo parent, ConsumerInfo record, String property,
					String filter) {
				if(record.getDisplayName().contains(filter)){
					return true;
				}
				
				return false;
			}
		};
		filter.setFieldLabel("搜索");
		filter.bind(store);
		tb.add(filter);
		this.setTopComponent(tb);

		GridCellRenderer<ConsumerInfo> thumbRender = new GridCellRenderer<ConsumerInfo>() {

			@Override
			public Object render(ConsumerInfo model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<ConsumerInfo> store, Grid<ConsumerInfo> grid) {
				
				return new ThumbContainer(model);
			}

		};
		GridCellRenderer<ConsumerInfo> infoRender = new GridCellRenderer<ConsumerInfo>() {

			@Override
			public Object render(ConsumerInfo model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<ConsumerInfo> store, Grid<ConsumerInfo> grid) {
				
				return new InfoContainer(model);
			}

		};

		GridCellRenderer<ConsumerInfo> buttonRenderer = new GridCellRenderer<ConsumerInfo>() {
			public Object render(final ConsumerInfo model, String property,
					ColumnData config, final int rowIndex, final int colIndex,
					ListStore<ConsumerInfo> store, Grid<ConsumerInfo> grid) {
				
				final Button b = new Button("添加");
//				System.out.println("new button");
				b.addListener(Events.Select,
						new SelectionListener<ButtonEvent>() {

							@Override
							public void componentSelected(ButtonEvent ce) {
								
								MessageBox.confirm("确认", "是否允许该应用访问用户的信息",new Listener<MessageBoxEvent>(){

									@Override
									public void handleEvent(MessageBoxEvent be) {
										Button btn =be.getButtonClicked();   
						                if(btn.getItemId().equals(Dialog.YES))
						                {
						                	EnvConfig.getPineconeService().
											addConsumerToUser(EnvConfig.getLoginUser(), model, new AsyncCallback<Void>(){

													@Override
													public void onFailure(Throwable caught) {
														MessageBox.info("错误", "调用后台服务添加用户失败", null);
														b.setEnabled(true);
													}

													@Override
													public void onSuccess(Void result) {
														b.setText("已添加");
														b.setEnabled(false);
													}
												
											});
						                }else{
						                	return;
						                }
									}
									
								});
								
								
							}
						});
				
				b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
				b.setHeight(80);
				
				for(AppInfo ci:myApps){
					if(ci.getConsumerId().equals(model.getConsumer().getId())){
						b.setText("已添加");
						b.setEnabled(false);
					}
				}
				
				return b;
			}
		};

		configs = new ArrayList<ColumnConfig>();
		ColumnConfig clmncnfgNewColumn = new ColumnConfig("displayName", "名称", 160);
		clmncnfgNewColumn.setAlignment(HorizontalAlignment.LEFT);
//		 clmncnfgNewColumn.setRenderer(thumbRender);
		configs.add(clmncnfgNewColumn);

		ColumnConfig clmncnfgNewColumn_1 = new ColumnConfig("description",
				"描述", 200);
		clmncnfgNewColumn_1.setAlignment(HorizontalAlignment.LEFT);
//		clmncnfgNewColumn_1.setRenderer(infoRender);
		configs.add(clmncnfgNewColumn_1);

		ColumnConfig clmncnfgNewColumn_2 = new ColumnConfig("", "", 150);
		clmncnfgNewColumn_2.setAlignment(HorizontalAlignment.CENTER);
		clmncnfgNewColumn_2.setRenderer(buttonRenderer);
		configs.add(clmncnfgNewColumn_2);

		grid = new Grid<ConsumerInfo>(store, new ColumnModel(configs));
		grid.setAutoExpandColumn("description");
		grid.setAutoHeight(false);
		grid.setAutoWidth(false);
		grid.getView().setAutoFill(true);
		add(grid, new BorderLayoutData(LayoutRegion.CENTER));
		grid.setBorders(true);
	}
	
//	public void reset(Object obj){
////		System.out.println("reset!");
//		this.mask();
//		
//		EnvConfig.getPineconeService().getAllConsumerInfo(new AsyncCallback<List<ConsumerInfo>>(){
//
//			@Override
//			public void onFailure(Throwable caught) {
//				MessageBox.info("错误", "调用后台服务获取所有的应用信息失败", null);
//				AllAppPage.this.unmask();
//			}
//
//			@Override
//			public void onSuccess(List<ConsumerInfo> result) {
//				allApps = result;
//				System.out.println("AllAppPage:allApps.Size()="+allApps.size());
////				for(ConsumerInfo ci:result){
////					System.out.println(ci.getConsumer().getId());
////				}
//				EnvConfig.getPineconeService().getConsumerInfo(EnvConfig.getLoginUser()
//						, new AsyncCallback<List<ConsumerInfo>>(){
//
//							@Override
//							public void onFailure(Throwable caught) {
//								MessageBox.info("错误", "调用后台服务获取用户的应用信息失败", null);
//								AllAppPage.this.unmask();
//							}
//
//							@Override
//							public void onSuccess(List<ConsumerInfo> result) {
//								myApps = result;
//								System.out.println("AllAppPage:myApps.Size()="+myApps.size());
////								System.out.println("before remove");
//								store.removeAll();
////								System.out.println("before remove 1");
//								store.add(allApps);
////								System.out.println("before remove 2");
//								store.commitChanges();
////								System.out.println("after remove");
////								grid.reconfigure(store, new ColumnModel(configs));
//								grid.setAutoExpandColumn("description");
//								AllAppPage.this.unmask();
//							}
//					
//				});
//				
//				
//			}
//		});
//		
//		
//	}
	public void reset(Object obj){
	
		count = 0;
		this.mask();
		
		count++;
		EnvConfig.getPineconeService().getAppInfo(EnvConfig.getLoginUser(),new AsyncCallback<List<AppInfo>>(){

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.info("错误", "调用后台服务获取所有的应用信息失败", null);
				AllAppPage.this.unmask();
			}

			@Override
			public void onSuccess(List<AppInfo> result) {
				count--;
				myApps = result;
//				System.out.println("AllAppPage:allApps.Size()="+allApps.size());
//				for(ConsumerInfo ci:result){
//					System.out.println(ci.getConsumer().getId());
//				}
				count++;
				EnvConfig.getPineconeService().getAllConsumerInfo(new AsyncCallback<List<ConsumerInfo>>(){

					@Override
					public void onFailure(Throwable caught) {
						MessageBox.info("错误", "调用后台服务获取用户的应用信息失败", null);
						AllAppPage.this.unmask();
					}

					@Override
					public void onSuccess(List<ConsumerInfo> result) {
						allApps = result;
//						System.out.println("AllAppPage:myApps.Size()="+myApps.size());
//						System.out.println("before remove");
						store.removeAll();
//						System.out.println("before remove 1");
						store.add(allApps);
//						System.out.println("before remove 2");
						store.commitChanges();
//						System.out.println("after remove");
//						grid.reconfigure(store, new ColumnModel(configs));
						grid.setAutoExpandColumn("description");
						AllAppPage.this.unmask();
					}
					
				});
				
				
			}
		});
		
		
	}

}
