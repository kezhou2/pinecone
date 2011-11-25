/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.io.Serializable;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public abstract class Entity implements Serializable {

	@PrimaryKey
	@Column(length = 255)
    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
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
