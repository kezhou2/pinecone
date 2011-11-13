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
import com.tenline.pinecone.platform.web.service.ConsumerService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class ConsumerRestfulService extends JdoDaoSupport implements ConsumerService {

	/**
	 * 
	 */
	@Autowired
	public ConsumerRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Consumer.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Consumer create(Consumer consumer) {
		// TODO Auto-generated method stub
		return getJdoTemplate().makePersistent(consumer);
	}

	@Override
	public Consumer update(Consumer consumer) {
		// TODO Auto-generated method stub
		Consumer detachedConsumer = this.getJdoTemplate().getObjectById(Consumer.class, consumer.getId());
		if (consumer.getConnectURI() != null) detachedConsumer.setConnectURI(consumer.getConnectURI());
		if (consumer.getDisplayName() != null) detachedConsumer.setDisplayName(consumer.getDisplayName());
		return getJdoTemplate().makePersistent(detachedConsumer);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Consumer> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Consumer.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

}
