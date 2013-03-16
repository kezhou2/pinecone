/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import com.tenline.pinecone.platform.model.User;

/**
 * @author Bill
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	@RestResource(path = "names", rel = "names")
	public List<User> findByName(@Param("name") String name);
	
	@RestResource(path = "emails", rel = "emails")
	public List<User> findByEmail(@Param("email") String email);
	
}
