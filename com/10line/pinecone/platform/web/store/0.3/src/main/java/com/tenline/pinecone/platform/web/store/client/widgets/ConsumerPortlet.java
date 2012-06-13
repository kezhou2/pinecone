package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerServiceAsync;

public class ConsumerPortlet extends Portlet {

	private Application application;
	private ToolButton toolButtonClose;
	private ToolButton toolButtonMax;
	
	public ConsumerPortlet(Application application){

		this.application = application;
		
		ConsumerServiceAsync service = Registry.get(ConsumerService.class.getName());
		service.show("id=='"+application.getConsumer().getId()+"'", new AsyncCallback<Collection<Consumer>>(){

			@Override
			public void onFailure(Throwable arg0) {
				Info.display("Error", arg0.getMessage());
			}

			@Override
			public void onSuccess(Collection<Consumer> arg0) {
				if(arg0.size() == 0){
					Info.display("Error", "Failure in load cunsumer by application");
				}
				for(Consumer con:arg0){
					ConsumerPortlet.this.setHeading(con.getName());
					ConsumerPortlet.this.setUrl(con.getConnectURI());
					break;
				}
			}
			
		});
		
		toolButtonMax = new ToolButton("x-tool-maximize",
				new SelectionListener<IconButtonEvent>() {

			@Override
			public void componentSelected(IconButtonEvent ce) {
				
			}
		});
		this.getHeader().addTool(toolButtonMax);
		toolButtonClose = new ToolButton("x-tool-close");
		toolButtonClose.addSelectionListener(new SelectionListener<IconButtonEvent>() {
			@Override
			public void componentSelected(IconButtonEvent ce) {
				
				
				
				toolButtonClose.setEnabled(false);
			}

				});
		this.getHeader().addTool(toolButtonClose);
	}
}
