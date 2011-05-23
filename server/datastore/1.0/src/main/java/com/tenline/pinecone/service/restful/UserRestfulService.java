/**
 * 
 */
package com.tenline.pinecone.service.restful;

import java.util.Collection;

import javax.jdo.PersistenceManagerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenline.pinecone.model.User;
import com.tenline.pinecone.service.UserService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class UserRestfulService extends JdoDaoSupport implements UserService {

	/**
	 * 
	 */
	@Autowired
	public UserRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.UserService#create(com.tenline.pinecone.model.User)
	 */
	@Override
	public User create(User user) {
		// TODO Auto-generated method stub
		return (User) getJdoTemplate().makePersistent(user);
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.UserService#show(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Collection<User> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + User.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return (Collection<User>) getJdoTemplate().find(queryString);
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.UserService#update(com.tenline.pinecone.model.User)
	 */
	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		User detachedUser = (User) getJdoTemplate().getObjectById(User.class, user.getId());
		if (user.getSnsId() != null) detachedUser.setSnsId(user.getSnsId());
		return (User) getJdoTemplate().makePersistent(detachedUser);
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.AbstractService#delete(java.lang.String)
	 */
	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(User.class, id));
		return Response.status(Status.OK).build();
	}

}
