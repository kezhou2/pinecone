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
import javax.persistence.Table;

/**
 * @author Bill
 *
 */
@Entity
@Table(name = "variables")
public class Variable extends com.tenline.pinecone.platform.model.Entity {

	/**
	 * Variable's Type
	 */
	public static final String READ = "read";
	public static final String WRITE = "write";
	public static final String DISCRETE = "discrete";
	public static final String CONTINUOUS = "continuous";
	
	@Column
	private String name;
	
	@Column
	private String type;
	
	@ManyToOne
	private Device device;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "variable")
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
	 * @param items the items to set
	 */
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
