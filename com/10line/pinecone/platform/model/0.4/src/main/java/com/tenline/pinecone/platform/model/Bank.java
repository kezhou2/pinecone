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
public class Bank extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3588753257854957972L;

	@Persistent
	private String name;
	
	@Persistent
	private String address;
	
	@Persistent(mappedBy = "bank", defaultFetchGroup = "true")
    @Element(dependent = "true")
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
