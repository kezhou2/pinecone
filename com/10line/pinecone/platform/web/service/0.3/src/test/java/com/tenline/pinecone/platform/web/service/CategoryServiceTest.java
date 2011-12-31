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

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.web.service.restful.CategoryRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"}) 
public class CategoryServiceTest extends AbstractServiceTest {

	private Category category;
	
	private List categories;
	
	private CategoryRestfulService categoryService; 	
	
	@Before
	public void testSetup() {
		categoryService = new CategoryRestfulService(persistenceManagerFactory);
		categoryService.setJdoTemplate(jdoTemplate);
		category = new Category();
		category.setId("asa");
		category.setType(Category.COM);		
		categories = new ArrayList();
		categories.add(category);
	}
	
	@After
	public void testShutdown() {	
		categoryService = null;
		categories.remove(category);
		category = null;
		categories = null;
	}

	@Test
	public void testCreate() {
		when(jdoTemplate.makePersistent(category)).thenReturn(category);
		Category result = categoryService.create(category);
		verify(jdoTemplate).makePersistent(category);
		assertEquals(Category.COM, result.getType());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Category.class, category.getId())).thenReturn(category);
		Response result = categoryService.delete(category.getId());
		verify(jdoTemplate).getObjectById(Category.class, category.getId());
		verify(jdoTemplate).deletePersistent(category);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Category.class, category.getId())).thenReturn(category);
		when(jdoTemplate.makePersistent(category)).thenReturn(category);
		Category result = categoryService.update(category);
		verify(jdoTemplate).getObjectById(Category.class, category.getId());
		verify(jdoTemplate).makePersistent(category);
		assertEquals(Category.COM, result.getType());
	}
	
	@Test
	public void testShow() {
		String filter = "id=='asa'";
		String queryString = "select from " + Category.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(categories);
		Collection<Category> result = categoryService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
