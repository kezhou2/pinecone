/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Bill
 *
 */
@Entity
public class Exchange extends com.tenline.pinecone.platform.model.Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3392696315534806848L;
	
	/**
	 * Exchange's Type
	 */
	public static final String INCOME = "income"; // User get from bank
	public static final String PAYOUT = "payout"; // User pay to bank
	
	@Column
	private String type;
	
	@Temporal(TemporalType.DATE)
	private Date timestamp;
	
	@Column
	private Integer nut = Integer.valueOf(0); // Virtual Coin
	
	@ManyToOne
	private Account account;
	
	/**
	 * 
	 */
	public Exchange() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
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
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

}
