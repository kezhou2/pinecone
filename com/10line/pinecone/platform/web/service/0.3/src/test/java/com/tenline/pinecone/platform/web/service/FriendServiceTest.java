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
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.web.service.restful.FriendRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class FriendServiceTest extends AbstractServiceTest {

	private User user;
	
	private Friend friend;
	
	private List friends;
	
	private FriendRestfulService friendService;
	
	@Before
	public void testSetup() {
		friendService = new FriendRestfulService(persistenceManagerFactory);
		friendService.setJdoTemplate(jdoTemplate);
		friend = new Friend();
		friend.setId("asa");
		user = new User();
		user.setId("asa");
		friend.setUser(user);
		friends = new ArrayList();
		friends.add(friend);
	}
	
	@After
	public void testShutdown() {	
		friendService = null;
		friends.remove(friend);
		user = null;
		friend = null;
		friends = null;
	}

	@Test
	public void testCreate() { 
		when(jdoTemplate.getObjectById(User.class, user.getId())).thenReturn(user);
		when(jdoTemplate.makePersistent(friend)).thenReturn(friend);
		Friend result = friendService.create(friend);
		verify(jdoTemplate).getObjectById(User.class, user.getId()); 
		verify(jdoTemplate).makePersistent(friend);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Friend.class, friend.getId())).thenReturn(friend);
		Response result = friendService.delete(friend.getId());
		verify(jdoTemplate).getObjectById(Friend.class, friend.getId());
		verify(jdoTemplate).deletePersistent(friend);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + Friend.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(friends);
		Collection<Friend> result = friendService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
