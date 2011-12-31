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

import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Dependency;
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.web.service.DependencyService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class DependencyRestfulService extends JdoDaoSupport implements DependencyService {

	/**
	 * 
	 */
	@Autowired
	public DependencyRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Dependency.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Dependency create(Dependency dependency) {
		// TODO Auto-generated method stub
		dependency.setConsumer(getJdoTemplate().getObjectById(Consumer.class, dependency.getConsumer().getId()));
		dependency.setDriver(getJdoTemplate().getObjectById(Driver.class, dependency.getDriver().getId()));
		return getJdoTemplate().makePersistent(dependency);
	}

	@Override
	public Dependency update(Dependency dependency) {
		// TODO Auto-generated method stub
		Dependency detachedDependency = getJdoTemplate().getObjectById(Dependency.class, dependency.getId());
		if (dependency.isOptional() != null) detachedDependency.setOptional(dependency.isOptional());
		return getJdoTemplate().makePersistent(detachedDependency);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Dependency> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Dependency.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<Dependency> showByConsumer(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(Consumer.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getDependencies();
	}

	@Override
	public Collection<Dependency> showByDriver(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(Driver.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getDependencies();
	}

}
