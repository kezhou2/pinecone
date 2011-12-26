package com.tenline.pinecone.platform.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
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
public class Consumer extends Entity {
	
	@Persistent
    private String key;
	
	@Persistent
    private String secret;
	
	@Persistent
    private String displayName;
	
	@Persistent
    private String connectURI;
	
	@Persistent
	private byte[] icon;
	
	@NotPersistent
    private Set<String> scopes;
	
	@NotPersistent
    private String[] permissions;
	
	@Persistent(mappedBy = "consumer", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<ConsumerInstallation> consumerInstallations;
	
	@Persistent(mappedBy = "consumer", defaultFetchGroup = "true")
    @Element(dependent = "true")
	private Collection<DeviceDependency> deviceDependencies;
	
	/**
	 * 
	 */
	public Consumer() {
		// TODO Auto-generated constructor stub
	}
    
    public Consumer(String key, String secret, String displayName, String connectURI) {
        this.setKey(key);
        this.setSecret(secret);
        this.setDisplayName(displayName);
        this.setConnectURI(connectURI);
    }
    
    public Consumer(String key, String secret, String displayName, String connectURI,
                         String[] perms) {
        this.setKey(key);
        this.setSecret(secret);
        this.setDisplayName(displayName);
        this.setConnectURI(connectURI);
        this.setPermissions(perms);
    }
	
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
    
    /**
	 * @param secret the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
    
    /**
	 * @param connectURI the connectURI to set
	 */
	public void setConnectURI(String connectURI) {
		this.connectURI = connectURI;
	}

	/**
	 * Returns the OAuth Consumer's connect URI.
     * If provided then it will be used to validate callback URLs which consumer
     * will provide during request token acquisition requests 
     * 
	 * @return the connectURI
	 */
	public String getConnectURI() {
		return connectURI;
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
     * Returns the OAuth Consumer's scopes. These are the scopes the consumer
     * will be able to access directly 
     */
    public String[] getScopes() {
        synchronized (this) {
            return scopes != null ? scopes.toArray(new String[]{}) : null;
        }
    }
    
    public void setScopes(String[] scopes) {
        synchronized (this) {
            this.scopes = new HashSet<String>(Arrays.asList(scopes));
        }
    }

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return the permissions
	 */
	public String[] getPermissions() {
		return permissions;
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
	 * @param deviceDependencies the deviceDependencies to set
	 */
	@XmlTransient
	public void setDeviceDependencies(Collection<DeviceDependency> deviceDependencies) {
		this.deviceDependencies = deviceDependencies;
	}

	/**
	 * @return the deviceDependencies
	 */
	public Collection<DeviceDependency> getDeviceDependencies() {
		return deviceDependencies;
	}
    
}
