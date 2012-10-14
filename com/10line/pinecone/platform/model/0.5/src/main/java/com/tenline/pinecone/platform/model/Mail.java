/**
 * 
 */
package com.tenline.pinecone.platform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Bill
 *
 */
@Entity
public class Mail extends com.tenline.pinecone.platform.model.Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3086221311216926619L;

	/**
	 * Whether is read or not
	 */
	@Column
	private Boolean isRead = false;
	
	@Column
	private String title;
	
	@Column
	private String content;
	
	@ManyToOne
	private User sender;
	
	@ManyToOne
	private User receiver;

	/**
	 * 
	 */
	public Mail() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param isRead the isRead to set
	 */
	public void setRead(Boolean isRead) {
		this.isRead = isRead;
	}

	/**
	 * @return the isRead
	 */
	public Boolean isRead() {
		return isRead;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(User sender) {
		this.sender = sender;
	}

	/**
	 * @return the sender
	 */
	public User getSender() {
		return sender;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the receiver
	 */
	public User getReceiver() {
		return receiver;
	}

}
