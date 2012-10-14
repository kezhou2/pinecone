/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Bill
 *
 */
@Entity
public class Record extends com.tenline.pinecone.platform.model.Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3734251552005688329L;

	@Temporal(TemporalType.DATE)
	private Date timestamp;
	
	@ManyToOne
	private Item item;
	
	/**
	 * 
	 */
	public Record() {
		// TODO Auto-generated constructor stub
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
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

}
