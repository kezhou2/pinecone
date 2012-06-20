package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.google.gwt.user.client.ui.Image;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.button.IconButton;
import com.extjs.gxt.ui.client.widget.button.Button;

public class UserHeader extends ContentPanel {
	public UserHeader() {
		setHeaderVisible(false);
		setSize("200", "60");
		setLayout(new BorderLayout());
		
		LayoutContainer portraitLayoutContainer = new LayoutContainer();
		portraitLayoutContainer.setLayout(new FitLayout());
		
		Image portraitImage = new Image((String) null);
		portraitLayoutContainer.add(portraitImage);
		add(portraitLayoutContainer, new BorderLayoutData(LayoutRegion.EAST, 60.0f));
		
		LayoutContainer layoutContainer_1 = new LayoutContainer();
		layoutContainer_1.setLayout(new FillLayout(Orientation.VERTICAL));
		
		LayoutContainer nameLayoutContainer = new LayoutContainer();
		HBoxLayout hbl_nameLayoutContainer = new HBoxLayout();
		hbl_nameLayoutContainer.setPack(BoxLayoutPack.END);
		hbl_nameLayoutContainer.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
		nameLayoutContainer.setLayout(hbl_nameLayoutContainer);
		
		Text nameTxt = new Text("Guest");
		nameLayoutContainer.add(nameTxt, new HBoxLayoutData(3, 3, 3, 3));
		
		Text statusTxt = new Text("[Status]");
		nameLayoutContainer.add(statusTxt, new HBoxLayoutData(3, 3, 3, 3));
		layoutContainer_1.add(nameLayoutContainer);
		
		LayoutContainer configLayoutContainer = new LayoutContainer();
		HBoxLayout hbl_configLayoutContainer = new HBoxLayout();
		hbl_configLayoutContainer.setPack(BoxLayoutPack.END);
		hbl_configLayoutContainer.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
		configLayoutContainer.setLayout(hbl_configLayoutContainer);
		
		Button configBtn = new Button("Setting");
		configLayoutContainer.add(configBtn, new HBoxLayoutData(2, 2, 2, 2));
		
		Button btnLogout = new Button("logout");
		configLayoutContainer.add(btnLogout, new HBoxLayoutData(2, 2, 2, 2));
		layoutContainer_1.add(configLayoutContainer);
		add(layoutContainer_1, new BorderLayoutData(LayoutRegion.CENTER));
	}

}
