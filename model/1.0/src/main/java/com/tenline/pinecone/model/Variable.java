/**
 * 
 */
package com.tenline.pinecone.model;

import java.util.Collection;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Bill
 *
 */
@XmlRootElement
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Variable extends Entity {

	@Persistent
	private String name;
	
	@Persistent
	private String type;
	
	@Persistent(defaultFetchGroup = "true")
	private Device device;
	
	@Persistent(mappedBy = "variable", defaultFetchGroup = "true")
    @Element(dependent = "true")
    @Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="value asc, timestamp asc"))
	private Collection<Record> records;
	
	@Persistent(mappedBy = "variable", defaultFetchGroup = "true")
    @Element(dependent = "true")
    @Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="value asc"))
	private Collection<Item> items;
	
	/**
	 * 
	 */
	public Variable() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * @param records the records to set
	 */
	@XmlTransient
	public void setRecords(Collection<Record> records) {
		this.records = records;
	}

	/**
	 * @return the records
	 */
	public Collection<Record> getRecords() {
		return records;
	}

	/**
	 * @param items the items to set
	 */
	@XmlTransient
	public void setItems(Collection<Item> items) {
		this.items = items;
	}

	/**
	 * @return the items
	 */
	public Collection<Item> getItems() {
		return items;
	}

}
