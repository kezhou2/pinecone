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
public class Driver extends com.tenline.pinecone.platform.model.Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2916083820582880933L;

	@Column
	private String name;
	
	@Column
	private String alias;
	
	@Column
	private String version;
	
	@Column
	private byte[] icon;
	
	@ManyToOne
	private Category category;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "driver")
	private Collection<Device> devices;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "driver")
	private Collection<Dependency> dependencies;
	
	/**
	 * 
	 */
	public Driver() {
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
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	/**
	 * @return the icon
	 */
	public byte[] getIcon() {
		return icon;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param devices the devices to set
	 */
	public void setDevices(Collection<Device> devices) {
		this.devices = devices;
	}

	/**
	 * @return the devices
	 */
	public Collection<Device> getDevices() {
		return devices;
	}

	/**
	 * @param dependencies the dependencies to set
	 */
	public void setDependencies(Collection<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	/**
	 * @return the dependencies
	 */
	public Collection<Dependency> getDependencies() {
		return dependencies;
	}

}
