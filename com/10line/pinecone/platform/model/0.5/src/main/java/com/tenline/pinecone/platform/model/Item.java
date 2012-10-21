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
public class Item extends com.tenline.pinecone.platform.model.Entity {
	
	@Column
	private byte[] value;
	
	@Column
	private String text;
	
	@ManyToOne
	private Variable variable;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	private Collection<Record> records;

	/**
	 * 
	 */
	public Item() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(byte[] value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public byte[] getValue() {
		return value;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param variable the variable to set
	 */
	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	/**
	 * @return the variable
	 */
	public Variable getVariable() {
		return variable;
	}

	/**
	 * @param records the records to set
	 */
	public void setRecords(Collection<Record> records) {
		this.records = records;
	}

	/**
	 * @return the records
	 */
	public Collection<Record> getRecords() {
		return records;
	}

}
