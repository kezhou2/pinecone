package com.tenline.pinecone.fishshow.application.client;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.IconAlign;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonGroup;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.fishshow.application.client.service.RenrenApi;
import com.tenline.pinecone.fishshow.application.client.service.RenrenApiAsync;
import com.tenline.pinecone.fishshow.application.client.window.OxygenSetWindow;
import com.tenline.pinecone.fishshow.application.client.window.TankStatusWindow;
import com.tenline.pinecone.fishshow.application.client.window.TemperatureSetWindow;
	
public class ControlPortlet extends Portlet {
	
	private String sessionKey = "";
	private String snsID = "";
	
	private RenrenApiAsync rapi;
	
	private Button upBtn;
	private Button downBtn; 
	private Button leftBtn;
	private Button rightBtn;
	
	public ControlPortlet() {
		rapi = GWT.create(RenrenApi.class);
		
		setSize("120px", "190px");
		setHeaderVisible(false);
		setLayout(new BorderLayout());
		
		TabPanel tabPanel = new TabPanel();
		
		TabItem tbtmNewTabitem_1 = new TabItem("鱼缸");
		TableLayout tl_tbtmNewTabitem_1 = new TableLayout(1);
		tl_tbtmNewTabitem_1.setWidth("110");
		tl_tbtmNewTabitem_1.setCellPadding(2);
		tl_tbtmNewTabitem_1.setCellHorizontalAlign(HorizontalAlignment.CENTER);
		tl_tbtmNewTabitem_1.setCellVerticalAlign(VerticalAlignment.MIDDLE);
		tbtmNewTabitem_1.setLayout(tl_tbtmNewTabitem_1);
		
		ButtonGroup buttonGroup = new ButtonGroup(1);
		TableLayout tableLayout = (TableLayout) buttonGroup.getLayout();
		tableLayout.setCellPadding(2);
		tableLayout.setCellVerticalAlign(VerticalAlignment.MIDDLE);
		tableLayout.setCellHorizontalAlign(HorizontalAlignment.CENTER);
		buttonGroup.setHeading("状态");
		
		Button btnNewButton = new Button("查询状态");
		btnNewButton.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				TankStatusWindow w = new TankStatusWindow(snsID);
				w.show();
			}
		});
		buttonGroup.add(btnNewButton);
		btnNewButton.setSize("59", "22");
		TableData td_buttonGroup = new TableData();
		td_buttonGroup.setHorizontalAlign(HorizontalAlignment.CENTER);
		tbtmNewTabitem_1.add(buttonGroup, td_buttonGroup);
		buttonGroup.setSize("70", "56");
		
		ButtonGroup buttonGroup_1 = new ButtonGroup(2);
		TableLayout tableLayout_1 = (TableLayout) buttonGroup_1.getLayout();
		tableLayout_1.setCellPadding(2);
		tableLayout_1.setColumns(1);
		buttonGroup_1.setHeading("控制令");
		
		Button btnNewButton_1 = new Button("供氧频率");
		btnNewButton_1.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				OxygenSetWindow w = new OxygenSetWindow();
				w.show();
			}
		});
		buttonGroup_1.add(btnNewButton_1);
		btnNewButton_1.setSize("59", "22");
		
		Button btnNewButton_3 = new Button("鱼缸温度");
		btnNewButton_3.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				TemperatureSetWindow w = new TemperatureSetWindow();
				w.show();
			}
		});
		buttonGroup_1.add(btnNewButton_3);
		btnNewButton_3.setSize("59", "22");
		tbtmNewTabitem_1.add(buttonGroup_1);
		buttonGroup_1.setSize("70", "82");
		tabPanel.add(tbtmNewTabitem_1);
		tbtmNewTabitem_1.setSize("110", "150");
		
		TabItem tbtmNewTabitem = new TabItem("摄像头");
		tbtmNewTabitem.setLayout(new TableLayout(1));
		
		ButtonGroup buttonGroup_2 = new ButtonGroup(3);
		buttonGroup_2.setHeading("方向控制");
		buttonGroup_2.setLayout(new TableLayout(3));
		buttonGroup_2.add(new Text());
		
		upBtn = new Button();
		upBtn.setIcon(EnvConfig.ICONS.upArrow());
		upBtn.setHeight(24);
		upBtn.setWidth(24);
		upBtn.setSize("32", "32");
		buttonGroup_2.add(upBtn);
		buttonGroup_2.add(new Text());
		
		leftBtn = new Button();
		leftBtn.setIcon(EnvConfig.ICONS.leftArrow());
		leftBtn.setHeight(24);
		leftBtn.setWidth(24);
		leftBtn.setSize("32", "32");
		buttonGroup_2.add(leftBtn);
		buttonGroup_2.add(new Text());
		
		rightBtn = new Button();
		rightBtn.setIcon(EnvConfig.ICONS.rightArrow());
		rightBtn.setHeight(24);
		rightBtn.setWidth(24);
		rightBtn.setSize("32", "32");
		buttonGroup_2.add(rightBtn);
		buttonGroup_2.add(new Text());
		
		downBtn = new Button();
		downBtn.setIcon(EnvConfig.ICONS.downArrow());
		downBtn.setHeight(24);
		downBtn.setWidth(24);
		downBtn.setSize("32", "32");
		buttonGroup_2.add(downBtn);
		buttonGroup_2.add(new Text());
		tbtmNewTabitem.add(buttonGroup_2);
		
//		upBtn = new Button("<image src='../img/up_arrow.png' />");
//		upBtn.setHeight(24);
//		upBtn.setWidth(24);
//		TableData td_upBtn = new TableData();
//		td_upBtn.setMargin(2);
//		td_upBtn.setHorizontalAlign(HorizontalAlignment.CENTER);
//		td_upBtn.setVerticalAlign(VerticalAlignment.MIDDLE);
//		td_upBtn.setWidth("24");
//		td_upBtn.setHeight("24");
//		tbtmNewTabitem.add(upBtn, td_upBtn);
//		upBtn.setSize("32", "32");
//		tbtmNewTabitem.add(new Text());
//		
//		leftBtn = new Button("<image src='../img/left_arrow.png' />");
//		TableData td_leftBtn = new TableData();
//		td_leftBtn.setWidth("24");
//		td_leftBtn.setVerticalAlign(VerticalAlignment.MIDDLE);
//		td_leftBtn.setHorizontalAlign(HorizontalAlignment.CENTER);
//		td_leftBtn.setHeight("24");
//		tbtmNewTabitem.add(leftBtn, td_leftBtn);
//		leftBtn.setSize("32", "32");
//		tbtmNewTabitem.add(new Text());
//		
//		rightBtn = new Button("<image src='../img/right_arrow.png' />");
//		TableData td_rightBtn = new TableData();
//		td_rightBtn.setWidth("24");
//		td_rightBtn.setVerticalAlign(VerticalAlignment.MIDDLE);
//		td_rightBtn.setHorizontalAlign(HorizontalAlignment.CENTER);
//		td_rightBtn.setHeight("24");
//		tbtmNewTabitem.add(rightBtn, td_rightBtn);
//		rightBtn.setSize("32", "32");
//		tbtmNewTabitem.add(new Text());
//		
//		downBtn = new Button("<image src='../img/down_arrow.png' />");
//		downBtn.setIconAlign(IconAlign.TOP);
//		downBtn.setWidth(24);
//		downBtn.setHeight(24);
//		TableData td_downBtn = new TableData();
//		td_downBtn.setWidth("24");
//		td_downBtn.setVerticalAlign(VerticalAlignment.MIDDLE);
//		td_downBtn.setHorizontalAlign(HorizontalAlignment.CENTER);
//		td_downBtn.setHeight("24");
//		tbtmNewTabitem.add(downBtn, td_downBtn);
//		downBtn.setSize("32", "32");
//		tbtmNewTabitem.add(new Text());
		tabPanel.add(tbtmNewTabitem);
		add(tabPanel, new BorderLayoutData(LayoutRegion.CENTER));
	}

	public void setContent(String sKey){
		this.sessionKey = sKey;
		
		rapi.getLoggedInUser(sessionKey, new AsyncCallback<Integer>(){

			public void onFailure(Throwable caught) {
				Window.alert("Failure getLoggedInUser. sessionKey=" + sessionKey);
			}

			public void onSuccess(Integer result) {
				snsID = ""+result;	
			}});
	}
	
}
