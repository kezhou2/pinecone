/**
 * 
 */
package com.tenline.pinecone.platform.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * @author Bill
 *
 */
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.JOINED)
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
