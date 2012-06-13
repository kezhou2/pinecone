package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.tenline.pinecone.platform.web.store.client.events.RegistryViewEvents;

public class RegistryView extends View {

	public RegistryView(Controller controller) {
		super(controller);
	}
	
	@Override
	protected void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(RegistryViewEvents.CREATE_USER)) {
				onCreateUser(event);
			}else if(event.getType().equals(RegistryViewEvents.CANCEL)){
				onCancel(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onCreateUser(AppEvent event){
		
	}
	
	public void onCancel(AppEvent event){
		
	}

}
