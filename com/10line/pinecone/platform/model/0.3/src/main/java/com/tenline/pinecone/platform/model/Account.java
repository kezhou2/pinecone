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
public class Account extends Entity {

	@Persistent
	private String number;
	
	@Persistent(defaultFetchGroup = "true")
	private User user;
	
	@Persistent(defaultFetchGroup = "true")
	private Bank bank;
	
	@Persistent(mappedBy = "account", defaultFetchGroup = "true")
    @Element(dependent = "true")
	private Collection<Exchange> exchanges;
	
	/**
	 * 
	 */
	public Account() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
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

	/**
	 * @param bank the bank to set
	 */
	public void setBank(Bank bank) {
		this.bank = bank;
	}

	/**
	 * @return the bank
	 */
	public Bank getBank() {
		return bank;
	}

	/**
	 * @param exchanges the exchanges to set
	 */
	@XmlTransient
	public void setExchanges(Collection<Exchange> exchanges) {
		this.exchanges = exchanges;
	}

	/**
	 * @return the exchanges
	 */
	public Collection<Exchange> getExchanges() {
		return exchanges;
	}

}
