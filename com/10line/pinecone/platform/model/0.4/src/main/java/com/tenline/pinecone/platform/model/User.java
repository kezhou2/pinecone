/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.util.Collection;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author Bill
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class User extends Entity {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2479507995915198169L;

	@Persistent
	private String name;
    
    @Persistent
    private String email;
    
    @Persistent
    private String password;
    
    @Persistent
    private String phone;
    
    @Persistent
    private String avatar;
    
    @Persistent
	private Integer nut = Integer.valueOf(0); // Virtual Coin
    
    @Persistent(mappedBy = "user", defaultFetchGroup = "true")
    @Element(dependent = "true")
	private Collection<Account> accounts;
    
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
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param nut the nut to set
	 */
	public void setNut(Integer nut) {
		this.nut = nut;
	}

	/**
	 * @return the nut
	 */
	public Integer getNut() {
		return nut;
	}

	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(Collection<Account> accounts) {
		this.accounts = accounts;
	}

	/**
	 * @return the accounts
	 */
	public Collection<Account> getAccounts() {
		return accounts;
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
	 * @param applications the applications to set
	 */
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
