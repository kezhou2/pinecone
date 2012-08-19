package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.core.XTemplate;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.StoreFilterField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridViewConfig;
import com.extjs.gxt.ui.client.widget.grid.RowExpander;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
import com.tenline.pinecone.platform.web.store.client.events.ConsumerEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerServiceAsync;
/**
 * @author lue
 *
 */
public class AppStoreViewport extends AbstractViewport {
	private ListStore<BeanModel> consumerStore;
	private ListStore<BaseModelData> categoriesStore;
	private ComboBox<BaseModelData> categoryCombo;
	public AppStoreViewport() {
		super();
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(3,3,3,3));
		body.add(new MainPanel(),bld);
	}
	private class MainPanel extends ContentPanel{

		public MainPanel() {
			setHeaderVisible(false);
			setCollapsible(true);
			setLayout(new FillLayout());
			setBorders(false);
			setBodyBorder(false);
			
			LayoutContainer mainlc = new LayoutContainer();
			this.add(mainlc,new FillData(0,0,0,0));
			mainlc.setLayout(new BorderLayout());
			mainlc.addStyleName("abstractviewport-background");

			BorderLayoutData centerLayoutData = new BorderLayoutData(LayoutRegion.CENTER);
			centerLayoutData.setMargins(new Margins(5,5,5,5));
			centerLayoutData.setCollapsible(true);
			centerLayoutData.setSplit(true);

			mainlc.add(createCenter(), centerLayoutData);
			
			BorderLayoutData eastLayoutData = new BorderLayoutData(LayoutRegion.EAST,300.0f);
			eastLayoutData.setMargins(new Margins(5,5,5,5));
			
			mainlc.add(createEast(), eastLayoutData);
		}

	}
	
	private ContentPanel createCenter() {
		ContentPanel centerPanel = new ContentPanel();
		centerPanel.setHeaderVisible(false);
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBorders(false);
		centerPanel.setBodyBorder(false);
		centerPanel.addStyleName("appstoreviewport-panel");

		centerPanel.setTopComponent(createToolBar());
		Grid<BeanModel> grid = initGrid();

		centerPanel.add(grid, new BorderLayoutData(LayoutRegion.CENTER));

		return centerPanel;
	}
	private ToolBar createToolBar() {
		ToolBar toolBar = new ToolBar();
		toolBar.setHeight("38px");
		toolBar.addStyleName("appstoreviewport-toolbar");

		LayoutContainer lc = new LayoutContainer();
		lc.setLayout(new FillLayout());
		Button buttonHomePage = new Button();
		buttonHomePage.setHTML("<img class='btn-img-left' src='../images/icons/back.png'>"+
				((Messages) Registry.get(Messages.class.getName())).HomeViewport_title()+
				"</img>");
		buttonHomePage.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				AppEvent event1 = new AppEvent(WidgetEvents.UPDATE_USERHOME_TO_PANEL);
				event1.setHistoryEvent(true);
				Dispatcher.get().dispatch(event1);
			}
		});
		lc.add(buttonHomePage);
		toolBar.add(lc);
		buttonHomePage.setHeight("32px");
		buttonHomePage.setStyleName("abstractviewport-btn");

		FillToolItem fillToolItem = new FillToolItem();
		toolBar.add(fillToolItem);
		
		Label label = new Label(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_label_category());
		label.addStyleName("homeviewport-title-small");
		toolBar.add(label);
		categoryCombo = new ComboBox<BaseModelData>();
		categoryCombo.setWidth("70px");
		categoryCombo.setTriggerAction(TriggerAction.ALL);
		categoryCombo.setEditable(false);
		categoryCombo.setForceSelection(true);
		categoryCombo.setDisplayField("domain");
		categoriesStore = new ListStore<BaseModelData>();
		categoryCombo.setStore(categoriesStore);
		categoryCombo.addSelectionChangedListener(new SelectionChangedListener<BaseModelData>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<BaseModelData> se) {
				AppEvent event;
				if("all".equalsIgnoreCase(se.getSelectedItem().get("domain").toString())) {
					event = new AppEvent(ConsumerEvents.GET_ALL);
				} else {
					event = new AppEvent(ConsumerEvents.GET_BY_CATEGORY);
					event.setData("id", se.getSelectedItem().get("id"));
				}
				event.setHistoryEvent(false);
				Dispatcher.get().dispatch(event);
			}
		});
		toolBar.add(categoryCombo);
		categoryCombo.setLabelStyle("font-size: 18px;font-weight: bold;line-height: 18px;margin-bottom: 10px;color: #4D5762;	position: relative;	word-spacing: -0.1em;");
		categoryCombo.setStyleAttribute("autoCreate", "{tag: \"input\", type: \"text\", size: \"20\", autocomplete: \"off\", maxlength: \"10\"}");
		
		Text text1 = new Text(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_label_search());
		text1.addStyleName("homeviewport-title-small");
		toolBar.add(text1);
		StoreFilterField<BeanModel> filter = new StoreFilterField<BeanModel>() {

			@Override
			protected boolean doSelect(Store<BeanModel> store,
					BeanModel parent, BeanModel record, String property,
					String filter) {
				System.out.println("###############checking");
				for (String key : record.getPropertyNames()) {
					System.out.println("check:"+key);
					if (record.get(key).toString().contains(filter)) {
						return true;
					}
				}
				return false;
			}

		};
		filter.bind(consumerStore);
		filter.setWidth("120px");
		filter.setEmptyText(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_text_search());
		toolBar.add(filter);
		
		com.extjs.gxt.ui.client.widget.button.Button blankBtn = new com.extjs.gxt.ui.client.widget.button.Button();
		toolBar.add(blankBtn);
		blankBtn.setEnabled(false);
		blankBtn.setHeight("30px");
		return toolBar;
	}
	
	private Grid<BeanModel> initGrid() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();  

		//��һ�У�չ������+����ť
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>");
		sb.append(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_ProductBy());
		sb.append(":</b> {name}</p><br><p><b>");
		sb.append(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_Summary());
		sb.append(":</b> {detail}</p>");
//		XTemplate xTemplate = XTemplate
//				.create("<p><b>Product by:</b> {name}</p><br><p><b>Summary:</b> {detail}</p>");
		XTemplate xTemplate = XTemplate.create(sb.toString());
		
		RowExpander rowExpander = new RowExpander();
		rowExpander.setTemplate(xTemplate);
		configs.add(rowExpander);  

		//�ڶ��У�Ӧ��ͼ�꣬Image
		ColumnConfig column = new ColumnConfig();  
		column.setId("icon");  
		column.setHeader(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_gridtitle_icon());  
		column.setWidth(150);  
		column.setRowHeader(true);
		
		GridCellRenderer<BeanModel> iconRenderer = new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				return ((AbstractImagePrototype) model.get("icons")).getHTML();
//				return ((Images) Registry.get(Images.class.getName())).consumerIcon().getHTML();
			}
		};
		column.setRenderer(iconRenderer);
		configs.add(column);  

		//�����У�Ӧ�����
		column = new ColumnConfig();  
		column.setId("name");  
		column.setHeader(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_gridtitle_name());  
		column.setWidth(300);  
		configs.add(column);  

		//�����У�Ӧ�ð汾��
		column = new ColumnConfig();  
		column.setId("version");  
		column.setHeader(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_gridtitle_version());  
		column.setWidth(100);  
		configs.add(column);  

		//�����У���װӦ�ã�����װ����ť
		column = new ColumnConfig("install", ((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_gridtitle_install(), 150);  
		GridCellRenderer<BeanModel> buttonRenderer = new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(final BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				final com.extjs.gxt.ui.client.widget.button.Button b = 
					new com.extjs.gxt.ui.client.widget.button.Button(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_installdialog_button_install(), new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						AppEvent event = new AppEvent(ApplicationEvents.INSTALL);
						event.setData("consumer",model.get("consumer"));
						event.setHistoryEvent(true);
						Dispatcher.get().dispatch(event);
						final Listener<MessageBoxEvent> listener = new Listener<MessageBoxEvent>() {
							public void handleEvent(MessageBoxEvent ce) {
							}
						};
						MessageBox.info(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_installdialog_title()
								, ((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_installdialog_info(), listener);
						ce.getButton().setText(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_installdialog_button_installed());
						ce.getButton().setToolTip(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_installed_button_tooltip());
						ce.getButton().setEnabled(false);
					}
				});
				b.setWidth(60);
				b.setToolTip(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_install_button_tooltip());

				return b;
			}
		};
		column.setRenderer(buttonRenderer);
		configs.add(column);

		consumerStore = new ListStore<BeanModel>();
		ColumnModel cm = new ColumnModel(configs);
		Grid<BeanModel> grid = new Grid<BeanModel>(consumerStore, cm);
//		grid.setBorders(false);
		grid.addPlugin(rowExpander);
		grid.setStripeRows(true);
		grid.setHideHeaders(true);
//		grid.getView().getHeader().addStyleName("appstoreviewport-table-header");
		
//		grid.addStyleName("appstoreviewport-table-header-1");
		//��ȡӦ���б�����
		AppEvent appEvent = new AppEvent(ConsumerEvents.GET_ALL);
		Dispatcher.get().dispatch(appEvent);
		return grid;
	}
	/**
	 * �������а�
	 * @return
	 */
	private LayoutContainer createEast() {
		LayoutContainer mainlc = new LayoutContainer();
		mainlc.setLayout(new BorderLayout());
		LayoutContainer toolbarLayoutContainer = new LayoutContainer();
		BorderLayoutData toolbarBld = new BorderLayoutData(LayoutRegion.NORTH, 40.0f);
		toolbarBld.setMargins(new Margins(0,0,0,0));
		mainlc.add(toolbarLayoutContainer,toolbarBld);
		toolbarLayoutContainer.addStyleName("abstractviewport-background");
		
		HBoxLayout hbl_toolbarLayoutContainer = new HBoxLayout();
		hbl_toolbarLayoutContainer.setPack(BoxLayoutPack.START);
		hbl_toolbarLayoutContainer.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
		toolbarLayoutContainer.setLayout(hbl_toolbarLayoutContainer);
		
		Button buttonRegister = new Button();
		buttonRegister.setHTML("<img class='btn-img-left' src='../images/icons/graphic-design.png'>"+
				((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_button_register()+
				"</img>");
		buttonRegister.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent e) {
				AppEvent event = new AppEvent(WidgetEvents.UPDATE_REGISTER_APP_TO_PANEL);
				event.setHistoryEvent(true);
				Dispatcher.get().dispatch(event);
			}
		});
		toolbarLayoutContainer.add(buttonRegister);
		buttonRegister.setHeight("32px");
		buttonRegister.setStyleName("abstractviewport-btn");
	
		ContentPanel eastPanel = new ContentPanel();
		eastPanel.setHeaderVisible(false);
		eastPanel.setLayout(new FillLayout());
		eastPanel.setBorders(false);
		eastPanel.setBodyBorder(false);
		eastPanel.addStyleName("appstoreviewport-panel");
		
		ToolBar toolBar = new ToolBar();
		toolBar.setHeight("34px");
		toolBar.addStyleName("appstoreviewport-toolbar");
		
		Text txtTitle = new Text(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_title_ranking());
		txtTitle.addStyleName("homeviewport-title-small");
		toolBar.add(txtTitle);
		eastPanel.setTopComponent(toolBar);
		
		com.extjs.gxt.ui.client.widget.button.Button blankBtn = new com.extjs.gxt.ui.client.widget.button.Button();
		toolBar.add(blankBtn);
		blankBtn.setEnabled(false);
		blankBtn.setHeight("30px");

		//TODO need to refresh rank list
		RpcProxy<Collection<Consumer>> proxy = new RpcProxy<Collection<Consumer>>() {
			@Override
			protected void load(Object loadConfig, AsyncCallback<Collection<Consumer>> callback) {
				ConsumerServiceAsync service = Registry.get(ConsumerService.class.getName());
//				service.show("all", callback);
			}
		};
		ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(
				proxy, new BeanModelReader());
		ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
		loader.load();

		ListView<BeanModel> listView = new ListView<BeanModel>(new ListStore<BeanModel>());
		listView.setStore(store);
		listView.setDisplayProperty("name");
		eastPanel.add(listView);
		listView.setHeight("200");
		listView.setBorders(false);
		
		BorderLayoutData mainBld = new BorderLayoutData(LayoutRegion.CENTER);
		mainBld.setMargins(new Margins(0,0,0,0));
		mainlc.add(eastPanel,mainBld);
		
		return mainlc;
	}
	public void setAppGridData(List<BeanModel> result) {
		consumerStore.removeAll();
		consumerStore.add(result);
		consumerStore.commitChanges();
	}
	
	public void setCategories(List<BaseModelData> categories){
		System.out.println("setCategories:"+categories.size());
		categoriesStore.removeAll();
		categoriesStore.add(categories);
		categoriesStore.commitChanges();
	}

}
