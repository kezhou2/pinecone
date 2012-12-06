/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenline.pinecone.platform.model.Authority;

/**
 * @author Bill
 *
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {}
