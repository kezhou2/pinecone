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
import com.tenline.pinecone.platform.web.service.restful.UserRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"}) 
public class UserServiceTest extends AbstractServiceTest {

	private User user;
	
	private List users;
	
	private UserRestfulService userService; 	
	
	@Before
	public void testSetup() {
		userService = new UserRestfulService(persistenceManagerFactory);
		userService.setJdoTemplate(jdoTemplate);
		user = new User();
		user.setId("asa");
		user.setName("bill");		
		users = new ArrayList();
		users.add(user);
	}
	
	@After
	public void testShutdown() {	
		userService = null;
		users.remove(user);
		user = null;
		users = null;
	}

	@Test
	public void testCreate() {
		when(jdoTemplate.makePersistent(user)).thenReturn(user);
		User result = userService.create(user);
		verify(jdoTemplate).makePersistent(user);
		assertEquals("bill", result.getName());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(User.class, user.getId())).thenReturn(user);
		Response result = userService.delete(user.getId());
		verify(jdoTemplate).deletePersistent(user);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(User.class, user.getId())).thenReturn(user);
		when(jdoTemplate.makePersistent(user)).thenReturn(user);
		User result = userService.update(user);
		verify(jdoTemplate).getObjectById(User.class, user.getId());
		verify(jdoTemplate).makePersistent(user);
		assertEquals("bill", result.getName());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + User.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(users);
		Collection<User> result = userService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
