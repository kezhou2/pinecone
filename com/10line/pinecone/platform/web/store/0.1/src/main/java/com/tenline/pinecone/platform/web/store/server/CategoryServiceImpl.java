/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import java.util.Collection;

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.sdk.CategoryAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.store.client.services.CategoryService;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class CategoryServiceImpl extends AbstractService implements CategoryService {
	
	private CategoryAPI categoryAPI;

	/**
	 * 
	 */
	public CategoryServiceImpl() {
		// TODO Auto-generated constructor stub
		categoryAPI = new CategoryAPI(HOST, PORT, CONTEXT);
	}

	@Override
	public boolean delete(String id) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = categoryAPI.delete(id);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	public Category create(Category category) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = categoryAPI.create(category);
		if (response.isDone()) return (Category) response.getMessage();
		else return null;
	}

	@Override
	public Category update(Category category) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = categoryAPI.update(category);
		if (response.isDone()) return (Category) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Category> show(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = categoryAPI.show(filter);
		if (response.isDone()) return (Collection<Category>) response.getMessage();
		else return null;
	}

}
