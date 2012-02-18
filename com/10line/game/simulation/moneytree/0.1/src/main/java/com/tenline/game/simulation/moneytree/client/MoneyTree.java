package com.tenline.game.simulation.moneytree.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.model.User;

/**
 * @author Bill
 */
public class MoneyTree implements EntryPoint {
	
	private final MoneyTreeServiceAsync service = GWT.create(MoneyTreeService.class);
	private final Messages messages = GWT.create(Messages.class);

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		Button button = new Button(messages.sendButton());
		RootPanel.get().add(button);
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				User user = new User();
				user.setName("bill");
				user.setNut(20);
				service.plantTree(user, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						System.out.println(messages.planted());
					}
					
				});
			}
			
		});
	}
	  
}
