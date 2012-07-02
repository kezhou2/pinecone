package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class ConsumerShowViewport extends AbstractViewport {

	private MainPanel mainPanel;
	
	public ConsumerShowViewport(){
		mainPanel = new MainPanel();
		body.add(mainPanel, new BorderLayoutData(LayoutRegion.CENTER));
	}
	
	public void loadConsumer(Application application){
		mainPanel.loadConsumer(application);
	}
	
	private class MainPanel extends ContentPanel{
		
		public MainPanel(){
			this.setHeaderVisible(false);
			
			ToolBar toolBar = new ToolBar();
			Button btnGotoHome = new Button(((Messages) Registry.get(Messages.class.getName())).HomeViewport_title());
			btnGotoHome.addListener(Events.Select,new Listener<ButtonEvent>() {

				@Override
				public void handleEvent(ButtonEvent be) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_USERHOME_TO_PANEL);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			toolBar.add(btnGotoHome);
			this.setTopComponent(toolBar);
		}
		
		public void loadConsumer(Application application){
			this.setUrl(application.getConsumer().getConnectURI());
		}
	}
}
