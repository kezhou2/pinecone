/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.util.Collection;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Bill
 *
 */
@XmlRootElement
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Category extends Entity {
	
	/**
	 * Category's Type
	 */
	public static final String ORG = "org";
	public static final String COM = "com";
	public static final String NET = "net";
	
	@Persistent
	private String type;
	
	@Persistent
	private String name;
	
	/**
	 * Category's Domain
	 */
	public static final String DOMAIN_PET = "pet";
	public static final String DOMAIN_SECURITY = "security";
	
	/**
	 * Category's SubDomain
	 */
	public static final String SUB_DOMAIN_LEISURE = "leisure";
	public static final String SUB_DOMAIN_AQUARIUM = "aquarium";
	public static final String SUB_DOMAIN_CAMERA = "camera";
	
	@Persistent
	private String domain;
	
	@Persistent
	private String subdomain;
	
	@Persistent(mappedBy = "category", defaultFetchGroup = "true")
    @Element(dependent = "true")
	private Collection<Consumer> consumers;
	
	@Persistent(mappedBy = "category", defaultFetchGroup = "true")
    @Element(dependent = "true")
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
	@XmlTransient
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
	@XmlTransient
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
