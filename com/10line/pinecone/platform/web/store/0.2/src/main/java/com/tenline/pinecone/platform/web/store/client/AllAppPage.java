package com.tenline.pinecone.platform.web.store.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
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
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.tenline.pinecone.platform.web.store.shared.ApplicationInfo;
import com.tenline.pinecone.platform.web.store.shared.CategoryInfo;
import com.tenline.pinecone.platform.web.store.shared.ConsumerInfo;

public class AllAppPage extends ContentPanel {

	private ListStore<ConsumerInfo> store = new ListStore<ConsumerInfo>();
	private Grid<ConsumerInfo> grid;
	private List<ColumnConfig> configs;
	
	private List<ConsumerInfo> allApps;
	private List<ApplicationInfo> myApps;
	
	private int count;
	
	private Button backButton; 
	private Button categoryButton;
	private Menu categoryMenu;

	@SuppressWarnings("unused")
	public AllAppPage() {
		setHeaderVisible(false);
		setLayout(new BorderLayout());

		ToolBar tb = new ToolBar();
		backButton = new Button("<m-appstore-panel-title>返回</m-appstore-panel-title>");
		backButton.setHeight(40);
		backButton.addListener(Events.Select, new Listener<ButtonEvent>() {
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
		tb.add(backButton);

		SeparatorToolItem separatorToolItem = new SeparatorToolItem();
		tb.add(separatorToolItem);
		separatorToolItem.setHeight("");

		categoryButton = new Button("<m-appstore-panel-title>应用类型</m-appstore-panel-title>");
		categoryButton.setHeight(40);
		tb.add(categoryButton);

		categoryMenu = new Menu();
		categoryButton.setMenu(categoryMenu);
		MenuItem allMenuItem = new MenuItem();
		allMenuItem.setText("全部");
		categoryMenu.add(allMenuItem);
		allMenuItem.addSelectionListener(new SelectionListener<MenuEvent>(){
			@Override
			public void componentSelected(MenuEvent ce) {
				EnvConfig.getPineconeService().getAllConsumerInfo(new AsyncCallback<List<ConsumerInfo>>(){

					@Override
					public void onFailure(Throwable caught) {
						MessageBox.info("错误", "调用后台服务获取用户的应用信息失败", null);
					}

					@Override
					public void onSuccess(List<ConsumerInfo> result) {
						if(result != null){
							store.removeAll();
							store.add(result);
							store.commitChanges();
						}
					}
					
				});
			}
		});
		
		
		EnvConfig.getPineconeService().getCategorys(new AsyncCallback<List<CategoryInfo>>(){

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.info("错误", "调用后台服务获取分类信息失败", null);
			}

			@Override
			public void onSuccess(List<CategoryInfo> result) {
				if(result != null){
					for(final CategoryInfo ci:result){
						final MenuItem mi = new MenuItem();
						mi.setText(ci.getName());
						categoryMenu.add(mi);
						mi.addSelectionListener(new SelectionListener<MenuEvent>(){
							@Override
							public void componentSelected(MenuEvent ce) {
								EnvConfig.getPineconeService().
									getConsumerByCategory(ci, new AsyncCallback<List<ConsumerInfo>>(){

									@Override
									public void onFailure(Throwable caught) {
										MessageBox.info("错误", "调用后台服务获取指定类型的应用失败", null);
									}

									@Override
									public void onSuccess(List<ConsumerInfo> result) {
										store.removeAll();
										store.add(result);
										store.commitChanges();
									}
								});
							}
						});
					}
				}
			}
			
		});
		
		
		FillToolItem fillToolItem = new FillToolItem();
		tb.add(fillToolItem);
		StoreFilterField<ConsumerInfo> filter = new StoreFilterField<ConsumerInfo>(){

			@Override
			protected boolean doSelect(Store<ConsumerInfo> store,
					ConsumerInfo parent, ConsumerInfo record, String property,
					String filter) {
				//TODO
				
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
						                	addApplication(EnvConfig.getLoginUser(), model, new AsyncCallback<Void>(){

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
				
				for(ApplicationInfo ai:myApps){
					if(ai.getConsumer().getId().equals(model.getConsumer().getId())){
						b.setText("已添加");
						b.setEnabled(false);
					}
				}
				
				return b;
			}
		};

		configs = new ArrayList<ColumnConfig>();
		ColumnConfig clmncnfgNewColumn = new ColumnConfig("name", "名称", 160);
		clmncnfgNewColumn.setAlignment(HorizontalAlignment.LEFT);
//		 clmncnfgNewColumn.setRenderer(thumbRender);
		configs.add(clmncnfgNewColumn);

		ColumnConfig clmncnfgNewColumn_1 = new ColumnConfig("version",
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

	public void reset(Object obj){
	
		count = 0;
		this.mask();
		
		count++;
		EnvConfig.getPineconeService().getAppInfo(EnvConfig.getLoginUser(),new AsyncCallback<List<ApplicationInfo>>(){

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.info("错误", "调用后台服务获取所有的应用信息失败", null);
				AllAppPage.this.unmask();
			}

			@Override
			public void onSuccess(List<ApplicationInfo> result) {
				count--;
				myApps = result;
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
						store.removeAll();
						store.add(allApps);
						store.commitChanges();
						grid.setAutoExpandColumn("description");
						AllAppPage.this.unmask();
					}
					
				});
				
				
			}
		});
		
		
	}

}
