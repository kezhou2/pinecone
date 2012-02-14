package com.tenline.pinecone.platform.web.store.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AppStore implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
//		Access access = new Access();
//		access.set("file", "../resources/themes/access/css/xtheme-access.css");
//		ThemeManager.register(access);
//		GXT.setDefaultTheme(access, true);
		
//		RootPanel.get().add(new LoginPage());
		RootPanel.get().add(new HomePage());
//		RootPanel.get().add(new Tester());
	}
}
