package com.tenline.pinecone.fishshow.application.client;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class OxygenSetWindow extends Window {
	
	class ChItem extends BaseModelData {
		private static final long serialVersionUID = 1644782172788405109L;
		
		public ChItem(String name, String value){
			this.setName(name);
			this.setValue(value);
		}
		
		public void setName(String name){
			this.set("name", name);
		}
		
		public String getName(){
			return this.get("name");
		}
		
		public void setValue(String value) {
			this.set("value", value);
		}

		public String getValue() {
			return this.get("value");
		}
	}

	private ComboBox<ChItem> startComboBox;
	private ComboBox<ChItem> endComboBox;
	
	private DeviceServiceAsync dsa;
	
	private Map<String,String> vMap;

	public OxygenSetWindow() {
		init();
		
		setSize("210", "150");
		dsa = GWT.create(DeviceService.class);
		
		setHeading("设置制氧时间");
		setLayout(new TableLayout(2));

		Text txtNewText = new Text("启动时间");
		TableData td_txtNewText = new TableData();
		td_txtNewText.setHeight("40");
		td_txtNewText.setPadding(4);
		add(txtNewText, td_txtNewText);
		txtNewText.setWidth("");
		txtNewText.setHeight("");

		ListStore<ChItem> startStore = new ListStore<ChItem>();
		List<ChItem> sList = new ArrayList<ChItem>();
		sList.add(new ChItem("1","1"));
		sList.add(new ChItem("2","2"));
		sList.add(new ChItem("3","3"));
		startStore.add(sList);
		
		startComboBox = new ComboBox<ChItem>();
		startComboBox.setEmptyText("输入...");
		startComboBox.setTypeAhead(true);
		startComboBox.setStore(startStore);
		startComboBox.setDisplayField("name");
		add(startComboBox);
		startComboBox.setSize("", "");
		startComboBox.setFieldLabel("New ComboBox");

		Text txtNewText_1 = new Text("停止时间");
		TableData td_txtNewText_1 = new TableData();
		td_txtNewText_1.setPadding(4);
		td_txtNewText_1.setHeight("40");
		add(txtNewText_1, td_txtNewText_1);

		ListStore<ChItem> endStore = new ListStore<ChItem>();
		List<ChItem> eList = new ArrayList<ChItem>();
		eList.add(new ChItem("5","5"));
		eList.add(new ChItem("6","6"));
		eList.add(new ChItem("7","7"));
		eList.add(new ChItem("8","8"));
		eList.add(new ChItem("9","9"));
		eList.add(new ChItem("10","10"));
		endStore.add(eList);
		
		endComboBox = new ComboBox<ChItem>();
		endComboBox.setEmptyText("输入...");
		endComboBox.setTypeAhead(true);
		endComboBox.setStore(endStore);
		endComboBox.setDisplayField("name");
		add(endComboBox);
		endComboBox.setWidth("");
		endComboBox.setFieldLabel("New ComboBox");

		Button button = new Button("设置", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				String text = null;
				List<ChItem> list = startComboBox.getSelection();
				if(list != null){
					if(list.size() > 0){
						text = list.get(0).getName();
					}else{
						MessageBox.alert(null, "请选择制氧启动时间", null);
						return;
					}
				}else{
					MessageBox.alert(null, "请选择制氧启动时间", null);
					return;
				}
				
				list = endComboBox.getSelection();
				if(list != null){
					if(list.size() > 0){
						text = text+"-"+list.get(0).getName();
					}else{
						MessageBox.alert(null, "请选择制氧停止时间", null);
						return;
					}
				}else{
					MessageBox.alert(null, "请选择制氧停止时间", null);
					return;
				}
				
				for(String s:vMap.keySet()){
					if(s.endsWith(text)){
						dsa.setStatus("ah1zfjEwbGluZS1waW5lY29uZS13ZWItc2VydmljZXInCxIEVXNlchj7VQwLEgZEZXZpY2UYnicMCxIIVmFyaWFibGUY7TYM", vMap.get(s), new AsyncCallback<Void>(){

							@Override
							public void onFailure(Throwable caught) {
								MessageBox.alert(null, "发送指令失败", null);
							}

							@Override
							public void onSuccess(Void result) {
								MessageBox.info(null, "发送指令成功", null);
							}});
					}
				}
				
			}
		});
		addButton(button);
		
		addButton(new Button("关闭", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				hide();
			}
		}));
		
//		//Test
//		System.out.println("Testing!!!!!!!");
//		for(PineconeDevice pd:EnvConfig.getDevList().values()){
//			System.out.println("---------------------------------");
//			System.out.println("id="+pd.getId());
//			System.out.println("name="+pd.getName());
//			System.out.println("SymbolicName="+pd.getSymbolicName());
//			if(EnvConfig.getVarMap().get(pd.getId()) != null){
//				for(PineconeVariable pv:EnvConfig.getVarMap().get(pd.getId())){
//					System.out.println("  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//					System.out.println("id="+pv.getId());
//					System.out.println("name="+pv.getName());
//					System.out.println("type="+pv.getType());
//					if(EnvConfig.getItMap().get(pv.getId()) != null){
//						for(PineconeItem pi:EnvConfig.getItMap().get(pv.getId())){
//							System.out.println("    ****************************");
//							System.out.println("id="+pi.getId());
//							System.out.println("text="+pi.getText());
//							System.out.println("value="+pi.getValue());
//						}
//					}
//				}
//			}
//		}
		
	}
	
	private void init(){
		vMap = new LinkedHashMap<String,String>();
		vMap.put("1-7","291");
		vMap.put("3-9","813");
		vMap.put("1-6","286");
		vMap.put("2-7","547");
		vMap.put("2-9","557");
		vMap.put("3-8","808");
		vMap.put("1-10","306");
		vMap.put("2-8","552");
		vMap.put("2-10","562");
		vMap.put("3-5","793");
		vMap.put("3-6","798");
		vMap.put("1-9","301");
		vMap.put("2-5","537");
		vMap.put("1-8","296");
		vMap.put("2-6","542");
		vMap.put("3-10","818");
		vMap.put("1-5","281");
		vMap.put("3-7","803"); 	 
	}
	
	

}
