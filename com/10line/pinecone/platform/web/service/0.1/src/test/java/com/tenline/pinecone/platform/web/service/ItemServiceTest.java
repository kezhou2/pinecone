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

import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.web.service.restful.ItemRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ItemServiceTest extends AbstractServiceTest {
	
	private Variable variable;
	
	private Item item;
	
	private List items;
	
	private ItemRestfulService itemService;

	@Before
	public void testSetup() {
		itemService = new ItemRestfulService(persistenceManagerFactory);
		itemService.setJdoTemplate(jdoTemplate);
		item = new Item();
		item.setId("asa");
		item.setValue("1");
		variable = new Variable();
		variable.setId("asa");
		item.setVariable(variable);
		items = new ArrayList();
		items.add(item);
	}
	
	@After
	public void testShutdown() {	
		itemService = null;
		items.remove(item);
		variable = null;
		item = null;
		items = null;
	}

	@Test
	public void testCreate() { 
		when(jdoTemplate.getObjectById(Variable.class, variable.getId())).thenReturn(variable);
		when(jdoTemplate.makePersistent(item)).thenReturn(item);
		Item result = itemService.create(item);
		verify(jdoTemplate).getObjectById(Variable.class, variable.getId());
		verify(jdoTemplate).makePersistent(item);
		assertEquals("1", result.getValue());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Item.class, item.getId())).thenReturn(item);
		Response result = itemService.delete(item.getId());
		verify(jdoTemplate).deletePersistent(item);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Item.class, item.getId())).thenReturn(item);
		when(jdoTemplate.makePersistent(item)).thenReturn(item);
		Item result = itemService.update(item);
		verify(jdoTemplate).getObjectById(Item.class, item.getId());
		verify(jdoTemplate).makePersistent(item);
		assertEquals("1", result.getValue());
	}
	
	@Test
	public void testShow() {
		String filter = "value=='1'";
		String queryString = "select from " + Item.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(items);
		Collection<Item> result = itemService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
