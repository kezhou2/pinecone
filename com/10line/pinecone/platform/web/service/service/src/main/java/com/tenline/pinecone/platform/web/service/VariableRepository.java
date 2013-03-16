/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenline.pinecone.platform.model.Variable;

/**
 * @author Bill
 *
 */
public interface VariableRepository extends JpaRepository<Variable, Long> {}
