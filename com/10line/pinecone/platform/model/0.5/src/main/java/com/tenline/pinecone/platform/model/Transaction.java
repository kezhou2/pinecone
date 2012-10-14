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
public class Transaction extends com.tenline.pinecone.platform.model.Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2375122374224678621L;
	
	/**
	 * Transaction's Type
	 */
	public static final String INCOME = "income"; // User get from consumer
	public static final String PAYOUT = "payout"; // User pay to consumer
	
	@Column
	private String type;
	
	@Temporal(TemporalType.DATE)
	private Date timestamp;
	
	@Column
	private Integer nut = Integer.valueOf(0); // Virtual Coin
	
	@ManyToOne
	private Application application;
	
	/**
	 * 
	 */
	public Transaction() {
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
	 * @param application the application to set
	 */
	public void setApplication(Application application) {
		this.application = application;
	}

	/**
	 * @return the application
	 */
	public Application getApplication() {
		return application;
	}

}
