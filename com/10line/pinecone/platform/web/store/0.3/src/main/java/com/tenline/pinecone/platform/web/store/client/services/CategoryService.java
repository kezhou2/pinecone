/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.services;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.Category;

/**
 * @author Bill
 *
 */
@RemoteServiceRelativePath("category/service")
public interface CategoryService extends AbstractService {

	/**
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public Category create(Category category) throws Exception;
	
	/**
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public Category update(Category category) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Category> show(String filter) throws Exception;
	
}
