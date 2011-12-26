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

	private User sender;
	
	private User receiver;
	
	private UserRelation relation;
	
	private List relations;
	
	private UserRelationRestfulService relationService;
	
	@Before
	public void testSetup() {
		relationService = new UserRelationRestfulService(persistenceManagerFactory);
		relationService.setJdoTemplate(jdoTemplate);
		relation = new UserRelation();
		relation.setId("asa");
		sender = new User();
		sender.setId("asa");
		relation.setSender(sender);
		receiver = new User();
		receiver.setId("ccc");
		relation.setReceiver(receiver);
		relations = new ArrayList();
		relations.add(relation);
	}
	
	@After
	public void testShutdown() {	
		relationService = null;
		relations.remove(relation);
		sender = null;
		receiver = null;
		relation = null;
		relations = null;
	}

	@Test
	public void testCreate() { 
		when(jdoTemplate.getObjectById(User.class, sender.getId())).thenReturn(sender);
		when(jdoTemplate.getObjectById(User.class, receiver.getId())).thenReturn(receiver);
		when(jdoTemplate.makePersistent(relation)).thenReturn(relation);
		UserRelation result = relationService.create(relation);
		verify(jdoTemplate).getObjectById(User.class, sender.getId());
		verify(jdoTemplate).getObjectById(User.class, receiver.getId());
		verify(jdoTemplate).makePersistent(relation);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(UserRelation.class, relation.getId())).thenReturn(relation);
		Response result = relationService.delete(relation.getId());
		verify(jdoTemplate).getObjectById(UserRelation.class, relation.getId());
		verify(jdoTemplate).deletePersistent(relation);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(UserRelation.class, relation.getId())).thenReturn(relation);
		when(jdoTemplate.makePersistent(relation)).thenReturn(relation);
		UserRelation result = relationService.update(relation);
		verify(jdoTemplate).getObjectById(UserRelation.class, relation.getId());
		verify(jdoTemplate).makePersistent(relation);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + UserRelation.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(relations);
		Collection<UserRelation> result = relationService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
