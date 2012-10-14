/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * @author Bill
 *
 */
@Entity
public class Category extends com.tenline.pinecone.platform.model.Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2917989584887836976L;
	
	/**
	 * Category's Type
	 */
	public static final String ORG = "org";
	public static final String COM = "com";
	public static final String NET = "net";
	
	@Column
	private String type;
	
	@Column
	private String name;
	
	/**
	 * Category's Domain
	 */
	public static final String DOMAIN_PET = "pet";
	public static final String DOMAIN_SECURITY = "security";
	public static final String DOMAIN_GAME = "game";
	
	/**
	 * Category's SubDomain
	 */
	public static final String SUB_DOMAIN_LEISURE = "leisure";
	public static final String SUB_DOMAIN_AQUARIUM = "aquarium";
	public static final String SUB_DOMAIN_CAMERA = "camera";
	public static final String SUB_DOMAIN_SIMULATION = "simulation";
	
	@Column
	private String domain;
	
	@Column
	private String subdomain;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "category")
	private Collection<Consumer> consumers;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "category")
	private Collection<Driver> drivers;

	/**
	 * 
	 */
	public Category() {
		// TODO Auto-generated constructor stub
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
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param subdomain the subdomain to set
	 */
	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}

	/**
	 * @return the subdomain
	 */
	public String getSubdomain() {
		return subdomain;
	}

	/**
	 * @param consumers the consumers to set
	 */
	public void setConsumers(Collection<Consumer> consumers) {
		this.consumers = consumers;
	}

	/**
	 * @return the consumers
	 */
	public Collection<Consumer> getConsumers() {
		return consumers;
	}

	/**
	 * @param drivers the drivers to set
	 */
	public void setDrivers(Collection<Driver> drivers) {
		this.drivers = drivers;
	}

	/**
	 * @return the drivers
	 */
	public Collection<Driver> getDrivers() {
		return drivers;
	}

}
