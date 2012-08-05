package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class ConsumerShowViewport extends AbstractViewport {

	private MainPanel mainPanel;
	
	public ConsumerShowViewport(){
		mainPanel = new MainPanel();
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(10,10,10,10));
		body.add(mainPanel, bld);
	}
	
	public void loadConsumer(Application application){
		mainPanel.loadConsumer(application);
	}
	
	private class MainPanel extends ContentPanel{
		
		public MainPanel(){
			this.setHeaderVisible(false);
			this.setBodyBorder(false);
			this.setBorders(false);
			this.getHeader().addStyleName("header-title");
			this.getHeader().addStyleName("appstoreviewport-panel-header");
			this.addStyleName("appstoreviewport-panel");
			
			ToolBar toolBar = new ToolBar();
			LayoutContainer lc = new LayoutContainer();
			lc.setLayout(new FitLayout());
			Button btnGotoHome = new Button();
			btnGotoHome.setHTML("<img class='btn-img-left' src='../images/icons/back.png'>"+
					((Messages) Registry.get(Messages.class.getName())).HomeViewport_title()+
					"</img>");
			btnGotoHome.setHeight("32px");
			btnGotoHome.setStyleName("abstractviewport-btn");
			btnGotoHome.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_USERHOME_TO_PANEL);
					appEvent.setHistoryEvent(true);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			lc.add(btnGotoHome);
			toolBar.add(lc);
			toolBar.addStyleName("appstoreviewport-toolbar");
			toolBar.setHeight("38px");
			this.setTopComponent(toolBar);
		}
		
		public void loadConsumer(Application application){
			this.setUrl(application.getConsumer().getConnectURI());
		}
	}
}
