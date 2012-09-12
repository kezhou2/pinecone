/**
 * 
 */
package com.tenline.pinecone.platform.model;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author Bill
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Friend extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4608716013146851907L;
	
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
	private Boolean decided;
	
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
	 * @param decided the decided to set
	 */
	public void setDecided(Boolean decided) {
		this.decided = decided;
	}

	/**
	 * @return the decided
	 */
	public Boolean getDecided() {
		return decided;
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
