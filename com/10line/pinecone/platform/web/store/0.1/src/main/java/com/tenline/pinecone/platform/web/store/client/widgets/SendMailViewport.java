package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.MailEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class SendMailViewport extends AbstractViewport {
	
	private MainPanel mainPanel;
	
	public SendMailViewport(){
		mainPanel = new MainPanel();
		body.add(mainPanel, new BorderLayoutData(LayoutRegion.CENTER));
	}
	
	public void loadContactInfo(User receiver){
		mainPanel.setContactInfo(receiver);
	}
	
	private class MainPanel extends ContentPanel{
		
		private TextField<String> receiverTextfield;
		private TextField<String> titleTextfield;
		private HtmlEditor contentHtmleditor;
		
		private User receiver;
		
		public MainPanel(){
			this.setHeaderVisible(false);
			this.setBodyBorder(false);
			this.setBodyStyleName("loginviewport-background");
			
			setLayout(new BorderLayout());
			
			LayoutContainer toolbarlc = new LayoutContainer();
			BorderLayoutData toolbarbld = new BorderLayoutData(LayoutRegion.NORTH,40);
			toolbarbld.setMargins(new Margins(10,10,10,30));
			this.add(toolbarlc, toolbarbld);
			toolbarlc.addStyleName("loginviewport-background");
			
			HBoxLayout hbl_toolbarLayoutContainer = new HBoxLayout();
			hbl_toolbarLayoutContainer.setPack(BoxLayoutPack.START);
			hbl_toolbarLayoutContainer.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
			toolbarlc.setLayout(hbl_toolbarLayoutContainer);
			
			Button buttonHomePage = new Button();
			buttonHomePage.setHTML("<img class='btn-img-left' src='../images/icons/back.png'>"+
					((Messages) Registry.get(Messages.class.getName())).HomeViewport_title()+
					"</img>");
			buttonHomePage.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent event1 = new AppEvent(WidgetEvents.UPDATE_USERHOME_TO_PANEL);
					event1.setHistoryEvent(true);
					Dispatcher.get().dispatch(event1);
				}
			});
			toolbarlc.add(buttonHomePage);
			buttonHomePage.setHeight("32px");
			buttonHomePage.setStyleName("abstractviewport-btn");
			
			ContentPanel cp = new ContentPanel();
			cp.setLayout(new FitLayout());
			cp.setHeading(((Messages) Registry.get(Messages.class.getName())).SendMailViewport_title());
			cp.setBodyBorder(false);
			cp.setBorders(false);
			cp.getHeader().addStyleName("");
			cp.getHeader().addStyleName("header-title");
			cp.getHeader().addStyleName("appstoreviewport-panel-header");
			cp.addStyleName("appstoreviewport-panel");
			
			LayoutContainer mailContainer = new LayoutContainer();
			FormLayout fl_layoutContainer = new FormLayout();
			fl_layoutContainer.setLabelAlign(LabelAlign.TOP);
			fl_layoutContainer.setLabelWidth(60);
			mailContainer.setLayout(fl_layoutContainer);
			cp.add(mailContainer, new FillData(10,30,10,30));
			
			String labelStyle = "padding-top: 8px; color: #222;font-weight: bold;";
			
			receiverTextfield = new TextField<String>();
			receiverTextfield.setAllowBlank(false);
			FormData fd_txtfldNewTextfield = new FormData("100%");
			fd_txtfldNewTextfield.setMargins(new Margins(0, 0, 0, 0));
			mailContainer.add(receiverTextfield, fd_txtfldNewTextfield);
			receiverTextfield.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).SendMailViewport_label_receiver());
			receiverTextfield.setLabelStyle(labelStyle);
			receiverTextfield.setHeight("30px");
			
			titleTextfield = new TextField<String>();
			mailContainer.add(titleTextfield, new FormData("100%"));
			titleTextfield.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).SendMailViewport_label_title());
			titleTextfield.setLabelStyle(labelStyle);
			titleTextfield.setHeight("30px");
			
			contentHtmleditor = new HtmlEditor();
			mailContainer.add(contentHtmleditor, new FormData("100% 70%"));
			contentHtmleditor.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).SendMailViewport_label_content());
			contentHtmleditor.setLabelStyle(labelStyle);
			
			ButtonBar buttonBar = new ButtonBar();
			buttonBar.setAlignment(HorizontalAlignment.RIGHT);
			
			LayoutContainer lc = new LayoutContainer();
			lc.setLayout(new FillLayout());
			lc.setSize("80px", "30px");
			Button sendButton = new Button(((Messages) Registry.get(Messages.class.getName())).SendMailViewport_button_send());
			sendButton.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent e = new AppEvent(MailEvents.SEND);
					e.setData("content", contentHtmleditor.getRawValue());
					if(receiver != null){
						e.setData("receiver",receiver);
					}else{
						e.setData("receiver",receiverTextfield.getValue());
					}
					e.setData("title", titleTextfield.getValue());
					e.setHistoryEvent(true);
					Dispatcher.get().dispatch(e);
				}
			});
			lc.add(sendButton);
			sendButton.setStyleName("abstractviewport-btn");
			sendButton.setSize("80px", "30px");
			buttonBar.add(lc);
			
			mailContainer.add(buttonBar, new FormData("100%"));
			
			BorderLayoutData mailcontainerbld = new BorderLayoutData(LayoutRegion.CENTER);
			mailcontainerbld.setMargins(new Margins(0,30,20,30));
			add(cp, mailcontainerbld);
			
		}
		
		public void setContactInfo(User receiver){
			if(receiver != null){
				this.receiver = receiver; 
				receiverTextfield.setValue(receiver.getEmail());
			}
		}
	}
}
