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
import com.tenline.pinecone.platform.model.UserMessage;
import com.tenline.pinecone.platform.web.service.restful.UserMessageRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class UserMessageServiceTest extends AbstractServiceTest {

	private User sender;
	
	private User receiver;
	
	private UserMessage message;
	
	private List messages;
	
	private UserMessageRestfulService messageService;
	
	@Before
	public void testSetup() {
		messageService = new UserMessageRestfulService(persistenceManagerFactory);
		messageService.setJdoTemplate(jdoTemplate);
		message = new UserMessage();
		message.setId("asa");
		message.setRead(true);
		message.setContent("aaaa");
		sender = new User();
		sender.setId("asa");
		message.setSender(sender);
		receiver = new User();
		receiver.setId("ccc");
		message.setReceiver(receiver);
		messages = new ArrayList();
		messages.add(message);
	}
	
	@After
	public void testShutdown() {	
		messageService = null;
		messages.remove(message);
		sender = null;
		receiver = null;
		message = null;
		messages = null;
	}

	@Test
	public void testCreate() { 
		when(jdoTemplate.getObjectById(User.class, sender.getId())).thenReturn(sender);
		when(jdoTemplate.getObjectById(User.class, receiver.getId())).thenReturn(receiver);
		when(jdoTemplate.makePersistent(message)).thenReturn(message);
		UserMessage result = messageService.create(message);
		verify(jdoTemplate).getObjectById(User.class, sender.getId());
		verify(jdoTemplate).getObjectById(User.class, receiver.getId());
		verify(jdoTemplate).makePersistent(message);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(UserMessage.class, message.getId())).thenReturn(message);
		Response result = messageService.delete(message.getId());
		verify(jdoTemplate).getObjectById(UserMessage.class, message.getId());
		verify(jdoTemplate).deletePersistent(message);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(UserMessage.class, message.getId())).thenReturn(message);
		when(jdoTemplate.makePersistent(message)).thenReturn(message);
		UserMessage result = messageService.update(message);
		verify(jdoTemplate).getObjectById(UserMessage.class, message.getId());
		verify(jdoTemplate).makePersistent(message);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + UserMessage.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(messages);
		Collection<UserMessage> result = messageService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
