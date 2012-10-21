/**
 * 
 */
package com.tenline.pinecone.platform.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Bill
 *
 */
@Entity
public class User extends com.tenline.pinecone.platform.model.Entity {
	
	@Column
	private String name;
    
    @Column
    private String email;
    
    @Column
    private String password;
    
    @Column
    private String phone;
    
    @Column
    private String avatar;
    
    @Column
	private Integer nut = Integer.valueOf(0); // Virtual Coin

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

}
