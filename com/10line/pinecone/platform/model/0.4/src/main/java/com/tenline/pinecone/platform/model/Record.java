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
public class Record extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3734251552005688329L;

	@Persistent
	private Date timestamp;
	
	@Persistent(defaultFetchGroup = "true")
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
