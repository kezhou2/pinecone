package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerServiceAsync;

public class ApplicationInfoPanel extends ContentPanel {
	
	private Image image;
	private Text infoText;
	private Button openButton;
	private Button removeButton;
	private Button settingButton;
	private BeanModel model;
	
	public ApplicationInfoPanel(BeanModel model) {
		this.model = model;
		setSize("300", "64");
		setLayout(new BorderLayout());
		this.setHeaderVisible(false);
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new FitLayout());
		
		image = new Image((String) null);
		layoutContainer.add(image, new FitData(2));
		add(layoutContainer, new BorderLayoutData(LayoutRegion.WEST, 64.0f));
		
		LayoutContainer layoutContainer_1 = new LayoutContainer();
		layoutContainer_1.setLayout(new FillLayout(Orientation.VERTICAL));
		
		LayoutContainer layoutContainer_2 = new LayoutContainer();
		layoutContainer_2.setLayout(new FitLayout());
		
		infoText = new Text("");
		layoutContainer_2.add(infoText);
		layoutContainer_1.add(layoutContainer_2);
		
		LayoutContainer layoutContainer_3 = new LayoutContainer();
		layoutContainer_3.setLayout(new HBoxLayout());
		
		openButton = new Button("Open");
		layoutContainer_3.add(openButton);
		openButton.setWidth("60");
		
		removeButton = new Button("Remove");
		layoutContainer_3.add(removeButton);
		removeButton.setWidth("60");
		
		settingButton = new Button("Setting");
		layoutContainer_3.add(settingButton);
		settingButton.setWidth("60");
		layoutContainer_1.add(layoutContainer_3);
		add(layoutContainer_1, new BorderLayoutData(LayoutRegion.CENTER));
		
		init();
	}
	
	public void init(){
		try{
			BeanModel consumer = (BeanModel)model.get("consumer");
			infoText.setText((String)consumer.get("name"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
}
