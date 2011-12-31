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
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.web.service.restful.MailRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class MailServiceTest extends AbstractServiceTest {

	private User sender;
	
	private User receiver;
	
	private Mail mail;
	
	private List mails;
	
	private MailRestfulService mailService;
	
	@Before
	public void testSetup() {
		mailService = new MailRestfulService(persistenceManagerFactory);
		mailService.setJdoTemplate(jdoTemplate);
		mail = new Mail();
		mail.setId("asa");
		mail.setRead(true);
		mail.setContent("aaaa");
		sender = new User();
		sender.setId("asa");
		mail.setSender(sender);
		receiver = new User();
		receiver.setId("ccc");
		mail.setReceiver(receiver);
		mails = new ArrayList();
		mails.add(mail);
	}
	
	@After
	public void testShutdown() {	
		mailService = null;
		mails.remove(mail);
		sender = null;
		receiver = null;
		mail = null;
		mails = null;
	}

	@Test
	public void testCreate() { 
		when(jdoTemplate.getObjectById(User.class, sender.getId())).thenReturn(sender);
		when(jdoTemplate.getObjectById(User.class, receiver.getId())).thenReturn(receiver);
		when(jdoTemplate.makePersistent(mail)).thenReturn(mail);
		Mail result = mailService.create(mail);
		verify(jdoTemplate).getObjectById(User.class, sender.getId());
		verify(jdoTemplate).getObjectById(User.class, receiver.getId());
		verify(jdoTemplate).makePersistent(mail);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Mail.class, mail.getId())).thenReturn(mail);
		Response result = mailService.delete(mail.getId());
		verify(jdoTemplate).getObjectById(Mail.class, mail.getId());
		verify(jdoTemplate).deletePersistent(mail);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Mail.class, mail.getId())).thenReturn(mail);
		when(jdoTemplate.makePersistent(mail)).thenReturn(mail);
		Mail result = mailService.update(mail);
		verify(jdoTemplate).getObjectById(Mail.class, mail.getId());
		verify(jdoTemplate).makePersistent(mail);
		assertEquals("asa", result.getId());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + Mail.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(mails);
		Collection<Mail> result = mailService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
