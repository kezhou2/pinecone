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
	
	@Persistent(defaultFetchGroup = "true")
	private User person;
	
	@Persistent(defaultFetchGroup = "true")
	private User user;

	/**
	 * 
	 */
	public Friend() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(User person) {
		this.person = person;
	}

	/**
	 * @return the person
	 */
	public User getPerson() {
		return person;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

}
