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

import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.service.ApplicationService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class ApplicationRestfulService extends JdoDaoSupport implements ApplicationService {

	/**
	 * 
	 */
	@Autowired
	public ApplicationRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Application.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Application create(Application application) {
		// TODO Auto-generated method stub
		application.setUser((User) getJdoTemplate().getObjectById(User.class, application.getUser().getId()));
		return getJdoTemplate().makePersistent(application);
	}

	@Override
	public Application update(Application application) {
		// TODO Auto-generated method stub
		Application detachedApplication = getJdoTemplate().getObjectById(Application.class, application.getId());
		if (application.getConsumerId() != null) detachedApplication.setConsumerId(application.getConsumerId());
		return getJdoTemplate().makePersistent(detachedApplication);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Application> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Application.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<Application> showByUser(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(User.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'"))).getApplications();
	}

}
