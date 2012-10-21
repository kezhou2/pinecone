/**
 * 
 */
package com.tenline.pinecone.platform.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Bill
 *
 */
@javax.persistence.Entity
public abstract class Entity {

	@Id
	@GeneratedValue
    private Long id;
	
	/**
	 * 
	 */
	public Entity() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

}
