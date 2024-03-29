package com.tenline.pinecone.platform.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlRootElement;

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
	
	@NotPersistent
    private Set<String> scopes;
	
	@NotPersistent
    private String[] permissions;
	
	public Consumer() {
		
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
    
}
