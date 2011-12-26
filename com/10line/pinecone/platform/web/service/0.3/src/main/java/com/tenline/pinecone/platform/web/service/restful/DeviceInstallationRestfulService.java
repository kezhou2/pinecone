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

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.DeviceInstallation;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.service.DeviceInstallationService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class DeviceInstallationRestfulService extends JdoDaoSupport implements DeviceInstallationService {

	/**
	 * 
	 */
	@Autowired
	public DeviceInstallationRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(DeviceInstallation.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public DeviceInstallation create(DeviceInstallation deviceInstallation) {
		// TODO Auto-generated method stub
		deviceInstallation.setDevice(getJdoTemplate().getObjectById(Device.class, deviceInstallation.getDevice().getId()));
		deviceInstallation.setUser(getJdoTemplate().getObjectById(User.class, deviceInstallation.getUser().getId()));
		return getJdoTemplate().makePersistent(deviceInstallation);
	}

	@Override
	public DeviceInstallation update(DeviceInstallation deviceInstallation) {
		// TODO Auto-generated method stub
		DeviceInstallation detachedInstallation = getJdoTemplate().getObjectById(DeviceInstallation.class, deviceInstallation.getId());
		if (deviceInstallation.isDefault() != null) detachedInstallation.setDefault(deviceInstallation.isDefault());
		if (deviceInstallation.getStatus() != null) detachedInstallation.setStatus(deviceInstallation.getStatus());
		return getJdoTemplate().makePersistent(detachedInstallation);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<DeviceInstallation> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + DeviceInstallation.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<DeviceInstallation> showByDevice(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(Device.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getDeviceInstallations();
	}

	@Override
	public Collection<DeviceInstallation> showByUser(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(User.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getDeviceInstallations();
	}

}
