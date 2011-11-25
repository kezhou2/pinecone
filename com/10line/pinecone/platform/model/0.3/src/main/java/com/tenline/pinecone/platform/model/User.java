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
public class User extends Entity {
	
    @Persistent
	private String name;
    
    @Persistent
    private String email;
    
    @Persistent
    private String password;
    
    @Persistent
    private byte[] avatar;
    
    @Persistent(mappedBy = "user", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<Device> devices;
    
    @Persistent(mappedBy = "user", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<Friend> friends;
    
    @Persistent(mappedBy = "user", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<ConsumerInstallation> consumerInstallations;

	/**
	 * 
	 */
	public User() {
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
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	/**
	 * @return the avatar
	 */
	public byte[] getAvatar() {
		return avatar;
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

	/**
	 * @param friends the friends to set
	 */
	@XmlTransient
	public void setFriends(Collection<Friend> friends) {
		this.friends = friends;
	}

	/**
	 * @return the friends
	 */
	public Collection<Friend> getFriends() {
		return friends;
	}

	/**
	 * @param consumerInstallations the consumerInstallations to set
	 */
	@XmlTransient
	public void setConsumerInstallations(Collection<ConsumerInstallation> consumerInstallations) {
		this.consumerInstallations = consumerInstallations;
	}

	/**
	 * @return the consumerInstallations
	 */
	public Collection<ConsumerInstallation> getConsumerInstallations() {
		return consumerInstallations;
	}

}
