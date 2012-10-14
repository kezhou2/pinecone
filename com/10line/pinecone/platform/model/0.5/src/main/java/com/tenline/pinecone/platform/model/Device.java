/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Bill
 *
 */
@Entity
public class Device extends com.tenline.pinecone.platform.model.Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1878720276377467305L;
	
	/**
	 * Device's Status
	 */
	public static final String CLOSED = "closed";
	public static final String OPENED = "opened";
	
	/**
	 * Whether is default to open or not
	 */
	@Column
	private Boolean isDefault = false;
	
	@Column
	private String status;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Driver driver;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "device")
	private Collection<Variable> variables;
	
	/**
	 * 
	 */
	public Device() {
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
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
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
	 * @param variables the variables to set
	 */
	public void setVariables(Collection<Variable> variables) {
		this.variables = variables;
	}

	/**
	 * @return the variables
	 */
	public Collection<Variable> getVariables() {
		return variables;
	}

}
