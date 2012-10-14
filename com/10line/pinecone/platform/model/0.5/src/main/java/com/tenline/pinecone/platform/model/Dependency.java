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
public class Dependency extends com.tenline.pinecone.platform.model.Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8986436777672839620L;

	/**
	 * Whether is optional for executing consumer or not
	 */
	@Column
	private Boolean isOptional = false;
	
	@ManyToOne
	private Driver driver;
	
	@ManyToOne
	private Consumer consumer;

	/**
	 * 
	 */
	public Dependency() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param isOptional the isOptional to set
	 */
	public void setOptional(Boolean isOptional) {
		this.isOptional = isOptional;
	}

	/**
	 * @return the isOptional
	 */
	public Boolean isOptional() {
		return isOptional;
	}

	/**
	 * @param driver the driver to set
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	/**
	 * @return the driver
	 */
	public Driver getDriver() {
		return driver;
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

}
