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
@PersistenceCapable(identityType = IdentityType.APPLICATION, 
		detachable = "true", table = "consumer_installation")
public class ConsumerInstallation extends Entity {
	
	/**
	 * Whether is default to open or not
	 */
	@Persistent
	private Boolean isDefault;
	
	@Persistent(defaultFetchGroup = "true")
	private Consumer consumer;
	
	@Persistent(defaultFetchGroup = "true")
	private User user;

	/**
	 * 
	 */
	public ConsumerInstallation() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * @return the isDefault
	 */
	public Boolean isDefault() {
		return isDefault;
	}

	/**
	 * @param consumer the consumer to set
	 */
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	/**
	 * @return the consumer
	 */
	public Consumer getConsumer() {
		return consumer;
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
