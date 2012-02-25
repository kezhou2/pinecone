package com.tenline.pinecone.platform.web.store.shared;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.model.User;

public class MailInfo extends BaseModelData {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5588589468873393179L;
	private Mail mail;

	public MailInfo(){
		mail = new Mail();
	}
	
	public MailInfo(Mail mail){
		this.mail = mail;
		
		this.setIsRead(mail.isRead());
		this.setTitle(mail.getTitle());
		this.setContent(mail.getContent());
		this.setSender(mail.getSender());
		this.setReceiver(mail.getReceiver());
	}

	public Boolean getIsRead() {
		return get("isRead");
	}

	public void setIsRead(Boolean isRead) {
		set("isRead",isRead);
		mail.setRead(isRead);
	}

	public String getTitle() {
		return get("title");
	}

	public void setTitle(String title) {
		set("title",title);
		mail.setTitle(title);
	}

	public String getContent() {
		return get("content");
	}

	public void setContent(String content) {
		set("content",content);
		mail.setContent(content);
	}

	public User getSender() {
		return get("sender");
	}

	public void setSender(User sender) {
		set("sender",sender);
		mail.setSender(sender);
	}

	public User getReceiver() {
		return get("receiver");
	}

	public void setReceiver(User receiver) {
		set("receiver",receiver);
		mail.setReceiver(receiver);
	}

	public Mail getMail() {
		return mail;
	}
	
	
}
