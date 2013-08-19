/**
 * 
 */
package com.tenline.pinecone.platform.web.service.m;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tenline.pinecone.platform.model.Authority;
import com.tenline.pinecone.platform.model.User;

/**
 * @author Bill
 *
 */
@Repository
@RequestMapping("/authority")
@Transactional(readOnly = true)
public class AuthorityRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String create(@RequestBody Authority authority) {
		manager.persist(authority); manager.flush(); return "{\"id\":\"" + authority.getId() + "\"}";
	}
	
	void create(User user) {
		Authority authority = new Authority();
		authority.setAuthority("ROLE_USER");
		authority.setUserName(user.getName());
		authority.setUser(user); create(authority);
	}

}
