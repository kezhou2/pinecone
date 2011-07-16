/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;

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
		try {
			device.setName(URLDecoder.decode(device.getName(), "utf-8"));
			device.setUser((User) getJdoTemplate().getObjectById(User.class, device.getUser().getId()));
			device = (Device) getJdoTemplate().makePersistent(device);
			device.setName(URLEncoder.encode(device.getName(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return device;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Device> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Device.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		Collection<Device> devices = getJdoTemplate().find(queryString);
		for (Iterator<Device> iterator = devices.iterator(); iterator.hasNext();) {
			try {
				Device device = iterator.next();
				device.setName(URLEncoder.encode(device.getName(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return devices;
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
		try {
			Device detachedDevice = (Device) getJdoTemplate().getObjectById(Device.class, device.getId());
			if (device.getName() != null) detachedDevice.setName(URLDecoder.decode(device.getName(), "utf-8"));
			if (device.getSymbolicName() != null) detachedDevice.setSymbolicName(device.getSymbolicName());
			if (device.getVersion() != null) detachedDevice.setVersion(device.getVersion());
			device = (Device) getJdoTemplate().makePersistent(detachedDevice);
			device.setName(URLEncoder.encode(device.getName(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return device;
	}

	@Override
	public Collection<Device> showByUser(String filter) {
		// TODO Auto-generated method stub
		User user = getJdoTemplate().getObjectById(User.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")));
		Collection<Device> devices = user.getDevices();
		for (Iterator<Device> iterator = devices.iterator(); iterator.hasNext();) {
			try {
				Device device = iterator.next();
				device.setName(URLEncoder.encode(device.getName(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return devices;
	}

}
