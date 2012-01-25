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
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.web.service.DriverService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class DriverRestfulService extends JdoDaoSupport implements DriverService {

	/**
	 * 
	 */
	@Autowired
	public DriverRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Driver.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Driver create(Driver driver) {
		// TODO Auto-generated method stub
		driver.setCategory(getJdoTemplate().getObjectById(Category.class, driver.getCategory().getId()));
		return getJdoTemplate().makePersistent(driver);
	}

	@Override
	public Driver update(Driver driver) {
		// TODO Auto-generated method stub
		Driver detachedDriver = getJdoTemplate().getObjectById(Driver.class, driver.getId());
		if (driver.getIcon() != null) detachedDriver.setIcon(driver.getIcon());
		if (driver.getName() != null) detachedDriver.setName(driver.getName());
		if (driver.getVersion() != null) detachedDriver.setVersion(driver.getVersion());
		if (driver.getAlias() != null) detachedDriver.setAlias(driver.getAlias());
		return getJdoTemplate().makePersistent(detachedDriver);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Driver> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Driver.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<Driver> showByCategory(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(Category.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getDrivers();
	}

}
