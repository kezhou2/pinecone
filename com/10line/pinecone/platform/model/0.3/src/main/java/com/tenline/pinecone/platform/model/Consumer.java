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
    private String name;
	
	@Persistent
    private String connectURI;
	
	@Persistent
	private byte[] icon;
	
	@Persistent
	private String alias;
	
	@Persistent
	private String version;
	
	@Persistent
	private Integer nut = Integer.valueOf(0); // Virtual Coin
	
	@NotPersistent
    private Set<String> scopes;
	
	@NotPersistent
    private String[] permissions;
	
	@Persistent(defaultFetchGroup = "true")
	private Category category;
	
	@Persistent(mappedBy = "consumer", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Collection<Application> applications;
	
	@Persistent(mappedBy = "consumer", defaultFetchGroup = "true")
    @Element(dependent = "true")
	private Collection<Dependency> dependencies;
	
	/**
	 * 
	 */
	public Consumer() {
		// TODO Auto-generated constructor stub
	}
    
    public Consumer(String key, String secret, String displayName, String connectURI) {
        this.setKey(key);
        this.setSecret(secret);
        this.setName(displayName);
        this.setConnectURI(connectURI);
    }
    
    public Consumer(String key, String secret, String displayName, String connectURI,
                         String[] perms) {
        this.setKey(key);
        this.setSecret(secret);
        this.setName(displayName);
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
	 * @param dependencies the dependencies to set
	 */
	@XmlTransient
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
