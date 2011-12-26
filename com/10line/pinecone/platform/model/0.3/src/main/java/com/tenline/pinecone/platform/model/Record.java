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
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Record extends Entity {
	
	@Persistent
	private Date timestamp;
	
	@Persistent(defaultFetchGroup = "true")
	private Device device;
	
	@Persistent(defaultFetchGroup = "true")
	private Variable variable;
	
	@Persistent(defaultFetchGroup = "true")
	private Item item;
	
	/**
	 * 
	 */
	public Record() {
		// TODO Auto-generated constructor stub
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
	 * @param device the device to set
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

	/**
	 * @return the device
	 */
	public Device getDevice() {
		return device;
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

	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

}
