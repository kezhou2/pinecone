package com.tenline.pinecone.fishshow.application.client;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class MainContentPanel extends ContentPanel {
	private String sessionKey = "";
	
	private UserPortlet userPortlet;
	private CameraPortlet cameraPortlet;
	private ControlPortlet controlPortlet;
	
	public MainContentPanel() {
		setLayout(new FillLayout(Orientation.VERTICAL));
		
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
		
		Portal portal = new Portal(2);
		portal.setColumnWidth(0, 0.2);
		portal.setColumnWidth(1, 0.8);
		
		userPortlet = new UserPortlet();
		userPortlet.setHeading("用户");
		userPortlet.setCollapsible(true);
		portal.add(userPortlet, 0);
		
		controlPortlet = new ControlPortlet();
		controlPortlet.setHeading("控制");
		controlPortlet.setCollapsible(true);
		portal.add(controlPortlet, 0);
		
		cameraPortlet = new CameraPortlet();
		cameraPortlet.setHeading("视频");
		cameraPortlet.setCollapsible(true);
		portal.add(cameraPortlet, 1);
		add(portal);
	}

	
	public void setContent(String sKey){
		this.sessionKey = sKey;
		userPortlet.setContent(this.sessionKey);
	}
}
