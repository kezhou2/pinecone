/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;

/**
 * @author Bill
 *
 */
public abstract class AbstractViewport extends Viewport {
	
	protected Header header = new Header();
	protected Body body = new Body();
	protected Footer footer = new Footer();

	/**
	 * 
	 */
	public AbstractViewport() {
		setLayout(new BorderLayout());
		this.setStyleAttribute("background-color","transparent");
		
		BorderLayoutData headerBld = new BorderLayoutData(LayoutRegion.NORTH, 60);
		headerBld.setMargins(new Margins(0,0,0,0));
		add(header, headerBld);
		add(body, new BorderLayoutData(LayoutRegion.CENTER));
		add(footer, new BorderLayoutData(LayoutRegion.SOUTH, 50));
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	protected class Header extends ContentPanel {
		
		protected Header() {
			setHeaderVisible(false);
			setLayout(new BorderLayout());
			Image logo = ((Images) Registry.get(Images.class.getName())).logo().createImage();
			add(logo, new BorderLayoutData(LayoutRegion.WEST,184));
			
//			setBodyStyle("background-color: transparent");
			this.setBodyStyleName("abstractviewport-header");
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	protected class Body extends ContentPanel {
		
		protected Body() {
			setHeaderVisible(false);
			
			BorderLayout bl = new BorderLayout();
			setLayout(bl);
			setBorders(false);
			setBodyBorder(false);
			this.setBodyStyleName("abstractviewport-background");
			
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	protected class Footer extends ContentPanel {
		
		protected Footer() {
			setHeaderVisible(false);
			setLayout(new BorderLayout());
			LayoutContainer copyrightContainer = new LayoutContainer(new CenterLayout());
			copyrightContainer.add(new LabelField(((Messages) Registry.get(Messages.class.getName())).copyright()));
			add(copyrightContainer, new BorderLayoutData(LayoutRegion.CENTER));
			setBorders(false);
			setBodyBorder(false);
			copyrightContainer.setStyleName("abstractviewport-footer");
//			copyrightContainer.setStyleAttribute("background-color","transparent");
		}
		
	}

}
