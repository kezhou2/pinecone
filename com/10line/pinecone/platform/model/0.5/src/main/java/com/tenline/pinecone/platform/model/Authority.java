/**
 * 
 */
package com.tenline.pinecone.platform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.rest.repository.annotation.RestResource;

/**
 * @author Bill
 *
 */
@Entity
@Table(name = "authorities")
public class Authority extends com.tenline.pinecone.platform.model.Entity {
	
	@Column
	private String authority;
	
	@Column(name = "username")
	@RestResource(exported = false)
	private String userName;
	
	@ManyToOne
	private User user;
	
	/**
	 * 
	 */
	public Authority() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	/**
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

}
