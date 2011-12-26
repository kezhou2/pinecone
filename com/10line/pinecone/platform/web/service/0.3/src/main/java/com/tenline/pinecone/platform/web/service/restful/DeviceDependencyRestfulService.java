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
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.DeviceDependency;
import com.tenline.pinecone.platform.web.service.DeviceDependencyService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class DeviceDependencyRestfulService extends JdoDaoSupport implements DeviceDependencyService {

	/**
	 * 
	 */
	@Autowired
	public DeviceDependencyRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(DeviceDependency.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public DeviceDependency create(DeviceDependency deviceDependency) {
		// TODO Auto-generated method stub
		deviceDependency.setConsumer(getJdoTemplate().getObjectById(Consumer.class, deviceDependency.getConsumer().getId()));
		deviceDependency.setDevice(getJdoTemplate().getObjectById(Device.class, deviceDependency.getDevice().getId()));
		return getJdoTemplate().makePersistent(deviceDependency);
	}

	@Override
	public DeviceDependency update(DeviceDependency deviceDependency) {
		// TODO Auto-generated method stub
		DeviceDependency detachedDependency = getJdoTemplate().getObjectById(DeviceDependency.class, deviceDependency.getId());
		if (deviceDependency.isOptional() != null) detachedDependency.setOptional(deviceDependency.isOptional());
		return getJdoTemplate().makePersistent(detachedDependency);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<DeviceDependency> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + DeviceDependency.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<DeviceDependency> showByConsumer(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(Consumer.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getDeviceDependencies();
	}

	@Override
	public Collection<DeviceDependency> showByDevice(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(Device.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getDeviceDependencies();
	}

}
