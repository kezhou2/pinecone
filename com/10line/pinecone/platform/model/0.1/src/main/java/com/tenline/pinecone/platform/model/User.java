/**
 * 
 */
package com.tenline.pinecone.platform.model;

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
public class User extends Entity {
	
    @Persistent
	private String snsId;
    
    @Persistent(mappedBy = "user", defaultFetchGroup = "true")
    @Element(dependent = "true")
    @Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="name asc"))
    private Collection<Device> devices;

	/**
	 * 
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param snsId the snsId to set
	 */
	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}

	/**
	 * @return the snsId
	 */
	public String getSnsId() {
		return snsId;
	}

	/**
	 * @param devices the devices to set
	 */
	@XmlTransient
	public void setDevices(Collection<Device> devices) {
		this.devices = devices;
	}

	/**
	 * @return the devices
	 */
	public Collection<Device> getDevices() {
		return devices;
	}

}
