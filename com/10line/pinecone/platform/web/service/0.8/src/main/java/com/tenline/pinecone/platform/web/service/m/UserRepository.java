/**
 * 
 */
package com.tenline.pinecone.platform.web.service.m;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tenline.pinecone.platform.model.User;

/**
 * @author Bill
 *
 */
@Repository
@RequestMapping("/user")
@Transactional(readOnly = true)
public class UserRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private AuthorityRepository repository;
	
	@RequestMapping(value = "/search/names", method = RequestMethod.GET)
	public @ResponseBody boolean findByName(@RequestParam(value = "username") String name) {
		List<User> users = manager.createQuery("from User where name='" + name + "'", User.class).getResultList();
		if(users.size() > 0) return false; else return true;
	}
	
	@RequestMapping(value = "/search/emails", method = RequestMethod.GET)
	public @ResponseBody boolean findByEmail(@RequestParam(value = "email") String email) {
		List<User> users = manager.createQuery("from User where email='" + email + "'", User.class).getResultList();
		if(users.size() > 0) return false; else return true;
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String create(@RequestBody User user) {
		manager.persist(user); manager.flush(); repository.create(user); return "{\"id\":\"" + user.getId() + "\"}";
	}

}
