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
	private Collection<DeviceInstallation> deviceInstallations;
    
    @Persistent(mappedBy = "user", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<ConsumerInstallation> consumerInstallations;
    
    @Persistent(mappedBy = "sender", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<UserRelation> sentRelations;
    
    @Persistent(mappedBy = "receiver", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<UserRelation> receivedRelations;
    
    @Persistent(mappedBy = "sender", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<UserMessage> sentMessages;
    
    @Persistent(mappedBy = "receiver", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<UserMessage> receivedMessages;

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
	 * @param deviceInstallations the deviceInstallations to set
	 */
	@XmlTransient
	public void setDeviceInstallations(Collection<DeviceInstallation> deviceInstallations) {
		this.deviceInstallations = deviceInstallations;
	}

	/**
	 * @return the deviceInstallations
	 */
	public Collection<DeviceInstallation> getDeviceInstallations() {
		return deviceInstallations;
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

	/**
	 * @param sentRelations the sentRelations to set
	 */
	@XmlTransient
	public void setSentRelations(Collection<UserRelation> sentRelations) {
		this.sentRelations = sentRelations;
	}

	/**
	 * @return the sentRelations
	 */
	public Collection<UserRelation> getSentRelations() {
		return sentRelations;
	}

	/**
	 * @param receivedRelations the receivedRelations to set
	 */
	@XmlTransient
	public void setReceivedRelations(Collection<UserRelation> receivedRelations) {
		this.receivedRelations = receivedRelations;
	}

	/**
	 * @return the receivedRelations
	 */
	public Collection<UserRelation> getReceivedRelations() {
		return receivedRelations;
	}

	/**
	 * @param sentMessages the sentMessages to set
	 */
	@XmlTransient
	public void setSentMessages(Collection<UserMessage> sentMessages) {
		this.sentMessages = sentMessages;
	}

	/**
	 * @return the sentMessages
	 */
	public Collection<UserMessage> getSentMessages() {
		return sentMessages;
	}

	/**
	 * @param receivedMessages the receivedMessages to set
	 */
	@XmlTransient
	public void setReceivedMessages(Collection<UserMessage> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	/**
	 * @return the receivedMessages
	 */
	public Collection<UserMessage> getReceivedMessages() {
		return receivedMessages;
	}

}
