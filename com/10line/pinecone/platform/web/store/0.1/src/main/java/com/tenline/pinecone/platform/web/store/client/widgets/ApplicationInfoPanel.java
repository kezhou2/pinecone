package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class ApplicationInfoPanel extends ContentPanel {
	
	private Image image;
	private Text infoText;
	private Button openButton;
	private Button removeButton;
	private BeanModel model;
	
	public ApplicationInfoPanel(BeanModel model) {
		this.model = model;
		setSize("225", "54");
		setLayout(new BorderLayout());
		setHeaderVisible(false);
		setBodyBorder(false);
		setBorders(false);
//		this.setStyleAttribute("background", "#FFFFFF");
		setBodyStyle("background-color: transparent");
//		this.setStyleAttribute("color", "#FFFFFF");
//		this.setStyleAttribute("filter", "Alpha(Opacity=10, Style=0)");
//		this.setStyleAttribute("opacity", "0.10");
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new FitLayout());
		
		image = ((Images)Registry.get(Images.class.getName())).application().createImage();
		layoutContainer.add(image, new FitData(2));
		add(layoutContainer, new BorderLayoutData(LayoutRegion.WEST, 54.0f));
		
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
		openButton.setStyleName("btn-blue");
		openButton.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				AppEvent appEvent = new AppEvent(ApplicationEvents.SETTING);
				appEvent.setData("application", ApplicationInfoPanel.this.model.get("application"));
				appEvent.setData("status", Application.OPENED);
				appEvent.setData("default", false);
				Dispatcher.get().dispatch(appEvent);
			}
		});
		
		removeButton = new Button("Remove");
		layoutContainer_3.add(removeButton);
		removeButton.setWidth("60");
		removeButton.setStyleName("btn-blue");
		removeButton.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				System.out.println("ApplicationInfoPanel uninstall");
				AppEvent appEvent = new AppEvent(ApplicationEvents.UNINSTALL);
				appEvent.setData("id", ApplicationInfoPanel.this.model.get("id"));
				Dispatcher.get().dispatch(appEvent);
			}
		});
		
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
