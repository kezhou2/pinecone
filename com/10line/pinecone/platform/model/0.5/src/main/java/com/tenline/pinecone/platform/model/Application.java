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
public class Application extends com.tenline.pinecone.platform.model.Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9208005245485567210L;
	
	/**
	 * Application's Status
	 */
	public static final String CLOSED = "closed";
	public static final String OPENED = "opened";
	
	/**
	 * Whether is default to open or not
	 */
	@Column
	private Boolean isDefault = false;
	
	@Column
	private String status;
	
	@ManyToOne
	private Consumer consumer;
	
	@ManyToOne
	private User user;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "application")
	private Collection<Transaction> transactions;

	/**
	 * 
	 */
	public Application() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * @return the isDefault
	 */
	public Boolean isDefault() {
		return isDefault;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param consumer the consumer to set
	 */
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	/**
	 * @return the consumer
	 */
	public Consumer getConsumer() {
		return consumer;
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
	 * @param transactions the transactions to set
	 */
	public void setTransactions(Collection<Transaction> transactions) {
		this.transactions = transactions;
	}

	/**
	 * @return the transactions
	 */
	public Collection<Transaction> getTransactions() {
		return transactions;
	}

}
