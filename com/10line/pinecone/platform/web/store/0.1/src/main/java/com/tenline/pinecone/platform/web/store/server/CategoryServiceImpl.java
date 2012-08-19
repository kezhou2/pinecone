/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import java.util.Collection;

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.store.client.services.CategoryService;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class CategoryServiceImpl extends AbstractService implements CategoryService {
	
	/**
	 * 
	 */
	public CategoryServiceImpl() {
		super();
	}

	@Override
	public boolean delete(Entity entity) throws Exception {
		APIResponse response = modelAPI.delete(entity);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	public Category create(Category category) throws Exception {
		APIResponse response = modelAPI.create(category);
		if (response.isDone()) return (Category) response.getMessage();
		else return null;
	}

	@Override
	public Category update(Category category) throws Exception {
		APIResponse response = modelAPI.update(category);
		if (response.isDone()) return (Category) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Category> show(String filter) throws Exception {
		APIResponse response = modelAPI.show(Category.class,filter);
		if (response.isDone()) return (Collection<Category>) response.getMessage();
		else return null;
	}

}
