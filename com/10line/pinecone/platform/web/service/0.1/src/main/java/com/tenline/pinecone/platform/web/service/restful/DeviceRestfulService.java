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
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.service.DeviceService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class DeviceRestfulService extends JdoDaoSupport implements DeviceService {

	/**
	 * 
	 */
	@Autowired
	public DeviceRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Device create(Device device) {
		// TODO Auto-generated method stub
		device.setUser((User) getJdoTemplate().getObjectById(User.class, device.getUser().getId()));
		return (Device) getJdoTemplate().makePersistent(device);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Device> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Device.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return (Collection<Device>) getJdoTemplate().find(queryString);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Device.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Device update(Device device) {
		// TODO Auto-generated method stub
		Device detachedDevice = (Device) getJdoTemplate().getObjectById(Device.class, device.getId());
		if (device.getName() != null) detachedDevice.setName(device.getName());
		if (device.getGroupId() != null) detachedDevice.setGroupId(device.getGroupId());
		if (device.getArtifactId() != null) detachedDevice.setArtifactId(device.getArtifactId());
		if (device.getVersion() != null) detachedDevice.setVersion(device.getVersion());
		return (Device) getJdoTemplate().makePersistent(detachedDevice);
	}

}
