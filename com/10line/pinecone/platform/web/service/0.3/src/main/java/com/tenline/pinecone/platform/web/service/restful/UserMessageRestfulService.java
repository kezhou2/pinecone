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
import com.tenline.pinecone.platform.model.UserMessage;
import com.tenline.pinecone.platform.web.service.UserMessageService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class UserMessageRestfulService extends JdoDaoSupport implements UserMessageService {

	/**
	 * 
	 */
	@Autowired
	public UserMessageRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(UserMessage.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public UserMessage create(UserMessage userMessage) {
		// TODO Auto-generated method stub
		userMessage.setReceiver(getJdoTemplate().getObjectById(User.class, userMessage.getReceiver().getId()));
		userMessage.setSender(getJdoTemplate().getObjectById(User.class, userMessage.getSender().getId()));
		return getJdoTemplate().makePersistent(userMessage);
	}

	@Override
	public UserMessage update(UserMessage userMessage) {
		// TODO Auto-generated method stub
		UserMessage detachedMessage = getJdoTemplate().getObjectById(UserMessage.class, userMessage.getId());
		if (userMessage.isRead() != null) detachedMessage.setRead(userMessage.isRead());
		if (userMessage.getTitle() != null) detachedMessage.setTitle(userMessage.getTitle());
		if (userMessage.getContent() != null) detachedMessage.setContent(userMessage.getContent());
		return getJdoTemplate().makePersistent(detachedMessage);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<UserMessage> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + UserMessage.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<UserMessage> showBySender(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(User.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getSentMessages();
	}

	@Override
	public Collection<UserMessage> showByReceiver(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(User.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getReceivedMessages();
	}

}
