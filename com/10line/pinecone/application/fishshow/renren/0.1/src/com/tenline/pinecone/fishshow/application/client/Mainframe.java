package com.tenline.pinecone.fishshow.application.client;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.util.Margins;

public class Mainframe extends Viewport {
	
	private String sessionKey = "";
	
	private MainContentPanel mainContentPanel;
	private InforPanel inforPanel;
	private FriendsPanel friendPanel;
	
	public Mainframe() {
		setLayout(new BorderLayout());
		
		friendPanel = new FriendsPanel();
		friendPanel.setHeading("好友名单");
		BorderLayoutData bld_cntntpnlNewContentpanel = new BorderLayoutData(LayoutRegion.EAST);
		bld_cntntpnlNewContentpanel.setMargins(new Margins(0, 0, 0, 5));
		bld_cntntpnlNewContentpanel.setHideCollapseTool(true);
		bld_cntntpnlNewContentpanel.setCollapsible(true);
		bld_cntntpnlNewContentpanel.setFloatable(false);
		bld_cntntpnlNewContentpanel.setSplit(true);
		friendPanel.setCollapsible(true);
		add(friendPanel, bld_cntntpnlNewContentpanel);
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new BorderLayout());
		
		inforPanel = new InforPanel();
		inforPanel.setHeading("实时信息");
		inforPanel.setCollapsible(true);
		BorderLayoutData bld_cntntpnlNewContentpanel_1 = new BorderLayoutData(LayoutRegion.SOUTH, 105.0f);
		bld_cntntpnlNewContentpanel_1.setMargins(new Margins(5, 0, 0, 0));
		bld_cntntpnlNewContentpanel_1.setSplit(true);
		bld_cntntpnlNewContentpanel_1.setCollapsible(true);
		layoutContainer.add(inforPanel, bld_cntntpnlNewContentpanel_1);
		
		mainContentPanel = new MainContentPanel();
		mainContentPanel.setHideCollapseTool(true);
		mainContentPanel.setHeading("真鱼秀");
		mainContentPanel.setCollapsible(true);
		BorderLayoutData bld_cntntpnlNewContentpanel_2 = new BorderLayoutData(LayoutRegion.CENTER);
		bld_cntntpnlNewContentpanel_2.setSplit(true);
		layoutContainer.add(mainContentPanel, bld_cntntpnlNewContentpanel_2);
		add(layoutContainer, new BorderLayoutData(LayoutRegion.CENTER));
		layoutContainer.setBorders(true);
	}
	
	public void setContent(String sKey){
		this.sessionKey = sKey;
		
		mainContentPanel.setContent(this.sessionKey);
		inforPanel.setContent(this.sessionKey);
		friendPanel.setContent(this.sessionKey);
	}

}
