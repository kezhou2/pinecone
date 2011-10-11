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

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.UserRelation;
import com.tenline.pinecone.platform.web.service.UserRelationService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class UserRelationRestfulService extends JdoDaoSupport implements UserRelationService {

	/**
	 * 
	 */
	@Autowired
	public UserRelationRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(UserRelation.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public UserRelation create(UserRelation userRelation) {
		// TODO Auto-generated method stub
		userRelation.setOwner((User) getJdoTemplate().getObjectById(User.class, userRelation.getOwner().getId()));
		return getJdoTemplate().makePersistent(userRelation);
	}

	@Override
	public UserRelation update(UserRelation userRelation) {
		// TODO Auto-generated method stub
		UserRelation detachedUserRelation = getJdoTemplate().getObjectById(UserRelation.class, userRelation.getId());
		if (userRelation.getType() != null) detachedUserRelation.setType(userRelation.getType());
		if (userRelation.getUserId() != null) detachedUserRelation.setUserId(userRelation.getUserId());
		return getJdoTemplate().makePersistent(detachedUserRelation);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<UserRelation> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + UserRelation.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<UserRelation> showByUser(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(User.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'"))).getUserRelations();
	}

}
