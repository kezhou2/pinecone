/**
 * 
 */
package com.tenline.pinecone.service.restful;

import java.util.Collection;

import javax.jdo.PersistenceManagerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenline.pinecone.model.Device;
import com.tenline.pinecone.model.Protocol;
import com.tenline.pinecone.service.ProtocolService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class ProtocolRestfulService extends JdoDaoSupport implements ProtocolService {
	
	/**
	 * 
	 */
	@Autowired
	public ProtocolRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.AbstractService#delete(java.lang.String)
	 */
	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Protocol.class, id));
		return Response.status(Status.OK).build();
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.ProtocolService#create(com.tenline.pinecone.model.Protocol)
	 */
	@Override
	public Protocol create(Protocol protocol) {
		// TODO Auto-generated method stub
		protocol.setDevice((Device) getJdoTemplate().getObjectById(Device.class, protocol.getDevice().getId()));
		return (Protocol) getJdoTemplate().makePersistent(protocol);
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.ProtocolService#update(com.tenline.pinecone.model.Protocol)
	 */
	@Override
	public Protocol update(Protocol protocol) {
		// TODO Auto-generated method stub
		Protocol detachedProtocol = (Protocol) getJdoTemplate().getObjectById(Protocol.class, protocol.getId());
		if (protocol.getName() != null) detachedProtocol.setName(protocol.getName());
		if (protocol.getVersion() != null) detachedProtocol.setVersion(protocol.getVersion());
		return (Protocol) getJdoTemplate().makePersistent(detachedProtocol);
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.ProtocolService#show(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Collection<Protocol> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Protocol.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return (Collection<Protocol>) getJdoTemplate().find(queryString);
	}

}
