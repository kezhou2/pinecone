/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Bill
 *
 */
@XmlRootElement
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Record extends Entity {

	@Persistent
	private String value;
	
	@Persistent
	private Date timestamp;
	
	@Persistent(defaultFetchGroup = "true")
	private Variable variable;
	
	/**
	 * 
	 */
	public Record() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param variable the variable to set
	 */
	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	/**
	 * @return the variable
	 */
	public Variable getVariable() {
		return variable;
	}

}
