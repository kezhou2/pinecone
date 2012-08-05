package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
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
	
	public void loadConsumers(List<BeanModel> consumers){
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
		
		/**my consumer panel*/
		private ContentPanel myConsumerPanel = null;
		/**consumer registry panel*/
		private ContentPanel consumerRegistryPanel = null;
		/**panel to show info*/
		private ContentPanel container = null;
		/***/
		private CardLayout layout = null;
		/***/
		private BeanModelFactory consumerFactory = BeanModelLookup.get().getFactory(Consumer.class);
		private BeanModelFactory categoryFactory = BeanModelLookup.get().getFactory(Category.class);
		
		/**user registered consumer grid*/
//		private Grid<BeanModel> grid;
		
		public MainPanel(){
			setHeaderVisible(false);
			setLayout(new FitLayout());
			this.setBodyBorder(false);
			this.setBorders(false);
			
//			LayoutContainer mainlc = new LayoutContainer();
			ToolBar toolbar = new ToolBar();
			toolbar.addStyleName("abstractviewport-background");
			toolbar.setBorders(false);
			toolbar.setHeight("50px");
			this.setTopComponent(toolbar);
			
			LayoutContainer leftlc = new LayoutContainer();
			leftlc.setLayout(new FillLayout());			
			Button btnGotoStore = new Button();
			btnGotoStore.setHTML("<img class='btn-img-left' src='../images/icons/back.png'>"+
					((Messages) Registry.get(Messages.class.getName())).AppStoreViewport_title()+
					"</img>");
			btnGotoStore.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent e) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_APP_STORE_TO_PANEL);
					appEvent.setHistoryEvent(true);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			leftlc.add(btnGotoStore, new FillData(8,0,8,30));
			toolbar.add(leftlc);
			leftlc.setSize("130px","48px");
			btnGotoStore.setSize("100px","32px");
			btnGotoStore.addStyleName("abstractviewport-btn");
			
			FillToolItem fillToolItem = new FillToolItem();
			toolbar.add(fillToolItem);
			
			Button myConsumerBtn = new Button();
			myConsumerBtn.setHTML("<img class='btn-img-center' src='../images/icons/showreel.png' />");
			myConsumerBtn.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent e) {
					if(container.getItemCount() == 1){
						container.add(createConsumerRegistryPanel(),new FitData(5,40,5,40));
					}
					
					layout.setActiveItem(createMyConsumerPanel());
					
				}
			});
			myConsumerBtn.setSize("60px", "32px");
			myConsumerBtn.setTitle(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_tabitem_application());
			myConsumerBtn.addStyleName("abstractviewport-btn");
				
			Button consumerRegistryBtn = new Button();
			consumerRegistryBtn.setHTML("<img class='btn-img-center' src='../images/icons/credit-card.png' />");
			consumerRegistryBtn.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent e) {
					if(container.getItemCount() == 1){
						container.add(createConsumerRegistryPanel(),new FitData(5,40,5,40));
					}
					
					layout.setActiveItem(createConsumerRegistryPanel());
				}
			});
			consumerRegistryBtn.setSize("60px", "32px");
			consumerRegistryBtn.setTitle(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_tabitem_register());
			consumerRegistryBtn.addStyleName("abstractviewport-btn");
			
			LayoutContainer rightlc = new LayoutContainer();
			rightlc.setSize("280px","48px");
			HBoxLayout hbl_toolbarLayoutContainer = new HBoxLayout();
			hbl_toolbarLayoutContainer.setPack(BoxLayoutPack.END);
			hbl_toolbarLayoutContainer.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
			rightlc.setLayout(hbl_toolbarLayoutContainer);
			rightlc.add(myConsumerBtn, new HBoxLayoutData(8, 3, 8, 3));
			rightlc.add(consumerRegistryBtn, new HBoxLayoutData(8, 30, 8, 3));
			toolbar.add(rightlc);

			container = new ContentPanel();
			layout = new CardLayout();
			container.setLayout(layout);
			container.setHeaderVisible(false);
			container.setBodyBorder(false);
			container.setBorders(false);
			
			//create firstly
			createMyConsumerPanel();
			createConsumerRegistryPanel();
			
			container.add(createMyConsumerPanel(), new FitData(5,40,5,40));	
//			container.add(createConsumerRegistryPanel(), new FitData(5,40,5,40));
			layout.setActiveItem(myConsumerPanel);
			
			this.add(container,new FillData(10,30,10,30));
		}
		
		public ContentPanel createMyConsumerPanel(){
			if(myConsumerPanel == null){
				myConsumerPanel = new ContentPanel();
				myConsumerPanel.setHeading(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_tabitem_application());
				myConsumerPanel.setLayout(new BorderLayout());
				myConsumerPanel.getHeader().addStyleName("appstoreviewport-panel-header");
				myConsumerPanel.setBodyBorder(false);
				myConsumerPanel.setBorders(false);
				myConsumerPanel.getHeader().setBorders(false);
				myConsumerPanel.setBodyStyleName("appstoreviewport-panel");
				
				LayoutContainer layoutContainer = new LayoutContainer();
				FormLayout formLayout = new FormLayout();
				formLayout.setLabelWidth(60);
	//			formLayout.setLabelAlign(LabelAlign.TOP);
				layoutContainer.setLayout(formLayout);
				layoutContainer.setBorders(false);
				layoutContainer.addStyleName("appstoreviewport-input-panel");
				
				String labelStyle = "padding-top: 8px; color: #222;font-weight: bold;";
				
				txtfldName = new TextField<String>();
				FormData fd_txtfldName = new FormData("90%");
				fd_txtfldName.setMargins(new Margins(10, 0, 10, 0));
				layoutContainer.add(txtfldName, fd_txtfldName);
				txtfldName.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_name());
				txtfldName.setLabelStyle(labelStyle);
				txtfldName.setHeight("30px");
				
				cmbxCategory = new ComboBox<BeanModel>();
				cmbxCategory.setStore(categoryListStore);
				FormData fd_cmbxCategory = new FormData("90%");
				fd_cmbxCategory.setMargins(new Margins(10, 0, 10, 0));
				layoutContainer.add(cmbxCategory, fd_cmbxCategory);
				cmbxCategory.setDisplayField("domain");
				cmbxCategory.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_category());
				cmbxCategory.setLabelStyle(labelStyle);
				cmbxCategory.setHeight("30px");
				cmbxCategory.setTriggerStyle("trigger-arrow");
				
				txtfldKey = new TextField<String>();
				FormData fd_txtfldKey = new FormData("90%");
				fd_txtfldKey.setMargins(new Margins(10, 0, 10, 0));
				layoutContainer.add(txtfldKey, fd_txtfldKey);
				txtfldKey.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_key());
				txtfldKey.setLabelStyle(labelStyle);
				txtfldKey.setHeight("30px");
				
				txtfldSecret = new TextField<String>();
				FormData fd_txtfldSecret = new FormData("90%");
				fd_txtfldSecret.setMargins(new Margins(10, 0, 10, 0));
				layoutContainer.add(txtfldSecret, fd_txtfldSecret);
				txtfldSecret.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_secret());
				txtfldSecret.setLabelStyle(labelStyle);
				txtfldSecret.setHeight("30px");
				
				txtfldConnectUri = new TextField<String>();
				FormData fd_txtfldConnectUri = new FormData("90%");
				fd_txtfldConnectUri.setMargins(new Margins(10, 0, 10, 0));
				layoutContainer.add(txtfldConnectUri, fd_txtfldConnectUri);
				txtfldConnectUri.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_connectURI());
				txtfldConnectUri.setLabelStyle(labelStyle);
				txtfldConnectUri.setHeight("30px");
				
				txtfldAlias = new TextField<String>();
				FormData fd_txtfldAlias = new FormData("90%");
				fd_txtfldAlias.setMargins(new Margins(10, 0, 10, 0));
				layoutContainer.add(txtfldAlias, fd_txtfldAlias);
				txtfldAlias.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_alias());
				txtfldAlias.setLabelStyle(labelStyle);
				txtfldAlias.setHeight("30px");
				
				txtfldVersion = new TextField<String>();
				FormData fd_txtfldVersion = new FormData("90%");
				fd_txtfldVersion.setMargins(new Margins(10, 0, 10, 0));
				layoutContainer.add(txtfldVersion, fd_txtfldVersion);
				txtfldVersion.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_version());
				txtfldVersion.setLabelStyle(labelStyle);
				txtfldVersion.setHeight("30px");
				
				FileUploadField flpldfldNewFileuploadfield = new FileUploadField();
				FormData fd_txtfldUpload = new FormData("90%");
				fd_txtfldUpload.setMargins(new Margins(10, 0, 10, 0));
				layoutContainer.add(flpldfldNewFileuploadfield,fd_txtfldUpload);
				flpldfldNewFileuploadfield.setHeight("30px");
				flpldfldNewFileuploadfield.setFieldLabel("New FileUploadField");
				flpldfldNewFileuploadfield.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_iconupload());
				flpldfldNewFileuploadfield.setLabelStyle(labelStyle);
				
				ButtonBar buttonBar = new ButtonBar();
				buttonBar.setAlignment(HorizontalAlignment.RIGHT);
				
				LayoutContainer lc = new LayoutContainer();
				lc.setLayout(new FillLayout());
				lc.setSize("80px", "30px");
				Button saveButton = 
					new Button(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_button_save());
				lc.add(saveButton);
				saveButton.setStyleName("abstractviewport-btn");
				saveButton.setSize("80px", "30px");
				buttonBar.add(lc);
				
				layoutContainer.add(buttonBar, new FormData("90%"));
				BorderLayoutData bld_layoutContainer = new BorderLayoutData(LayoutRegion.CENTER);
				bld_layoutContainer.setMargins(new Margins(10, 20, 10, 20));
				myConsumerPanel.add(layoutContainer, bld_layoutContainer);
				layoutContainer.setBorders(true);
				
				LayoutContainer consumerLayoutContainer = new LayoutContainer();
				consumerLayoutContainer.setLayout(new FitLayout());
				List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
				
				ColumnConfig clmncnfgNewColumn = new ColumnConfig("name", "name", 180);
				configs.add(clmncnfgNewColumn);
				
				myConsumerGrid = new Grid<BeanModel>(consumerListStore, new ColumnModel(configs));
				myConsumerGrid.setHideHeaders(true);
				myConsumerGrid.setBorders(false);
				myConsumerGrid.addStyleName("appstoreviewport-panel");
				myConsumerGrid.addListener(Events.RowClick, new Listener<GridEvent>() {
					public void handleEvent(GridEvent e) {
						BeanModel bm = (BeanModel)e.getModel();
						try{
						txtfldName.setValue(bm.get("name").toString());
						
						txtfldKey.setValue(bm.get("key").toString());
						txtfldSecret.setValue(bm.get("secret").toString());
						txtfldConnectUri.setValue(bm.get("connectURI").toString());
						txtfldAlias.setValue(bm.get("alias").toString());
						txtfldVersion.setValue(bm.get("version").toString());
						
						BeanModel cate = (BeanModel)bm.get("category");
						
						for(BeanModel b:categoryListStore.getModels()){
							if(b.get("id").equals(cate.get("id"))){
								cmbxCategory.select(b);
								cmbxCategory.setValue(b);
							}
						}
						
						
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
				});
				consumerLayoutContainer.add(myConsumerGrid, new FillData(3,3,3,3));
				myConsumerPanel.add(consumerLayoutContainer, new BorderLayoutData(LayoutRegion.WEST));
			}
			return myConsumerPanel;
		}
		
		public ContentPanel createConsumerRegistryPanel(){
			if(this.consumerRegistryPanel == null){
				this.consumerRegistryPanel = new ContentPanel();
				this.consumerRegistryPanel.setHeading(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_tabitem_register());
				consumerRegistryPanel.setLayout(new FitLayout());
				consumerRegistryPanel.getHeader().addStyleName("abstractviewport-panel-header");
				consumerRegistryPanel.setBodyBorder(false);
				consumerRegistryPanel.setBorders(false);
				consumerRegistryPanel.getHeader().setBorders(false);
				consumerRegistryPanel.getHeader().addStyleName("appstoreviewport-panel-header");
				consumerRegistryPanel.addStyleName("appstoreviewport-panel");
				
				String labelStyle = "padding-top: 8px; color: #222;font-weight: bold;";
				
				LayoutContainer registerLayoutContainer = new LayoutContainer();
				FormLayout formLayout1 = new FormLayout();
				formLayout1.setLabelWidth(60);
				formLayout1.setLabelAlign(LabelAlign.TOP);
				registerLayoutContainer.setLayout(formLayout1);
				registerLayoutContainer.addStyleName("appstoreviewport-input-panel");
				
				cmbxCategory_1 = new ComboBox<BeanModel>();
				cmbxCategory_1.setStore(categoryListStore);
				cmbxCategory_1.setTriggerAction(TriggerAction.ALL);
				cmbxCategory_1.setEditable(false);
				cmbxCategory_1.setForceSelection(true);
				registerLayoutContainer.add(cmbxCategory_1, new FormData("90%"));
				cmbxCategory_1.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_category());
				cmbxCategory_1.setDisplayField("domain");
				cmbxCategory_1.setLabelStyle(labelStyle);
				cmbxCategory_1.setHeight("30px");
				cmbxCategory_1.setTriggerStyle("trigger-arrow");
				
				txtfldName_1 = new TextField<String>();
				registerLayoutContainer.add(txtfldName_1, new FormData("90%"));
				txtfldName_1.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_name());
				txtfldName_1.setAllowBlank(false);
				txtfldName_1.setLabelStyle(labelStyle);
				txtfldName_1.setHeight("30px");
				
				txtfldVersion_1 = new TextField<String>();
				registerLayoutContainer.add(txtfldVersion_1, new FormData("90%"));
				txtfldVersion_1.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_version());
				txtfldVersion_1.setLabelStyle(labelStyle);
				txtfldVersion_1.setHeight("30px");
				
				txtfldAlias_1 = new TextField<String>();
				registerLayoutContainer.add(txtfldAlias_1, new FormData("90%"));
				txtfldAlias_1.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_alias());
				txtfldAlias_1.setLabelStyle(labelStyle);
				txtfldAlias_1.setHeight("30px");
				
				txtfldConnectUri_1 = new TextField<String>();
				registerLayoutContainer.add(txtfldConnectUri_1, new FormData("90%"));
				txtfldConnectUri_1.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_connectURI());
				txtfldConnectUri_1.setAllowBlank(false);
				txtfldConnectUri_1.setLabelStyle(labelStyle);
				txtfldConnectUri_1.setHeight("30px");
				
				FileUploadField flpldfldNewFileuploadfield = new FileUploadField();
				FormData fd_txtfldUpload = new FormData("90%");
				fd_txtfldUpload.setMargins(new Margins(10, 0, 10, 0));
				registerLayoutContainer.add(flpldfldNewFileuploadfield,fd_txtfldUpload);
				flpldfldNewFileuploadfield.setHeight("30px");
				flpldfldNewFileuploadfield.setFieldLabel("New FileUploadField");
				flpldfldNewFileuploadfield.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_label_iconupload());
				flpldfldNewFileuploadfield.setLabelStyle(labelStyle);
				
				ButtonBar buttonBar = new ButtonBar();
				buttonBar.setAlignment(HorizontalAlignment.RIGHT);
				
				LayoutContainer lc = new LayoutContainer();
				lc.setLayout(new FillLayout());
				lc.setSize("80px", "30px");
				Button saveButton = 
					new Button(((Messages) Registry.get(Messages.class.getName())).ConsumerRegistryViewport_button_submit());
				saveButton.addMouseUpHandler(new MouseUpHandler() {
					public void onMouseUp(MouseUpEvent e) {
						AppEvent event = new AppEvent(ConsumerEvents.REGISTER);
						event.setData("category", cmbxCategory_1.getValue().get("category"));
						event.setData("name",txtfldName_1.getValue());
						event.setData("version",txtfldName_1.getValue());
						event.setData("alias",txtfldAlias_1.getValue());
						event.setData("connectURI", txtfldConnectUri_1.getValue());
						event.setHistoryEvent(true);
						Dispatcher.get().dispatch(event);
					}
				});
				lc.add(saveButton);
				saveButton.setStyleName("abstractviewport-btn");
				saveButton.setSize("80px", "30px");
				buttonBar.add(lc);
				registerLayoutContainer.add(buttonBar, new FormData("90%"));
				consumerRegistryPanel.add(registerLayoutContainer, new FitData(20, 100, 20, 100));
			}
			return this.consumerRegistryPanel;
		}
		
		public void loadConsumers(List<BeanModel> consumers){
			consumerListStore.removeAll();
			consumerListStore.add(consumers);
			consumerListStore.commitChanges();
		}
		
		public void loadCategories(List<BeanModel> categories){
			categoryListStore.removeAll();
			categoryListStore.add(categories);
			categoryListStore.commitChanges();
		}
	}
	
	
}
