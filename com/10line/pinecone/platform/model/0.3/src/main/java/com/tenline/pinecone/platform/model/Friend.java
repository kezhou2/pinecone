/**
 * 
 */
package com.tenline.pinecone.platform.model;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Bill
 *
 */
@XmlRootElement
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Friend extends Entity {
	
	/**
	 * Friend's Type
	 */
	public static final String CLASSMATE = "classmate";
	public static final String TEAMMATE = "teammate";
	public static final String COLLEAGUE = "colleague";
	
	/**
	 * Whether decision is made to agree or not
	 */
	@Persistent
	private Boolean isDecided = false;
	
	@Persistent
	private String type;
	
	@Persistent(defaultFetchGroup = "true")
	private User sender;
	
	@Persistent(defaultFetchGroup = "true")
	private User receiver;

	/**
	 * 
	 */
	public Friend() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param isDecided the isDecided to set
	 */
	public void setDecided(Boolean isDecided) {
		this.isDecided = isDecided;
	}

	/**
	 * @return the isDecided
	 */
	public Boolean isDecided() {
		return isDecided;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
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
