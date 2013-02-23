/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "entities")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Entity implements Serializable {

	@Id
	@GeneratedValue
    private Long id;
	
	@Transient
	private ArrayList<Link> _links;
	
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

	/**
	 * @param _links the _links to set
	 */
	public void set_links(ArrayList<Link> _links) {
		this._links = _links;
	}
	
	/**
	 * @return the _links
	 */
	public ArrayList<Link> get_links() {
		return _links;
	}

}
