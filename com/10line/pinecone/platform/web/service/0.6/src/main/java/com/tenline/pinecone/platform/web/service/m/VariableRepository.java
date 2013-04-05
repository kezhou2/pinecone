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

import com.tenline.pinecone.platform.model.Variable;

/**
 * @author Bill
 *
 */
@Repository
@RequestMapping("/variable")
public class VariableRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String create(@RequestBody Variable variable) {
		manager.persist(variable); manager.flush(); return "{\"id\":\"" + variable.getId() + "\"}";
	}

}
