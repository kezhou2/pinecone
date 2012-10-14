/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * @author Bill
 *
 */
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8706372050889204175L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
    private String id;
	
	/**
	 * 
	 */
	public Entity() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

}
