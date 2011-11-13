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

import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.web.service.restful.ConsumerRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"}) 
public class ConsumerServiceTest extends AbstractServiceTest {

	private Consumer consumer;
	
	private List consumers;
	
	private ConsumerRestfulService consumerService;
	
	@Before
	public void testSetup() {
		consumerService = new ConsumerRestfulService(persistenceManagerFactory);
		consumerService.setJdoTemplate(jdoTemplate);
		consumer = new Consumer();
		consumer.setId("asa");
		consumer.setDisplayName("test");		
		consumers = new ArrayList();
		consumers.add(consumer);
	}
	
	@After
	public void testShutdown() {	
		consumerService = null;
		consumers.remove(consumer);
		consumer = null;
		consumers = null;
	}
	
	@Test
	public void testCreate() {
		when(jdoTemplate.makePersistent(consumer)).thenReturn(consumer);
		Consumer result = consumerService.create(consumer);
		verify(jdoTemplate).makePersistent(consumer);
		assertEquals("test", result.getDisplayName());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Consumer.class, consumer.getId())).thenReturn(consumer);
		Response result = consumerService.delete(consumer.getId());
		verify(jdoTemplate).deletePersistent(consumer);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Consumer.class, consumer.getId())).thenReturn(consumer);
		when(jdoTemplate.makePersistent(consumer)).thenReturn(consumer);
		Consumer result = consumerService.update(consumer);
		verify(jdoTemplate).getObjectById(Consumer.class, consumer.getId());
		verify(jdoTemplate).makePersistent(consumer);
		assertEquals("test", result.getDisplayName());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + Consumer.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(consumers);
		Collection<Consumer> result = consumerService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
