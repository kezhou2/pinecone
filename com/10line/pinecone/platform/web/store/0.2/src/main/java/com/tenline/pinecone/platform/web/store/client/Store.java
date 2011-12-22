package com.tenline.pinecone.platform.web.store.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Store implements EntryPoint {


  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
	  RootPanel.get().add(new HomePage());
  }
}
