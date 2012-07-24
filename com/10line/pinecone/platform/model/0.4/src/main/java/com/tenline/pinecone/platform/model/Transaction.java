/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author Bill
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Transaction extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2375122374224678621L;
	
	/**
	 * Transaction's Type
	 */
	public static final String INCOME = "income"; // User get from consumer
	public static final String PAYOUT = "payout"; // User pay to consumer
	
	@Persistent
	private String type;
	
	@Persistent
	private Date timestamp;
	
	@Persistent
	private Integer nut = Integer.valueOf(0); // Virtual Coin
	
	@Persistent(defaultFetchGroup = "true")
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
