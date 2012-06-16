/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;

/**
 * @author Bill
 *
 */
public abstract class AbstractViewport extends Viewport {
	
	protected ContentPanel body = new ContentPanel();

	/**
	 * 
	 */
	public AbstractViewport() {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		body.setIcon(((Images) Registry.get(Images.class.getName())).logo());
		body.setLayout(new BorderLayout());
		add(body, new BorderLayoutData(LayoutRegion.CENTER));
		add(new Footer(), new BorderLayoutData(LayoutRegion.SOUTH, 50));
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class Footer extends ContentPanel {
		
		private Footer() {
			setHeaderVisible(false);
			setLayout(new CenterLayout());
			add(new LabelField(((Messages) Registry.get(Messages.class.getName())).copyright()));
		}
		
	}

}
