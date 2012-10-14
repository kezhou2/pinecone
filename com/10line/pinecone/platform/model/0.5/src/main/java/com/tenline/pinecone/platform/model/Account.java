/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Bill
 *
 */
@Entity
public class Account extends com.tenline.pinecone.platform.model.Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5295245598824834456L;

	@Column
	private String number;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Bank bank;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "account")
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
