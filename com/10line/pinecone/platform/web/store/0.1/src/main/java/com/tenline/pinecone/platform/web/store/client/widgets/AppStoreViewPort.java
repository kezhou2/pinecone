package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.core.XTemplate;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
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
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.RowExpander;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
import com.tenline.pinecone.platform.web.store.client.events.ConsumerEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerServiceAsync;
/**
 * 应用商店页面
 * @author lue
 *
 */
public class AppStoreViewPort extends AbstractViewport {
	ListStore<BeanModel> store;
	public AppStoreViewPort() {
		super();
		body.add(new MainPanel(),new BorderLayoutData(LayoutRegion.CENTER));
	}
	private class MainPanel extends ContentPanel{

		public MainPanel() {
			setHeaderVisible(false);
			setCollapsible(true);
			setLayout(new BorderLayout());

			BorderLayoutData centerLayoutData = new BorderLayoutData(LayoutRegion.CENTER);
			centerLayoutData.setCollapsible(true);
			centerLayoutData.setSplit(true);

			add(createCenter(), centerLayoutData);
			add(createEast(), new BorderLayoutData(LayoutRegion.EAST,300.0f));
		}

	}
	/**
	 * 创建应用列表
	 * @return
	 */
	private ContentPanel createCenter() {
		ContentPanel centerPanel = new ContentPanel();
		centerPanel.setHeaderVisible(false);
		centerPanel.setLayout(new BorderLayout());

		LayoutContainer header = createHeader();
		Grid<BeanModel> grid = initGrid();

		centerPanel.add(header,new BorderLayoutData(LayoutRegion.NORTH,30));
		centerPanel.add(grid, new BorderLayoutData(LayoutRegion.CENTER));

		return centerPanel;
	}
	private LayoutContainer createHeader() {
		LayoutContainer header = new LayoutContainer();
		HBoxLayout layout = new HBoxLayout();  
		layout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);  
		header.setLayout(layout);

		//返回“个人主页”按钮
		Button buttonHomePage = new Button();
		buttonHomePage.setText(((Messages) Registry.get(Messages.class.getName())).homePage());
		buttonHomePage.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				AppEvent event = new AppEvent(WidgetEvents.UPDATE_USERHOME_TO_PANEL);
				event.setHistoryEvent(true);
				Dispatcher.get().dispatch(event);
			}
		});
		header.add(buttonHomePage);

		//按类别过滤
		HBoxLayoutData flex = new HBoxLayoutData(new Margins(0, 5, 0, 0));  
		flex.setFlex(1);  
		header.add(new Text(), flex);  
		Label label = new Label(((Messages) Registry.get(Messages.class.getName())).label_category());
		header.add(label, new HBoxLayoutData(new Margins(0, 5, 0, 0)));
		SimpleComboBox<String> consumerCombo = new SimpleComboBox<String>();
		consumerCombo.add(Arrays.asList("ALL",Category.DOMAIN_GAME,Category.DOMAIN_PET,Category.DOMAIN_SECURITY));
		consumerCombo.setTriggerAction(TriggerAction.ALL);
		consumerCombo.setEditable(false);
		consumerCombo.setForceSelection(true);
		consumerCombo.addSelectionChangedListener(new SelectionChangedListener<SimpleComboValue<String>>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<SimpleComboValue<String>> se) {
				AppEvent event;
				if("all".equalsIgnoreCase(se.getSelectedItem().getValue())) {
					event = new AppEvent(ConsumerEvents.GET_ALL);
				} else {
					event = new AppEvent(ConsumerEvents.GET_BY_CATEGORY);
					event.setData("id", se.getSelectedItem().getValue());
				}

				Dispatcher.get().dispatch(event);
			}
		});
		header.add(consumerCombo, new HBoxLayoutData(new Margins(0, 25, 0, 0)));

		//搜索
		final TextField<String> txtfldSearch = new TextField<String>();
		header.add(txtfldSearch,new HBoxLayoutData(new Margins(0, 5, 0, 0)));
		txtfldSearch.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).text_search());
		Button button = new Button(((Messages) Registry.get(Messages.class.getName())).button_search());
		button.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				String name = txtfldSearch.getValue();
				AppEvent event = new AppEvent(ConsumerEvents.GET_BY_NAME);
				event.setData("name",name);
				Dispatcher.get().dispatch(event);
			}
		});
		header.add(button,new HBoxLayoutData(new Margins(0, 5, 0, 0)));
		return header;
	}
	private Grid<BeanModel> initGrid() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();  

		//第一列：展开，“+”按钮
		XTemplate xTemplate = XTemplate
				.create("<p><b>Product by:</b> {name}</p><br><p><b>Summary:</b> {detail}</p>");

		RowExpander rowExpander = new RowExpander();
		rowExpander.setTemplate(xTemplate);
		configs.add(rowExpander);  

		//第二列：应用图标，Image
		ColumnConfig column = new ColumnConfig();  
		column.setId("icon");  
		column.setHeader(((Messages) Registry.get(Messages.class.getName())).app_Icon());  
		column.setWidth(150);  
		column.setRowHeader(true);
		GridCellRenderer<BeanModel> iconRenderer = new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				return ((Images) Registry.get(Images.class.getName())).consumerIcon().getHTML();
			}
		};
		column.setRenderer(iconRenderer);
		configs.add(column);  

		//第三列：应用名称
		column = new ColumnConfig();  
		column.setId("name");  
		column.setHeader(((Messages) Registry.get(Messages.class.getName())).app_Name());  
		column.setWidth(400);  
		configs.add(column);  

		//第四列：应用版本号
		column = new ColumnConfig();  
		column.setId("version");  
		column.setHeader(((Messages) Registry.get(Messages.class.getName())).app_version());  
		column.setWidth(100);  
		configs.add(column);  

		//第五列：安装应用，“安装”按钮
		column = new ColumnConfig("install", ((Messages) Registry.get(Messages.class.getName())).app_install(), 150);  
		GridCellRenderer<BeanModel> buttonRenderer = new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(final BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				final Button b = new Button(((Messages) Registry.get(Messages.class.getName())).app_install(), new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						AppEvent event = new AppEvent(ApplicationEvents.INSTALL);
						event.setData("consumer",model.get("consumer"));
						Dispatcher.get().dispatch(event);
						final Listener<MessageBoxEvent> listener = new Listener<MessageBoxEvent>() {
							public void handleEvent(MessageBoxEvent ce) {
							}
						};
						MessageBox.info("INFO", "This application has been successfully installed.", listener);
						ce.getButton().setText("installed");
						ce.getButton().setToolTip("This Application has already been installed");
						ce.getButton().setEnabled(false);
					}
				});
				b.setWidth(60);
				b.setToolTip("Click to install");

				return b;
			}
		};
		column.setRenderer(buttonRenderer);
		configs.add(column);  

		store = new ListStore<BeanModel>();
		ColumnModel cm = new ColumnModel(configs);
		Grid<BeanModel> grid = new Grid<BeanModel>(store, cm);
		grid.setBorders(true);
		grid.addPlugin(rowExpander);
		//获取应用列表内容
		AppEvent appEvent = new AppEvent(ConsumerEvents.GET_ALL);
		Dispatcher.get().dispatch(appEvent);
		return grid;
	}
	/**
	 * 创建排行榜
	 * @return
	 */
	private ContentPanel createEast() {
		ContentPanel eastPanel = new ContentPanel();
		eastPanel.setHeading(((Messages) Registry.get(Messages.class.getName())).ranking());
		eastPanel.setLayout(new FillLayout());

		RpcProxy<Collection<Consumer>> proxy = new RpcProxy<Collection<Consumer>>() {
			@Override
			protected void load(Object loadConfig, AsyncCallback<Collection<Consumer>> callback) {
				ConsumerServiceAsync service = Registry.get(ConsumerService.class.getName());
				service.show("all", callback);
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
		listView.setHeight("245");
		return eastPanel;
	}
	public void setAppGridData(List<BeanModel> result) {
		List<BeanModel> list = new ArrayList<BeanModel>();
		BeanModelFactory consumerFactory = BeanModelLookup.get().getFactory(Consumer.class);
		Consumer c = new Consumer("key", "secret", "displayName", "connectURI");
		BeanModel bean = consumerFactory.createModel(c);
		bean.set("detail", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.<br/><br/>Aliquam commodo ullamcorper erat. Nullam vel justo in neque porttitor laoreet. Aenean lacus dui, consequat eu, adipiscing eget, nonummy non, nisi. Morbi nunc est, dignissim non, ornare sed, luctus eu, massa. Vivamus eget quam. Vivamus tincidunt diam nec urna. Curabitur velit.");
		bean.set("install", ((Images) Registry.get(Images.class.getName())).consumerIcon());
		bean.set("consumer", c);
		list.add(bean);
		store.removeAll();
		store.add(list);
		store.commitChanges();
	}

}
