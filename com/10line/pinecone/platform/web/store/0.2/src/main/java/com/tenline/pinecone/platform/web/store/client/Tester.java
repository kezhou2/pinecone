package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class Tester extends ContentPanel {
	public Tester() {
		setLayout(new FitLayout());
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer_1 = new LayoutContainer();
		layoutContainer_1.setLayout(new FitLayout());
		
		Text txtHelloThere = new Text("Hello there");
		layoutContainer_1.add(txtHelloThere);
		layoutContainer.add(layoutContainer_1, new BorderLayoutData(LayoutRegion.SOUTH, 30.0f));
		layoutContainer_1.setBorders(true);
		add(layoutContainer);
		layoutContainer.setSize("448", "300");
		layoutContainer.setBorders(true);
	}

}
