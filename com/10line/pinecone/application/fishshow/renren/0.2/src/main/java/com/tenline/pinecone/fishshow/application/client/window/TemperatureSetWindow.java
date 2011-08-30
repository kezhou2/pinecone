package com.tenline.pinecone.fishshow.application.client.window;

import java.util.ArrayList;
import java.util.List;

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
import com.tenline.pinecone.fishshow.application.client.ChItem;
import com.tenline.pinecone.fishshow.application.client.service.DeviceService;
import com.tenline.pinecone.fishshow.application.client.service.DeviceServiceAsync;

public class TemperatureSetWindow extends Window {

	private ComboBox<ChItem> tempComboBox;
	
	private DeviceServiceAsync dsa;
	
	private List<ChItem> vList;

	public TemperatureSetWindow() {
		init();
		setSize("210", "120");
		dsa = GWT.create(DeviceService.class);
		
		setHeading("设置鱼缸温度");
		setLayout(new TableLayout(2));

		Text txtNewText = new Text("鱼缸温度");
		TableData td_txtNewText = new TableData();
		td_txtNewText.setHeight("50");
		td_txtNewText.setPadding(4);
		add(txtNewText, td_txtNewText);
		txtNewText.setWidth("");
		txtNewText.setHeight("");

		ListStore<ChItem> tempStore = new ListStore<ChItem>();
		tempStore.add(vList);
		
		tempComboBox = new ComboBox<ChItem>();
		tempComboBox.setEmptyText("输入...");
		tempComboBox.setTypeAhead(true);
		tempComboBox.setStore(tempStore);
		tempComboBox.setDisplayField("name");
		add(tempComboBox);
		tempComboBox.setSize("", "");
		tempComboBox.setFieldLabel("New ComboBox");


		Button button = new Button("设置", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				String text = null;
				List<ChItem> list = tempComboBox.getSelection();
				if(list != null){
					if(list.size() > 0){
						text = list.get(0).getName();
					}else{
						MessageBox.alert(null, "请选择鱼缸温度", null);
						return;
					}
				}else{
					MessageBox.alert(null, "请选择鱼缸温度", null);
					return;
				}
				
				for(ChItem c:vList){
					if(c.getName().endsWith(text)){
//						dsa.setStatus("ah1zfjEwbGluZS1waW5lY29uZS13ZWItc2VydmljZXInCxIEVXNlchj7VQwLEgZEZXZpY2UYnicMCxIIVmFyaWFibGUYok4M", c.getValue(), new AsyncCallback<Void>(){
//
//							@Override
//							public void onFailure(Throwable caught) {
//								MessageBox.alert(null, "发送指令失败", null);
//							}
//
//							@Override
//							public void onSuccess(Void result) {
//								MessageBox.info(null, "发送指令成功", null);
//							}});
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
		vList = new ArrayList<ChItem>();

		vList.add(new ChItem("12℃","23810"));
		vList.add(new ChItem("13℃","21250"));
		vList.add(new ChItem("14℃","18690"));
		vList.add(new ChItem("15℃","16642"));
		vList.add(new ChItem("16℃","14082"));
		vList.add(new ChItem("17℃","11010"));
		vList.add(new ChItem("18℃","7938"));
		vList.add(new ChItem("19℃","5122"));
		vList.add(new ChItem("20℃","2050"));
		vList.add(new ChItem("21℃","64001"));
		vList.add(new ChItem("22℃","60417"));
		vList.add(new ChItem("23℃","57089"));
		vList.add(new ChItem("24℃","53505"));
		vList.add(new ChItem("25℃","49665"));
		vList.add(new ChItem("26℃","46593"));
		vList.add(new ChItem("27℃","43777"));
		vList.add(new ChItem("28℃","41217"));
		vList.add(new ChItem("29℃","38657"));
		vList.add(new ChItem("30℃","48385"));

	}
}
