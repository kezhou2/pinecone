/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import com.tenline.pinecone.platform.model.Authority;

/**
 * @author Bill
 *
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	
	@RestResource(path = "userNames", rel = "userNames")
	public List<Authority> findByUserName(@Param("userName") String userName);
	
}
