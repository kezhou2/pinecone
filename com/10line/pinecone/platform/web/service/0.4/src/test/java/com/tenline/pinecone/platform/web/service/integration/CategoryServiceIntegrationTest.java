/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.sdk.CategoryAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
public class CategoryServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private Category category;
	
	private CategoryAPI categoryAPI;
	
	@Before
	public void testSetup() {
		category = new Category();
		category.setType(Category.COM);
		category.setName("huishi");
		category.setDomain(Category.DOMAIN_PET);
		category.setSubdomain(Category.SUB_DOMAIN_AQUARIUM);
		categoryAPI = new CategoryAPI("localhost", "8888", "service");
	}
	
	@After
	public void testShutdown() {
		category = null;
		categoryAPI = null;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCRUD() throws Exception {
		APIResponse response = categoryAPI.create(category);
		if (response.isDone()) {
			category = (Category) response.getMessage();
			assertEquals("huishi", category.getName());
			assertEquals(Category.COM, category.getType());
			assertEquals(Category.DOMAIN_PET, category.getDomain());
			assertEquals(Category.SUB_DOMAIN_AQUARIUM, category.getSubdomain());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		category.setName("efish");
		category.setType(Category.ORG);
		category.setDomain(Category.DOMAIN_SECURITY);
		category.setSubdomain(Category.SUB_DOMAIN_CAMERA);
		response = categoryAPI.update(category);
		if (response.isDone()) {
			category = (Category) response.getMessage();
			assertEquals("efish", category.getName());
			assertEquals(Category.ORG, category.getType());
			assertEquals(Category.DOMAIN_SECURITY, category.getDomain());
			assertEquals(Category.SUB_DOMAIN_CAMERA, category.getSubdomain());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = categoryAPI.show("id=='"+category.getId()+"'");
		if (response.isDone()) {
			assertEquals(1, ((Collection<Category>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = categoryAPI.delete(category.getId());
		if (response.isDone()) {
			assertEquals("Category Deleted!", response.getMessage().toString());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = categoryAPI.show("id=='"+category.getId()+"'");
		if (response.isDone()) {
			assertEquals(0, ((Collection<Category>) response.getMessage()).size());
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
	}

}
