/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.google.gwt.user.client.Element;
import com.tenline.pinecone.platform.web.store.client.Images;

/**
 * @author Bill
 *
 */
public class HomeViewport extends Viewport {

	/**
	 * 
	 */
	public HomeViewport() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
	    setLayout(new BorderLayout());
	    
	    ContentPanel header = new ContentPanel(); 
	    header.setHeaderVisible(false);
	    header.setBorders(false);
	    header.setLayout(new CenterLayout());
	    header.setTopComponent(new HomeToolBar()); 
	    
	    ContentPanel headerPanel = new ContentPanel(); 
	    headerPanel.setHeaderVisible(false);
	    headerPanel.setBorders(false);
	    headerPanel.add(((Images) Registry.get(Images.class.getName())).logo().createImage());
	    header.add(headerPanel); 
	    
	    add(header, new BorderLayoutData(LayoutRegion.NORTH));  
	    add(new HomePortal(3), new BorderLayoutData(LayoutRegion.CENTER));
	}

}
