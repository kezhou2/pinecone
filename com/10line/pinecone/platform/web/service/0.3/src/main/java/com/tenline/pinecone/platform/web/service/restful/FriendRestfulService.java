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
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.web.service.FriendService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class FriendRestfulService extends JdoDaoSupport implements FriendService {

	/**
	 * 
	 */
	@Autowired
	public FriendRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Friend.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Friend create(Friend friend) {
		// TODO Auto-generated method stub
		friend.setUser((User) getJdoTemplate().getObjectById(User.class, friend.getUser().getId()));
		return getJdoTemplate().makePersistent(friend);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Friend> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Friend.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<Friend> showByUser(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(User.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'"))).getFriends();
	}

}
