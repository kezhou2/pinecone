/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;

/**
 * 
 * @author Bill
 *
 */
public class HomePage extends LayoutContainer {
	
	private Images images = GWT.create(Images.class);
	private Messages messages = GWT.create(Messages.class);
	
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
	    setLayout(new BorderLayout());
	    
	    ToolBar toolBar = new ToolBar();
	    toolBar.add(new ToggleButton(messages.home(), images.home()));
	    toolBar.add(new SeparatorToolItem());  
	    
	    ContentPanel navigation = new ContentPanel(); 
	    navigation.setHeaderVisible(false);
	    navigation.setBorders(false);
	    navigation.setLayout(new CenterLayout());
	    navigation.setTopComponent(toolBar); 
	    
	    ContentPanel panel = new ContentPanel(); 
	    panel.setHeaderVisible(false);
	    panel.setBorders(false);
	    panel.add(images.logo().createImage());
	    panel.add(new Label(messages.logoText()));
	    navigation.add(panel);
	  
	    Portal portal = new Portal(3);   
	    portal.setColumnWidth(0, .33);  
	    portal.setColumnWidth(1, .33);  
	    portal.setColumnWidth(2, .33);  
	  
	    Portlet portlet = new Portlet();  
	    portlet.setHeading("Grid in a Portlet");  
	    portlet.setLayout(new FitLayout());  
	    portlet.setHeight(250);  
	    portal.add(portlet, 0);  
	  
	    portlet = new Portlet();  
	    portlet.setHeading("Another Panel 1");
	    portal.add(portlet, 0);  
	  
	    portlet = new Portlet();  
	    portlet.setHeading("Panel 2");  
	    portal.add(portlet, 1);  
	  
	    portlet = new Portlet();  
	    portlet.setHeading("Another Panel 2");  
	    portal.add(portlet, 1);  
	  
	    portlet = new Portlet();  
	    portlet.setHeading("Panel 3");    
	    portal.add(portlet, 2);  
	    
	    add(navigation, new BorderLayoutData(LayoutRegion.NORTH, 250));  
	    add(portal, new BorderLayoutData(LayoutRegion.CENTER));
	}
	
}