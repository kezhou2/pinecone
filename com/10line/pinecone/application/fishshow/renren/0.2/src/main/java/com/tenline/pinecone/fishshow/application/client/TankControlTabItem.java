package com.tenline.pinecone.fishshow.application.client;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.fishshow.application.client.service.DeviceService;
import com.tenline.pinecone.fishshow.application.client.service.DeviceServiceAsync;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.layout.TableRowLayout;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.util.Margins;

public class TankControlTabItem extends TabItem {
	
	private FieldSet statusFieldset;
	private FieldSet paramFieldSet;
	private Device device;
	private DeviceServiceAsync dsa;
	
	private Map<Variable,Text> tfMap = new LinkedHashMap<Variable,Text>();
	private Map<Variable,ComboBox<ChItem>> cbMap = new LinkedHashMap<Variable,ComboBox<ChItem>>();
	private Map<Button,Variable> bMap = new LinkedHashMap<Button,Variable>();
	
	public TankControlTabItem() {
		setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer_1 = new LayoutContainer();
		layoutContainer_1.setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new RowLayout(Orientation.VERTICAL));
		
		statusFieldset = new FieldSet();
		TableLayout tl_statusFieldset = new TableLayout(2);
		tl_statusFieldset.setCellPadding(3);
		statusFieldset.setLayout(tl_statusFieldset);
		
//		Text txtNewText = new Text("New Text");
//		statusFieldset.add(txtNewText);
//		
//		TextField txtfldNewTextfield = new TextField();
//		statusFieldset.add(txtfldNewTextfield);
//		txtfldNewTextfield.setFieldLabel("New TextField");
		layoutContainer.add(statusFieldset, new RowData(Style.DEFAULT, Style.DEFAULT, new Margins(5, 3, 5, 3)));
		statusFieldset.setHeading("当前状态");
		statusFieldset.setCollapsible(true);
		
		paramFieldSet = new FieldSet();
		TableLayout tl_paramFieldSet = new TableLayout(3);
		tl_paramFieldSet.setCellPadding(3);
		paramFieldSet.setLayout(tl_paramFieldSet);
		
//		Text txtNewText = new Text("New Text");
//		paramFieldSet.add(txtNewText);
//		
//		ComboBox cmbxNewCombobox = new ComboBox();
//		cmbxNewCombobox.setStore(new ListStore());
//		paramFieldSet.add(cmbxNewCombobox);
//		cmbxNewCombobox.setFieldLabel("New ComboBox");
//		
//		Button btnNewButton = new Button("New Button");
//		btnNewButton.addListener(Events.Select, new Listener<ButtonEvent>() {
//			public void handleEvent(ButtonEvent e) {
//			}
//		});
//		paramFieldSet.add(btnNewButton);
		layoutContainer.add(paramFieldSet, new RowData(Style.DEFAULT, Style.DEFAULT, new Margins(5, 3, 5, 3)));
		paramFieldSet.setHeading("参数设置");
		paramFieldSet.setCollapsible(true);
		BorderLayoutData bld_layoutContainer = new BorderLayoutData(LayoutRegion.EAST,300);
		bld_layoutContainer.setMargins(new Margins(3, 3, 3, 3));
		layoutContainer_1.add(layoutContainer, bld_layoutContainer);
		layoutContainer.setBorders(true);
		
		LayoutContainer layoutContainer_2 = new LayoutContainer();
		layoutContainer_2.setLayout(new RowLayout(Orientation.VERTICAL));
		
		LayoutContainer layoutContainer_3 = new LayoutContainer();
		layoutContainer_3.setLayout(new FitLayout());
		
		HtmlContainer htmlContainer = new HtmlContainer("<center><img src='../img/fishTank.jpg'></center>");
		layoutContainer_3.add(htmlContainer);
		layoutContainer_2.add(layoutContainer_3, new RowData(460.0, 350.0, new Margins()));
		layoutContainer_3.setBorders(true);
		BorderLayoutData bld_layoutContainer_2 = new BorderLayoutData(LayoutRegion.CENTER);
		bld_layoutContainer_2.setMargins(new Margins(3, 3, 3, 3));
		layoutContainer_1.add(layoutContainer_2, bld_layoutContainer_2);
		layoutContainer_2.setBorders(true);
		add(layoutContainer_1, new BorderLayoutData(LayoutRegion.CENTER));
		layoutContainer_1.setBorders(true);
		dsa = GWT.create(DeviceService.class);
		
		Timer timer = new Timer(){
			@Override
			public void run() {
				for(Variable v:tfMap.keySet()){
//					System.out.println("device id:"+device.getId());
					dsa.getStatus(v.getType(), device.getId(), new AsyncCallback<Device>(){
	
						public void onFailure(Throwable caught) {
							System.out.println("Failure in getStatus");
						}
	
						public void onSuccess(Device result) {
							if(result == null){
								for(Text t: tfMap.values()){							
									t.setText("--");
								}
								return;
							}
							
							if(result.getVariables().size() > 0){
								for(Variable var:result.getVariables()){
									for(Variable vv: tfMap.keySet()){
//										System.out.println("get Variable id:"+var.getId());
//										System.out.println("database Variable id:"+vv.getId());
										if(vv.getId().equals(var.getId())){
											if(var.getItems().size() > 0){
												for(Item it:var.getItems()){
													tfMap.get(vv).setText(it.getText());
												}
											}
										}
									}
								}
							}
						}
					});
				}
			}
		};
		timer.scheduleRepeating(6000);
		
	}
	
	public void setDevice(Device dev){
		this.device = dev;
		
		tfMap.clear();
		dsa.getVariable(dev.getId(), new AsyncCallback<Variable[]>(){

			public void onFailure(Throwable caught) {
				System.out.println("Failure in getVariable");
			}

			public void onSuccess(Variable[] result) {
				for(final Variable v:result){
					if(v.getType().equals(ConstDict.VAR_TYPE_WRITE_READ_DISCRETE)){
						Text text = new Text(v.getName());
						statusFieldset.add(text);
						
						Text t = new Text();
						t.setBorders(true);
						statusFieldset.add(t);
						t.setText("--");
						tfMap.put(v, t);
						statusFieldset.layout(true);
						
						Text text_1 = new Text(v.getName());
						paramFieldSet.add(text_1);
						
						ComboBox<ChItem> cb = new ComboBox<ChItem>();
//						cb.setStore(new ListStore<ChItem>());
						cb.setEmptyText("Loading...");
						cb.setEnabled(false);
						paramFieldSet.add(cb);
						cbMap.put(v, cb);
						final Button b = new Button("设置");
						paramFieldSet.add(b);
						bMap.put(b, v);
						b.addListener(Events.Select, new Listener<ButtonEvent>() {
							public void handleEvent(ButtonEvent e) {
								Variable var = bMap.get(b);
								ComboBox<ChItem> box = cbMap.get(var);
								List<ChItem> list = box.getSelection();
								if(list != null){
									if(list.size() <= 0){
										MessageBox.alert(null, "请选择"+var.getName(), null);
										return;
									}else{
										Device d = new Device();
										d.setId(device.getId());
										d.setName(device.getName());
										d.setSymbolicName(device.getSymbolicName());
										d.setVersion(device.getVersion());
										List<Variable> vs = new ArrayList<Variable>();
										Variable vv = new Variable();
										vv.setId(v.getId());
										vv.setName(v.getName());
										vv.setType(v.getType());
										vs.add(vv);
										
										List<Item> its = new ArrayList<Item>();
										its.add(list.get(0).getItem());
										vv.setItems(its);
										d.setVariables(vs);
										
										dsa.setStatus(device.getId(), d, new AsyncCallback<Void>(){

											public void onFailure(
													Throwable caught) {
												System.out.println("Failure in setStatus");
											}

											public void onSuccess(Void result) {
												System.out.println(result);
											}});
									}
								}else{
									MessageBox.alert(null, "请选择制"+var.getName(), null);
									return;
								}
								
								
							}
						});
						
						
						dsa.getItem(v.getId(), new AsyncCallback<Item[]>(){

							public void onFailure(Throwable caught) {
								System.out.println("Failure in getItem");
							}


							public void onSuccess(Item[] result) {
								ListStore<ChItem> store = new ListStore<ChItem>();
								List<ChItem> list = new ArrayList<ChItem>();
								for(Item it:result){
									ChItem ci = new ChItem(it);
									list.add(ci);
									System.out.println("combobox:"+ci.getName());
								}
								
								store.add(list);
								
								for(Variable var:cbMap.keySet()){
									if(var.getId().equals(v.getId())){
										System.out.println("Set:"+var.getName());
										cbMap.get(var).setStore(store);
										cbMap.get(var).setTypeAhead(true);
										cbMap.get(var).setDisplayField("name");
										cbMap.get(var).setTriggerAction(TriggerAction.ALL);
										cbMap.get(var).setEmptyText("Please enter...");
										cbMap.get(var).setEnabled(true);
										break;
									}
								}
								//需要在重画界面前判断ComboBox是否设置了Store
								boolean _isNeedLayout = true;
								for(ComboBox _comboBox:cbMap.values()){
									if(_comboBox.getStore() == null){
										_isNeedLayout = false;
									}
								}
								if(_isNeedLayout){
									paramFieldSet.layout(true);
								}
							}
							
						});
					}else if(v.getType().equals(ConstDict.VAR_TYPE_READ_DISCRETE)){
						Text text = new Text(v.getName());
						statusFieldset.add(text);
						
						Text t = new Text();
						t.setBorders(true);
						statusFieldset.add(t);	
						t.setText("--");
						tfMap.put(v, t);
						statusFieldset.layout(true);
						
					}else if(v.getType().equals(ConstDict.VAR_TYPE_WRITE_DISCRETE)){
						Text text = new Text(v.getName());
						paramFieldSet.add(text);
						
						ComboBox<ChItem> cb = new ComboBox<ChItem>();
//						cb.setStore(new ListStore<ChItem>());
						cb.setEmptyText("Loading...");
						cb.setEnabled(false);
						paramFieldSet.add(cb);
						cbMap.put(v, cb);
						final Button b = new Button("设置");
						paramFieldSet.add(b);
						bMap.put(b, v);
						b.addListener(Events.Select, new Listener<ButtonEvent>() {
							public void handleEvent(ButtonEvent e) {
								Variable var = bMap.get(b);
								ComboBox<ChItem> box = cbMap.get(var);
								List<ChItem> list = box.getSelection();
								if(list != null){
									if(list.size() <= 0){
										MessageBox.alert(null, "请选择"+var.getName(), null);
										return;
									}else{
										Device d = new Device();
										d.setId(device.getId());
										d.setName(device.getName());
										d.setSymbolicName(device.getSymbolicName());
										d.setVersion(device.getVersion());
										List<Variable> vs = new ArrayList<Variable>();
										Variable vv = new Variable();
										vv.setId(v.getId());
										vv.setName(v.getName());
										vv.setType(v.getType());
										vs.add(vv);
										
										List<Item> its = new ArrayList<Item>();
										its.add(list.get(0).getItem());
										vv.setItems(its);
										d.setVariables(vs);
										
										dsa.setStatus(device.getId(), d, new AsyncCallback<Void>(){

											public void onFailure(
													Throwable caught) {
												System.out.println("Failure in setStatus");
											}

											public void onSuccess(Void result) {
												System.out.println(result);
											}});
									}
								}else{
									MessageBox.alert(null, "请选择制"+var.getName(), null);
									return;
								}
								
								
							}
						});
						
						
						dsa.getItem(v.getId(), new AsyncCallback<Item[]>(){

							public void onFailure(Throwable caught) {
								System.out.println("Failure in getItem");
							}

							public void onSuccess(Item[] result) {
								ListStore<ChItem> store = new ListStore<ChItem>();
								List<ChItem> list = new ArrayList<ChItem>();
								for(Item it:result){
									ChItem ci = new ChItem(it);
									list.add(ci);
									System.out.println("combobox:"+ci.getName());
								}
								store.add(list);
								
								for(Variable var:cbMap.keySet()){
									if(var.getId().equals(v.getId())){
										System.out.println("Set:"+var.getName());
										cbMap.get(var).setStore(store);
										cbMap.get(var).setTypeAhead(true);
										cbMap.get(var).setDisplayField("name");
										cbMap.get(var).setTriggerAction(TriggerAction.ALL);
										cbMap.get(var).setEmptyText("Please enter...");
										cbMap.get(var).setEnabled(true);
										break;
									}
								}
								//需要在重画界面前判断ComboBox是否设置了Store
								boolean _isNeedLayout = true;
								for(ComboBox _comboBox:cbMap.values()){
									if(_comboBox.getStore() == null){
										_isNeedLayout = false;
									}
								}
								if(_isNeedLayout){
									paramFieldSet.layout(true);
								}
							}
							
						});
					}
				}
			}
			
		});
	}

}
