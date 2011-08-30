package com.tenline.pinecone.fishshow.application.client.window;

import java.util.LinkedHashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.fishshow.application.client.service.DeviceService;
import com.tenline.pinecone.fishshow.application.client.service.DeviceServiceAsync;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;

public class TankStatusWindow extends Window {
	
	private Timer refreshTimer;
	private DeviceServiceAsync dsa;
	private String snsId;
	private Device device;
	private Variable[] vars;
	private Map<String, Item[]> items = new LinkedHashMap<String, Item[]>();
	
	private Text statusText;
	private Text oxygenText;
	private Text temperText;
	
	public TankStatusWindow(String id) {
		snsId = id;
		
		dsa = GWT.create(DeviceService.class);
		
		//initialize
		dsa.getUser(snsId, new AsyncCallback<User>(){

			public void onFailure(Throwable caught) {
				System.out.println("Failure in get user info");
			}

			public void onSuccess(User result) {
				if(result == null){
					return;
				}
				
				if(snsId != null){
					dsa.getDevice(result.getId(), new AsyncCallback<Device[]>(){

						public void onFailure(Throwable caught) {
							System.out.println("Failure in get device info.");
						}

						public void onSuccess(Device[] result) {
							if(result != null){
								if(result.length > 0){
									device = result[0];
									dsa.getVariable(device.getId(), new AsyncCallback<Variable[]>(){

										public void onFailure(Throwable caught) {
											System.out.println("Failure in get device info.");
										}

										public void onSuccess(
												Variable[] result) {
											vars = result;
										}});
								}
							}
							
						}});
				}
				
			}});
		
		setSize("170", "120");
		setHeading("鱼缸状态");
		TableLayout tableLayout = new TableLayout(2);
		tableLayout.setCellSpacing(2);
		tableLayout.setCellPadding(2);
		tableLayout.setCellVerticalAlign(VerticalAlignment.MIDDLE);
		tableLayout.setCellHorizontalAlign(HorizontalAlignment.CENTER);
		setLayout(tableLayout);
		
		LabelField lblfldNewLabelfield = new LabelField("鱼缸状态");
		add(lblfldNewLabelfield);
		
		statusText = new Text();
		statusText.setText("--");
		add(statusText);
		statusText.setWidth("90");
		
		LabelField lblfldNewLabelfield_1 = new LabelField("制氧频率");
		add(lblfldNewLabelfield_1);
		
		oxygenText = new Text();
		oxygenText.setText("--");
		add(oxygenText);
		oxygenText.setWidth("90");
		
		LabelField lblfldNewLabelfield_2 = new LabelField("鱼缸温度");
		add(lblfldNewLabelfield_2);
		
		temperText = new Text();
		temperText.setText("--");
		add(temperText);
		temperText.setWidth("90");
		
		this.refreshTimer = new Timer(){
			@Override
			public void run() {
				System.out.println("refresh");
				if(vars != null){
//					for(Variable pv:vars){
//						if(pv.getId().equals("ah1zfjEwbGluZS1waW5lY29uZS13ZWItc2VydmljZXImCxIEVXNlchj7VQwLEgZEZXZpY2UYAQwLEghWYXJpYWJsZRjpBww")){
//							dsa.getStatus(null, pv.getId(), new AsyncCallback<String>(){
//								
//								@Override
//								public void onFailure(Throwable caught) {
//									MessageBox.prompt("Error", "Failure in get ah1zfjEwbGluZS1waW5lY29uZS13ZWItc2VydmljZXImCxIEVXNlchj7VQwLEgZEZXZpY2UYAQwLEghWYXJpYWJsZRjpBww Status");
//								}
//	
//								@Override
//								public void onSuccess(String result) {
//									System.out.println("1"+result);
//									statusText.setText(result);
//								}});
//						}else if(pv.getId().equals("ah1zfjEwbGluZS1waW5lY29uZS13ZWItc2VydmljZXImCxIEVXNlchj7VQwLEgZEZXZpY2UYAQwLEghWYXJpYWJsZRihHww")){
//							dsa.getStatus(null, pv.getId(), new AsyncCallback<String>(){
//								
//								@Override
//								public void onFailure(Throwable caught) {
//									MessageBox.prompt("Error","Failure in get ah1zfjEwbGluZS1waW5lY29uZS13ZWItc2VydmljZXImCxIEVXNlchj7VQwLEgZEZXZpY2UYAQwLEghWYXJpYWJsZRihHww Status");
//									
//								}
//	
//								@Override
//								public void onSuccess(String result) {
//									System.out.println("2"+result);
//									oxygenText.setText(result);
//								}});
//						}else if(pv.getId().equals("ah1zfjEwbGluZS1waW5lY29uZS13ZWItc2VydmljZXImCxIEVXNlchj7VQwLEgZEZXZpY2UYAQwLEghWYXJpYWJsZRjZNgw")){
//							dsa.getStatus(null, pv.getId(), new AsyncCallback<String>(){
//								
//								@Override
//								public void onFailure(Throwable caught) {
//									MessageBox.prompt("Error","Failure in get ah1zfjEwbGluZS1waW5lY29uZS13ZWItc2VydmljZXImCxIEVXNlchj7VQwLEgZEZXZpY2UYAQwLEghWYXJpYWJsZRjZNgw Status");
//								}
//	
//								@Override
//								public void onSuccess(String result) {
//									System.out.println("3"+result);
//									temperText.setText(result);
//								}});
//						}	
//					}
				}
			}		
		};
		this.refreshTimer.scheduleRepeating(3000);
	}

	@Override
	protected void onHide() {
		if(this.refreshTimer != null)
			this.refreshTimer.cancel();
		super.onHide();
	}
	
	

}
