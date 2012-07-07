package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.Collection;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.CategoryEvents;
import com.tenline.pinecone.platform.web.store.client.events.ConsumerEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class ConsumerRegistryViewport extends AbstractViewport {

	private MainPanel mainPanel;
	
	public ConsumerRegistryViewport(){
		mainPanel = new MainPanel();
		body.add(mainPanel, new BorderLayoutData(LayoutRegion.CENTER));
	}
	
	public void loadInfo(){
		AppEvent event = new AppEvent(CategoryEvents.GET_ALL);
		Dispatcher.get().dispatch(event);
	}
	
	public void loadCategories(List<BeanModel> categories){
		mainPanel.loadCategories(categories);
	}
	
	public void loadConsumers(Collection<Consumer> consumers){
		mainPanel.loadConsumers(consumers);
	}
	
	/**
	 * Main panel to show
	 * @author liugy
	 *
	 */
	private class MainPanel extends ContentPanel{
		
		/**category ListStore*/
		private ListStore<BeanModel> categoryListStore = new ListStore<BeanModel>();
		/**user registered consumer ListStore*/
		private ListStore<BeanModel> consumerListStore = new ListStore<BeanModel>();
		/**name textfield*/
		private TextField<String> txtfldName;
		/**consumer category ComboBox*/
		private ComboBox<BeanModel> cmbxCategory;
		/**key textfield*/
		private TextField<String> txtfldKey;
		/**secret textfield*/
		private TextField<String> txtfldSecret;
		/**connect uri*/
		private TextField<String> txtfldConnectUri;
		/***/
		private TextField<String> txtfldAlias;
		private TextField<String> txtfldVersion;
		private Grid<BeanModel> myConsumerGrid;
		
		/**consumer category ComboBox*/
		private ComboBox<BeanModel> cmbxCategory_1;
		/***/
		private TextField<String> txtfldName_1;
		private TextField<String> txtfldVersion_1;
		private TextField<String> txtfldAlias_1;
		private TextField<String> txtfldConnectUri_1;
		
		/***/
		private BeanModelFactory consumerFactory = BeanModelLookup.get().getFactory(Consumer.class);
		private BeanModelFactory categoryFactory = BeanModelLookup.get().getFactory(Category.class);
		
		/**user registered consumer grid*/
//		private Grid<BeanModel> grid;
		
		public MainPanel(){
			setHeaderVisible(false);
			setLayout(new FitLayout());
			
			TabPanel tabPanel = new TabPanel();
			
//			TabItem tbtmApplication = new TabItem(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_tabitem_application());
//			tbtmApplication.setLayout(new BorderLayout());
//			
//			LayoutContainer layoutContainer = new LayoutContainer();
//			FormLayout fl_layoutContainer = new FormLayout();
//			layoutContainer.setLayout(fl_layoutContainer);
//			
//			txtfldName = new TextField<String>();
//			FormData fd_txtfldName = new FormData("80%");
//			fd_txtfldName.setMargins(new Margins(10, 0, 10, 0));
//			layoutContainer.add(txtfldName, fd_txtfldName);
//			txtfldName.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_name());
//			
//			cmbxCategory = new ComboBox<BeanModel>();
//			cmbxCategory.setStore(categoryListStore);
//			FormData fd_cmbxCategory = new FormData("80%");
//			fd_cmbxCategory.setMargins(new Margins(10, 0, 10, 0));
//			layoutContainer.add(cmbxCategory, fd_cmbxCategory);
//			cmbxCategory.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_category());
//			
//			txtfldKey = new TextField<String>();
//			FormData fd_txtfldKey = new FormData("80%");
//			fd_txtfldKey.setMargins(new Margins(10, 0, 10, 0));
//			layoutContainer.add(txtfldKey, fd_txtfldKey);
//			txtfldKey.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_key());
//			
//			txtfldSecret = new TextField<String>();
//			FormData fd_txtfldSecret = new FormData("80%");
//			fd_txtfldSecret.setMargins(new Margins(10, 0, 10, 0));
//			layoutContainer.add(txtfldSecret, fd_txtfldSecret);
//			txtfldSecret.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_secret());
//			
//			txtfldConnectUri = new TextField<String>();
//			FormData fd_txtfldConnectUri = new FormData("80%");
//			fd_txtfldConnectUri.setMargins(new Margins(10, 0, 10, 0));
//			layoutContainer.add(txtfldConnectUri, fd_txtfldConnectUri);
//			txtfldConnectUri.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_connectURI());
//			
//			txtfldAlias = new TextField<String>();
//			FormData fd_txtfldAlias = new FormData("80%");
//			fd_txtfldAlias.setMargins(new Margins(10, 0, 10, 0));
//			layoutContainer.add(txtfldAlias, fd_txtfldAlias);
//			txtfldAlias.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_alias());
//			
//			txtfldVersion = new TextField<String>();
//			FormData fd_txtfldVersion = new FormData("80%");
//			fd_txtfldVersion.setMargins(new Margins(10, 0, 10, 0));
//			layoutContainer.add(txtfldVersion, fd_txtfldVersion);
//			txtfldVersion.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_version());
//			
//			MultiField mltfldIconUpload = new MultiField();
//			
//			FileUploadField flpldfldNewFileuploadfield = new FileUploadField();
//			mltfldIconUpload.add(flpldfldNewFileuploadfield);
//			flpldfldNewFileuploadfield.setFieldLabel("New FileUploadField");
//			FormData fd_mltfldIconUpload = new FormData("80%");
//			fd_mltfldIconUpload.setMargins(new Margins(10, 0, 10, 0));
//			layoutContainer.add(mltfldIconUpload, fd_mltfldIconUpload);
//			mltfldIconUpload.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_iconupload());
//			
//			ButtonBar buttonBar = new ButtonBar();
//			buttonBar.setAlignment(HorizontalAlignment.RIGHT);
//			
//			Button saveButton = new Button(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_button_save());
//			buttonBar.add(saveButton);
//			layoutContainer.add(buttonBar, new FormData("80%"));
//			BorderLayoutData bld_layoutContainer = new BorderLayoutData(LayoutRegion.CENTER);
//			bld_layoutContainer.setMargins(new Margins(10, 40, 10, 40));
//			tbtmApplication.add(layoutContainer, bld_layoutContainer);
//			layoutContainer.setBorders(true);
//			
//			LayoutContainer consumerLayoutContainer = new LayoutContainer();
//			consumerLayoutContainer.setLayout(new FitLayout());
//			List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
//			
//			ColumnConfig clmncnfgNewColumn = new ColumnConfig("id", "New Column", 150);
//			configs.add(clmncnfgNewColumn);
//			
//			myConsumerGrid = new Grid<BeanModel>(consumerListStore, new ColumnModel(configs));
//			myConsumerGrid.setHideHeaders(true);
//			consumerLayoutContainer.add(myConsumerGrid);
//			tbtmApplication.add(consumerLayoutContainer, new BorderLayoutData(LayoutRegion.WEST));
//			tabPanel.add(tbtmApplication);
			
			ToolBar toolBar = new ToolBar();
			toolBar.setHeight("32px");
			
			Button btnGotoStore = new Button();
			btnGotoStore.addListener(Events.Select,new Listener<ButtonEvent>() {

				@Override
				public void handleEvent(ButtonEvent be) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_APP_STORE_TO_PANEL);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			toolBar.add(btnGotoStore);
//			btnGotoStore.setIcon(((Images)Registry.get(Images.class.getName())).store());
			btnGotoStore.setText(((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_title());
//			btnGotoStore.addStyleName("btn-blue");
			btnGotoStore.setHeight("32px");
			setTopComponent(toolBar);
			
			//
			TabItem tbtmRegister = new TabItem(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_tabitem_register());
			tbtmRegister.setLayout(new FitLayout());
			
			LayoutContainer registerLayoutContainer = new LayoutContainer();
			registerLayoutContainer.setLayout(new FormLayout());
			
			cmbxCategory_1 = new ComboBox<BeanModel>();
			cmbxCategory_1.setStore(categoryListStore);
			cmbxCategory_1.setTriggerAction(TriggerAction.ALL);
			cmbxCategory_1.setEditable(false);
			cmbxCategory_1.setForceSelection(true);
			registerLayoutContainer.add(cmbxCategory_1, new FormData("80%"));
			cmbxCategory_1.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_category());
			cmbxCategory_1.setDisplayField("domain");
			
			txtfldName_1 = new TextField<String>();
			registerLayoutContainer.add(txtfldName_1, new FormData("80%"));
			txtfldName_1.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_name());
			txtfldName_1.setAllowBlank(false);
			
			txtfldVersion_1 = new TextField<String>();
			registerLayoutContainer.add(txtfldVersion_1, new FormData("80%"));
			txtfldVersion_1.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_version());
			
			txtfldAlias_1 = new TextField<String>();
			registerLayoutContainer.add(txtfldAlias_1, new FormData("80%"));
			txtfldAlias_1.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_alias());
			
			txtfldConnectUri_1 = new TextField<String>();
			registerLayoutContainer.add(txtfldConnectUri_1, new FormData("80%"));
			txtfldConnectUri_1.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_connectURI());
			txtfldConnectUri_1.setAllowBlank(false);
			
			MultiField mltfldIcon = new MultiField();
			
			FileUploadField flpldfldNewFileuploadfield_1 = new FileUploadField();
			mltfldIcon.add(flpldfldNewFileuploadfield_1);
			flpldfldNewFileuploadfield_1.setFieldLabel("New FileUploadField");
			registerLayoutContainer.add(mltfldIcon, new FormData("80%"));
			mltfldIcon.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_iconupload());
			
			ButtonBar buttonBar_1 = new ButtonBar();
			buttonBar_1.setAlignment(HorizontalAlignment.RIGHT);
			
			Button btnSubmit = new Button(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_button_submit());
			btnSubmit.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent ce) {
					AppEvent event = new AppEvent(ConsumerEvents.REGISTER);
					event.setData("category", cmbxCategory_1.getValue().get("category"));
					event.setData("name",txtfldName_1.getValue());
					event.setData("version",txtfldName_1.getValue());
					event.setData("alias",txtfldAlias_1.getValue());
					event.setData("connectURI", txtfldConnectUri_1.getValue());
					Dispatcher.get().dispatch(event);
				}
			});
			buttonBar_1.add(btnSubmit);
			registerLayoutContainer.add(buttonBar_1, new FormData("80%"));
			tbtmRegister.add(registerLayoutContainer, new FitData(20, 100, 20, 100));
			tabPanel.add(tbtmRegister);
			add(tabPanel);
		}
		
		public void loadConsumers(Collection<Consumer> consumers){
			categoryListStore.removeAll();
			for(Consumer c:consumers){
				BeanModel bm = consumerFactory.createModel(c);
				categoryListStore.add(bm);
			}
			
			categoryListStore.commitChanges();
		}
		
		public void loadCategories(List<BeanModel> categories){
			categoryListStore.removeAll();
			categoryListStore.add(categories);
			categoryListStore.commitChanges();
		}
	}
	
	
}
