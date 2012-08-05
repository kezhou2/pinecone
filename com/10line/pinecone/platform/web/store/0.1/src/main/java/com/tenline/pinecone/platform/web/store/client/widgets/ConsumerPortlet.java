package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class ConsumerPortlet extends Portlet {

	/**application entity*/
	private Application application;
	/***/
	private ToolButton toolButtonClose;
	/***/
	private ToolButton toolButtonMax;
	
	public ConsumerPortlet(Application application){

		this.application = application;
		
		this.setHeading(application.getConsumer().getName());
		this.setUrl(application.getConsumer().getConnectURI());
		
		toolButtonMax = new ToolButton("x-tool-maximize",
				new SelectionListener<IconButtonEvent>() {

			@Override
			public void componentSelected(IconButtonEvent ce) {
				AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_MAX_PORTLET_TO_PANEL);
				appEvent.setData("application", ConsumerPortlet.this.application);
				appEvent.setHistoryEvent(true);
				Dispatcher.get().dispatch(appEvent);
			}
		});
		this.getHeader().addTool(toolButtonMax);
		toolButtonClose = new ToolButton("x-tool-close");
		toolButtonClose.addSelectionListener(new SelectionListener<IconButtonEvent>() {
			@Override
			public void componentSelected(IconButtonEvent ce) {
				AppEvent appEvent = new AppEvent(ApplicationEvents.SETTING);
				appEvent.setData("application", ConsumerPortlet.this.application);
				appEvent.setData("status", Application.CLOSED);
				appEvent.setData("default", false);
				appEvent.setHistoryEvent(true);
				Dispatcher.get().dispatch(appEvent);
			}

		});
		this.getHeader().addTool(toolButtonClose);
	}
}
