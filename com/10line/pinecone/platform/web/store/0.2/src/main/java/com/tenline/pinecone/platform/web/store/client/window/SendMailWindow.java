package com.tenline.pinecone.platform.web.store.client.window;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.PropertyEditor;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.web.store.client.EnvConfig;
import com.tenline.pinecone.platform.web.store.shared.FriendInfo;
import com.tenline.pinecone.platform.web.store.shared.MailInfo;
import com.tenline.pinecone.platform.web.store.shared.UserInfo;

public class SendMailWindow extends Window {
	private TextField<UserInfo> receiverTextfield;
	private TextField<String> titleTextfield;
	private HtmlEditor contentHtmleditor;
	
	private UserInfo sender;
	private UserInfo receiver;
	
	private MailWindow parent;
	
	public SendMailWindow(MailWindow parent){
		this.parent = parent;
		this.init();
	}
	
	public SendMailWindow(){
		this.parent = null;
		this.init();
	}
	
	public void init() {
		setSize("500", "400");
		setHeading("邮件");
		setLayout(new FitLayout());
		
		LayoutContainer mailContainer = new LayoutContainer();
		FormLayout fl_layoutContainer = new FormLayout();
		fl_layoutContainer.setLabelAlign(LabelAlign.TOP);
		fl_layoutContainer.setLabelWidth(60);
		mailContainer.setStyleAttribute("padding", "10px");
		mailContainer.setLayout(fl_layoutContainer);
		
		receiverTextfield = new TextField<UserInfo>();
		receiverTextfield.setAllowBlank(false);
		FormData fd_txtfldNewTextfield = new FormData("80%");
		fd_txtfldNewTextfield.setMargins(new Margins(0, 0, 0, 0));
		mailContainer.add(receiverTextfield, fd_txtfldNewTextfield);
		receiverTextfield.setFieldLabel("收件人");
		receiverTextfield.setPropertyEditor(new PropertyEditor<UserInfo>(){

			@Override
			public String getStringValue(UserInfo value) {
				if(value != null){
					return value.getName();
				}else{
					return null;
				}
			}

			@Override
			public UserInfo convertStringValue(String value) {
				return receiver;
			}
			
		});
		
		titleTextfield = new TextField<String>();
		mailContainer.add(titleTextfield, new FormData("80%"));
		titleTextfield.setFieldLabel("邮件标题");
		
		contentHtmleditor = new HtmlEditor();
		mailContainer.add(contentHtmleditor, new FormData("100% 65%"));
		contentHtmleditor.setSize("", "");
		contentHtmleditor.setFieldLabel("内容");
		add(mailContainer, new FitData(5));
		mailContainer.setSize("", "");
		
		ToolBar toolBar = new ToolBar();
		
		FillToolItem fillToolItem = new FillToolItem();
		toolBar.add(fillToolItem);
		
		final Button sendButton = new Button("发送");
		sendButton.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				sendButton.setEnabled(false);
				
				MailInfo mi = new MailInfo();
				mi.setIsRead(false);
				mi.setContent(contentHtmleditor.getValue());
				mi.setTitle(titleTextfield.getValue());
				EnvConfig.getPineconeService().
					sendMail(sender, receiverTextfield.getValue(), mi, new AsyncCallback<Boolean>(){

						@Override
						public void onFailure(Throwable caught) {
							MessageBox.info("错误", "调用后台服务发送邮件失败", null);
							sendButton.setEnabled(true);
						}

						@Override
						public void onSuccess(Boolean result) {
							sendButton.setEnabled(true);
							if(parent != null){
								parent.loadMails();
							}
							SendMailWindow.this.hide();
						}
					
				});
			}
		});
		toolBar.add(sendButton);
		sendButton.setWidth("50");
		
		final Button cancelButton = new Button("取消");
		cancelButton.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				SendMailWindow.this.hide();
			}
		});
		toolBar.add(cancelButton);
		cancelButton.setWidth("50");
		setBottomComponent(toolBar);
	}
	
	
	
	public void setContactInfo(FriendInfo friendInfo){
		if(friendInfo.getSender().getId().equals(EnvConfig.getLoginUser().getUser().getId())){
			sender = new UserInfo(friendInfo.getSender());
			receiver = new UserInfo(friendInfo.getReceiver()); 
		}else{
			sender = new UserInfo(friendInfo.getReceiver());
			receiver = new UserInfo(friendInfo.getSender());
		}
		
		receiverTextfield.setValue(receiver);
	}
	
	public void setMail(MailInfo mailInfo){
		receiverTextfield.setValue(new UserInfo(mailInfo.getReceiver()));
		titleTextfield.setValue(mailInfo.getTitle());
		contentHtmleditor.setValue(mailInfo.getContent());
	}
}
