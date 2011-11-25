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

import com.tenline.pinecone.platform.model.ConsumerInstallation;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.service.ConsumerInstallationService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class ConsumerInstallationRestfulService extends JdoDaoSupport implements ConsumerInstallationService {

	/**
	 * 
	 */
	@Autowired
	public ConsumerInstallationRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(ConsumerInstallation.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public ConsumerInstallation create(ConsumerInstallation consumerInstallation) {
		// TODO Auto-generated method stub
		consumerInstallation.setUser((User) getJdoTemplate().getObjectById(User.class, consumerInstallation.getUser().getId()));
		return getJdoTemplate().makePersistent(consumerInstallation);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<ConsumerInstallation> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + ConsumerInstallation.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<ConsumerInstallation> showByUser(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(User.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'"))).getConsumerInstallations();
	}

}
