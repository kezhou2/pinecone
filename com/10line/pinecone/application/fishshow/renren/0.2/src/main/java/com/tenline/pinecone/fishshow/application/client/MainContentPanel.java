package com.tenline.pinecone.fishshow.application.client;

import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TreeEvent;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.fishshow.application.client.service.DeviceService;
import com.tenline.pinecone.fishshow.application.client.service.DeviceServiceAsync;
import com.tenline.pinecone.fishshow.application.client.service.RenrenApi;
import com.tenline.pinecone.fishshow.application.client.service.RenrenApiAsync;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class MainContentPanel extends ContentPanel {
	private String sessionKey = "";
	private String snsId = "";
	
	private UserPortlet userPortlet;
	private CameraPortlet cameraPortlet;
	private ControlPortlet controlPortlet;
	
	private UserPanel userPanel;
	
	private HtmlContainer userInfoContainer;
	private Text welcomeText;
	private LayoutContainer menuLayoutContainer;
	private LayoutContainer deviceLayoutContainer;
	private TabPanel deviceTabPanel;
	
	/**动态显示用户当前拥有的设备*/
	private Tree deviceTree;
	/**用于防止deviceTree的Container,根据用户的设备数量动态设置高度*/
	ContentPanel myDeviceTreePanel;
	/**后台设备服务接口*/
	private DeviceServiceAsync dsa;
	/**人人网服务接口*/
	private RenrenApiAsync rapi;
	
	public MainContentPanel() {
		
		dsa = GWT.create(DeviceService.class);
		rapi = GWT.create(RenrenApi.class);
		
		setCollapsible(true);
		setLayout(new BorderLayout());
		
		ToolBar toolBar = new ToolBar ();
		
		Button operButton = new Button("操作");
		toolBar.add(operButton);
		Menu menu = new Menu();  
		
		MenuItem menuItem = new MenuItem("拍照");
		menu.add(menuItem);
		
		MenuItem menuItem_1 = new MenuItem("录像");
		menu.add(menuItem_1);
		
		MenuItem menuItem_2 = new MenuItem("购物车");
		menu.add(menuItem_2);
		operButton.setMenu(menu);  
		
		Button btnNewButton = new Button("互动");
		toolBar.add(btnNewButton);
		
		Menu menu_1 = new Menu();
		
		MenuItem menuItem_3 = new MenuItem("邀请");
		menu_1.add(menuItem_3);
		
		MenuItem menuItem_4 = new MenuItem("评论");
		menu_1.add(menuItem_4);
		
		MenuItem menuItem_5 = new MenuItem("分享");
		menu_1.add(menuItem_5);
		btnNewButton.setMenu(menu_1);
		
		Button button = new Button("帮助");
		toolBar.add(button);
		setTopComponent(toolBar);
		
		menuLayoutContainer = new LayoutContainer();
		menuLayoutContainer.setLayout(new RowLayout(Orientation.VERTICAL));
		
		userInfoContainer = new HtmlContainer("<center><img src='../img/contact.jpg'/></center>");
		menuLayoutContainer.add(userInfoContainer, new RowData(Style.DEFAULT, Style.DEFAULT, new Margins(5, 2, 2, 2)));
		
		welcomeText = new Text("<center>你好！</center>");
		menuLayoutContainer.add(welcomeText, new RowData(Style.DEFAULT, Style.DEFAULT, new Margins(2, 2, 2, 2)));
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new AccordionLayout());
		
		myDeviceTreePanel = new ContentPanel();
		myDeviceTreePanel.setHeading("我的设备");
		myDeviceTreePanel.setCollapsible(true);
		myDeviceTreePanel.setLayout(new FillLayout(Orientation.HORIZONTAL));
		myDeviceTreePanel.setExpanded(false);
		
		deviceTree = new Tree();
		deviceTree.addListener(Events.KeyPress, new Listener<TreeEvent>() {
			public void handleEvent(TreeEvent e) {
				List<TreeItem> list = e.getSelected();
				if(list != null){
					if(list.size() == 0){
						return;
					}else{
						TreeItem ti = list.get(0);
						ti.getText();
					}
				}
			}
		});
		
//		TreeItem trtmNewTreeitem = new TreeItem("New TreeItem");
//		trtmNewTreeitem.setExpanded(true);
//		deviceTree.getRootItem().add(trtmNewTreeitem);
		myDeviceTreePanel.add(deviceTree);
		deviceTree.setBorders(true);
		
		layoutContainer.add(myDeviceTreePanel);
		myDeviceTreePanel.setHeight("100");
		menuLayoutContainer.add(layoutContainer);
		layoutContainer.setHeight("");
		layoutContainer.setBorders(true);
		add(menuLayoutContainer, new BorderLayoutData(LayoutRegion.WEST,150));
		menuLayoutContainer.setBorders(true);
		
		deviceLayoutContainer = new LayoutContainer();
		deviceLayoutContainer.setLayout(new FitLayout());
		
		deviceTabPanel = new TabPanel();
		
//		TestTabItem tti = new TestTabItem();
//		tti.setText("Test");
//		deviceTabPanel.add(tti);
//		CameraTabItem cameraTabItem = new CameraTabItem();
//		cameraTabItem.setText("摄像头");
//		deviceTabPanel.add(cameraTabItem);
		deviceLayoutContainer.add(deviceTabPanel);
		add(deviceLayoutContainer, new BorderLayoutData(LayoutRegion.CENTER));
		deviceLayoutContainer.setBorders(true);
		
//		menuLayoutContainer = new LayoutContainer();
//		menuLayoutContainer.setLayout(new FillLayout(Orientation.VERTICAL));
		
//		HtmlContainer htmlContainer = new HtmlContainer("<center><img src='../img/contact.jpg'/></center>");
//		menuLayoutContainer.add(htmlContainer);
//		add(menuLayoutContainer, new BorderLayoutData(LayoutRegion.WEST,150));
//		menuLayoutContainer.setBorders(true);
//		
//		deviceLayoutContainer = new LayoutContainer();
//		add(deviceLayoutContainer, new BorderLayoutData(LayoutRegion.CENTER));
//		deviceLayoutContainer.setBorders(true);
		
//		Portal portal = new Portal(2);
//		portal.setColumnWidth(0, 0.2);
//		portal.setColumnWidth(1, 0.8);
//		
//		userPortlet = new UserPortlet();
//		userPortlet.setHeading("用户");
//		userPortlet.setCollapsible(true);
//		portal.add(userPortlet, 0);
//		
//		controlPortlet = new ControlPortlet();
//		controlPortlet.setHeading("控制");
//		controlPortlet.setCollapsible(true);
//		portal.add(controlPortlet, 0);
//		
//		cameraPortlet = new CameraPortlet();
//		cameraPortlet.setHeading("视频");
//		cameraPortlet.setCollapsible(true);
//		portal.add(cameraPortlet, 1);
//		add(portal);
	}

	
	public void setContent(String sKey){
		this.sessionKey = sKey;
//		this.userPanel.setContent(sessionKey);
//		userPortlet.setContent(this.sessionKey);
		
		//reset user info widget;
		rapi.getLoggedInUser(this.sessionKey, new AsyncCallback<Integer>(){

			public void onFailure(Throwable caught) {
				System.out.println("Failure in getLoggedInUser");
			}

			public void onSuccess(Integer result) {
				snsId = ""+result;
				
				dsa.getUser(snsId,new AsyncCallback<User>(){

					public void onFailure(Throwable caught) {
						System.out.println("Failure in getUser in MainContentPanel");
					}

					public void onSuccess(User result) {
						//获取设备信息，并重画相应的控件,有多少设备，在左侧的树中添加多少设备节点
						dsa.getDevice(result.getId(), new AsyncCallback<Device[]>(){
							public void onFailure(Throwable caught) {
								System.out.println("Failure in getDevice");						
							}

							public void onSuccess(Device[] result) {
								if(result == null){
									System.out.println("No Device");
									return;
								}
								
								for(final Device d:result){
									EnvConfig.addDevie(d);
									
									TreeItem deviceItem = new TreeItem(d.getName());
									deviceItem.setExpanded(true);
									deviceTree.getRootItem().add(deviceItem);
									myDeviceTreePanel.setHeight(""+(myDeviceTreePanel.getHeight()+28));
									//TODO 初始化设备的panel
									if(d.getSymbolicName().equals("com.10line.pinecone.platform.osgi.device.efish")){
										TankControlTabItem tcti = new TankControlTabItem();
										tcti.setText(d.getName());
										deviceTabPanel.add(tcti);
										tcti.setDevice(d);
									}else if(d.getSymbolicName().equals("")){
										CameraTabItem cti = new CameraTabItem();
										deviceTabPanel.add(cti);
									}
									
									
//									dsa.getVariable(d.getId(), new AsyncCallback<Variable[]>(){
		//
//										@Override
//										public void onFailure(Throwable caught) {
//											System.out.println("Failure in getVariable");
//										}
		//
//										@Override
//										public void onSuccess(Variable[] result) {
//											for(final Variable v:result){
//												EnvConfig.addVariable(d.getId(), v);
//												
//												dsa.getItem(v.getId(), new AsyncCallback<Item[]>(){
		//
//													@Override
//													public void onFailure(
//															Throwable caught) {
//														System.out.println("Failure in getItem");
//													}
		//
//													@Override
//													public void onSuccess(Item[] result) {
//														for(Item it:result){
//															EnvConfig.addItem(v.getId(), it);
//														}
//														//TODO 初始化设备控制项的参数值
//														
//													}
//													
//												});
//											}
//										}
//										
//									});
								}
							}
							
						});
						
					}
				
				
				
				});
				
				//获取当前用户登录的信息，并对控件进行重画
				rapi.getInfo(sessionKey, result.toString(), "uid,name,headurl,tinyurl,mainurl", new AsyncCallback<String>(){

					public void onFailure(Throwable caught) {
						Window.alert("Failure in getLoggedInUser");
						
					}

					public void onSuccess(String result) {
						
						JSONArray ja = JSONParser.parseLenient(result).isArray();
						if(ja != null){
							JSONObject o = (JSONObject)ja.get(0);
							if(o != null){
								JSONString url = o.get("headurl").isString();
								userInfoContainer.setHtml("<center><img src='"+url.stringValue()+"'/></center>");
								JSONString name = o.get("name").isString();			
								welcomeText.setText("<center>"+name.stringValue().toString()+",欢迎回来！</center>");
								return;
							}
						}
						Window.alert("Failure in set portrait.");
					}
				});
			}
		});
		
		//reset my devices menu
//		dsa.getDevice(userId, callback)
	}
	
}
