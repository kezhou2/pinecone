package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.MailEvents;

public class MailWindow extends Window {
	private TextField<String> senderTextfield;
	private TextField<String> titleTextfield;
	private HtmlEditor contentHtmleditor;
	
	public MailWindow(MailWindow parent){
		this.init();
	}
	
	public MailWindow(){
		this.init();
	}
	
	public void init() {
		setSize("500", "400");
		setHeading(((Messages) Registry.get(Messages.class.getName())).MailWindow_title());
		setLayout(new FitLayout());
		
		LayoutContainer mailContainer = new LayoutContainer();
		FormLayout fl_layoutContainer = new FormLayout();
		fl_layoutContainer.setLabelAlign(LabelAlign.TOP);
		fl_layoutContainer.setLabelWidth(60);
		mailContainer.setStyleAttribute("padding", "10px");
		mailContainer.setLayout(fl_layoutContainer);
		
		senderTextfield = new TextField<String>();
		senderTextfield.setAllowBlank(false);
		FormData fd_txtfldNewTextfield = new FormData("80%");
		fd_txtfldNewTextfield.setMargins(new Margins(0, 0, 0, 0));
		mailContainer.add(senderTextfield, fd_txtfldNewTextfield);
		senderTextfield.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).MailWindow_label_sender());
		
		titleTextfield = new TextField<String>();
		mailContainer.add(titleTextfield, new FormData("80%"));
		titleTextfield.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).MailWindow_label_title());
		
		contentHtmleditor = new HtmlEditor();
		mailContainer.add(contentHtmleditor, new FormData("100% 65%"));
		contentHtmleditor.setSize("", "");
		contentHtmleditor.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).MailWindow_label_content());
		add(mailContainer, new FitData(5));
		mailContainer.setSize("", "");
		
		ToolBar toolBar = new ToolBar();
		
		FillToolItem fillToolItem = new FillToolItem();
		toolBar.add(fillToolItem);
		
		
		final Button backButton = new Button(((Messages) Registry.get(Messages.class.getName())).MailWindow_button_back());
		backButton.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				MailWindow.this.hide();
			}
		});
		toolBar.add(backButton);
		backButton.setWidth("50");
		setBottomComponent(toolBar);
	}
	
	public void setMail(Mail mail){
		senderTextfield.setValue(mail.getSender().getName());
		titleTextfield.setValue(mail.getTitle());
		contentHtmleditor.setValue(mail.getContent());
		
		//set the mail read flag.
		AppEvent event = new AppEvent(MailEvents.SETTING);
		event.setData("isRead",true);
		Dispatcher.get().dispatch(event);
	}
}
