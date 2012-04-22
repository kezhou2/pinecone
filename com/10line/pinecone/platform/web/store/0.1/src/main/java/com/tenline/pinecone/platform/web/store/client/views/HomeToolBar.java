/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;
import com.tenline.pinecone.platform.web.store.client.Messages;

/**
 * @author Bill
 *
 */
public class HomeToolBar extends ToolBar {

	/**
	 * 
	 */
	public HomeToolBar() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		Button loginUserButton = new Button(((Messages) Registry.get(Messages.class.getName())).login());
		loginUserButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
		Button registerUserButton = new Button(((Messages) Registry.get(Messages.class.getName())).register());
		loginUserButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
		add(loginUserButton);
	    add(new SeparatorToolItem()); 
	    add(registerUserButton);
	}

}
