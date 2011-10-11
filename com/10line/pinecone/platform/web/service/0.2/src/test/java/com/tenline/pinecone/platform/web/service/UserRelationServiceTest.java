/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.UserRelation;
import com.tenline.pinecone.platform.web.service.restful.UserRelationRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class UserRelationServiceTest extends AbstractServiceTest {

	private User owner;
	
	private UserRelation userRelation;
	
	private List userRelations;
	
	private UserRelationRestfulService userRelationService;
	
	@Before
	public void testSetup() {
		userRelationService = new UserRelationRestfulService(persistenceManagerFactory);
		userRelationService.setJdoTemplate(jdoTemplate);
		userRelation = new UserRelation();
		userRelation.setId("asa");
		userRelation.setType("classmate");
		owner = new User();
		owner.setId("asa");
		userRelation.setOwner(owner);
		userRelations = new ArrayList();
		userRelations.add(userRelation);
	}
	
	@After
	public void testShutdown() {	
		userRelationService = null;
		userRelations.remove(userRelation);
		owner = null;
		userRelation = null;
		userRelations = null;
	}

	@Test
	public void testCreate() { 
		when(jdoTemplate.getObjectById(User.class, owner.getId())).thenReturn(owner);
		when(jdoTemplate.makePersistent(userRelation)).thenReturn(userRelation);
		UserRelation result = userRelationService.create(userRelation);
		verify(jdoTemplate).getObjectById(User.class, owner.getId()); 
		verify(jdoTemplate).makePersistent(userRelation);
		assertEquals("classmate", result.getType());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(UserRelation.class, userRelation.getId())).thenReturn(userRelation);
		Response result = userRelationService.delete(userRelation.getId());
		verify(jdoTemplate).getObjectById(UserRelation.class, userRelation.getId());
		verify(jdoTemplate).deletePersistent(userRelation);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(UserRelation.class, userRelation.getId())).thenReturn(userRelation);
		when(jdoTemplate.makePersistent(userRelation)).thenReturn(userRelation);
		UserRelation result = userRelationService.update(userRelation);
		verify(jdoTemplate).getObjectById(UserRelation.class, userRelation.getId());
		verify(jdoTemplate).makePersistent(userRelation);
		assertEquals("classmate", result.getType());
	}
	
	@Test
	public void testShow() {
		String filter = "type=='classmate'";
		String queryString = "select from " + UserRelation.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(userRelations);
		Collection<UserRelation> result = userRelationService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
