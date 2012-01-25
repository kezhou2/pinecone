/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.util.Collection;

import javax.jdo.PersistenceManagerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.web.service.CategoryService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class CategoryRestfulService extends JdoDaoSupport implements CategoryService {

	/**
	 * 
	 */
	@Autowired
	public CategoryRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Category.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Category create(Category category) {
		// TODO Auto-generated method stub
		return getJdoTemplate().makePersistent(category);
	}

	@Override
	public Category update(Category category) {
		// TODO Auto-generated method stub
		Category detachedCategory = getJdoTemplate().getObjectById(Category.class, category.getId());
		if (category.getType() != null) detachedCategory.setType(category.getType());
		if (category.getName() != null) detachedCategory.setName(category.getName());
		if (category.getDomain() != null) detachedCategory.setDomain(category.getDomain());
		if (category.getSubdomain() != null) detachedCategory.setSubdomain(category.getSubdomain());
		return getJdoTemplate().makePersistent(detachedCategory);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Category> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Category.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

}
