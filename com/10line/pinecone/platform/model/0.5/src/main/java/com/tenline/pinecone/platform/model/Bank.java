/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * @author Bill
 *
 */
@Entity
public class Bank extends com.tenline.pinecone.platform.model.Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3588753257854957972L;

	@Column
	private String name;
	
	@Column
	private String address;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "bank")
	private Collection<Account> accounts;
	
	/**
	 * 
	 */
	public Bank() {
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
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
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

}
