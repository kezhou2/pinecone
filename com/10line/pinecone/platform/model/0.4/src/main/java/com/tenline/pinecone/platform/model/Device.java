/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.util.Collection;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author Bill
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Device extends Entity {

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
	@Persistent
	private Boolean isDefault = false;
	
	@Persistent
	private String status;
	
	@Persistent(defaultFetchGroup = "true")
	private User user;
	
	@Persistent(defaultFetchGroup = "true")
	private Driver driver;

	@Persistent(mappedBy = "device", defaultFetchGroup = "true")
    @Element(dependent = "true")
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
