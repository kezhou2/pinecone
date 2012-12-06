/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.rest.repository.annotation.RestResource;

/**
 * @author Bill
 *
 */
@Entity
@Table(name = "users")
public class User extends com.tenline.pinecone.platform.model.Entity {
	
	@Column(name = "username")
	private String name;
    
    @Column
    private String email;
    
    @Column
    @RestResource(exported = false)
    private String password;
    
    @Column
    @RestResource(exported = false)
    private boolean enabled = true;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
	private Collection<Device> devices;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private Collection<Authority> authorities;

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
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
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
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(Collection<Authority> authorities) {
		this.authorities = authorities;
	}
	
	/**
	 * @return the authorities
	 */
	public Collection<Authority> getAuthorities() {
		return authorities;
	}

}
