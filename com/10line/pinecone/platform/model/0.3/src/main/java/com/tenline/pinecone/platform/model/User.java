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
    private Collection<Application> applications;
    
    @Persistent(mappedBy = "sender", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<Friend> sentFriends;
    
    @Persistent(mappedBy = "receiver", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<Friend> receivedFriends;
    
    @Persistent(mappedBy = "sender", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<Mail> sentMails;
    
    @Persistent(mappedBy = "receiver", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<Mail> receivedMails;

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
	 * @param applications the applications to set
	 */
	@XmlTransient
	public void setApplications(Collection<Application> applications) {
		this.applications = applications;
	}

	/**
	 * @return the applications
	 */
	public Collection<Application> getApplications() {
		return applications;
	}

	/**
	 * @param sentFriends the sentFriends to set
	 */
	@XmlTransient
	public void setSentFriends(Collection<Friend> sentFriends) {
		this.sentFriends = sentFriends;
	}

	/**
	 * @return the sentFriends
	 */
	public Collection<Friend> getSentFriends() {
		return sentFriends;
	}

	/**
	 * @param receivedFriends the receivedFriends to set
	 */
	@XmlTransient
	public void setReceivedFriends(Collection<Friend> receivedFriends) {
		this.receivedFriends = receivedFriends;
	}

	/**
	 * @return the receivedFriends
	 */
	public Collection<Friend> getReceivedFriends() {
		return receivedFriends;
	}

	/**
	 * @param sentMails the sentMails to set
	 */
	@XmlTransient
	public void setSentMails(Collection<Mail> sentMails) {
		this.sentMails = sentMails;
	}

	/**
	 * @return the sentMails
	 */
	public Collection<Mail> getSentMails() {
		return sentMails;
	}

	/**
	 * @param receivedMails the receivedMails to set
	 */
	@XmlTransient
	public void setReceivedMails(Collection<Mail> receivedMails) {
		this.receivedMails = receivedMails;
	}

	/**
	 * @return the receivedMails
	 */
	public Collection<Mail> getReceivedMails() {
		return receivedMails;
	}

}
