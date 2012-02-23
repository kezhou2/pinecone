/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * 
 * @author Bill
 *
 */
public class Store implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		RootPanel.get().add(new HomePage());	
	}
	
}
