/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.util.Collection;
import java.util.Date;

import javax.jdo.PersistenceManagerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenline.pinecone.platform.model.Account;
import com.tenline.pinecone.platform.model.Exchange;
import com.tenline.pinecone.platform.web.service.PaymentService;

/**
 * @author wangyq
 * 
 */
@Service
@Transactional
public class PaymentRestfulService extends JdoDaoSupport implements
		PaymentService {

	/**
	 * 
	 */
	@Autowired
	public PaymentRestfulService(
			PersistenceManagerFactory persistenceManagerFactory) {
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Exchange> show(String filter) {
		String queryString = "select from " + Exchange.class.getName();
		if (!filter.equals("all"))
			queryString += " where " + filter;
		return getJdoTemplate().find(queryString);

	}

	@SuppressWarnings("unchecked")
	public Collection<Exchange> show(Date from, Date to) {
		String queryString = "select from " + Exchange.class.getName()
				+ " where timestamp >= " + from + " and timestamp<= " + to;
		return getJdoTemplate().find(queryString);

	}

	@Override
	public Response delete(String id) {
		getJdoTemplate().deletePersistent(
				getJdoTemplate().getObjectById(Exchange.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Exchange create(Exchange exchange) {
		exchange.setAccount(getJdoTemplate().getObjectById(Account.class,
				exchange.getAccount().getId()));
		return getJdoTemplate().makePersistent(exchange);
	}

	@Override
	public Exchange update(Exchange exchange) {
		Exchange detachedExchange = getJdoTemplate().getObjectById(
				Exchange.class, exchange.getId());
		if (exchange.getType() != null)
			detachedExchange.setType(exchange.getType());
		if (exchange.getTimestamp() != null)
			detachedExchange.setTimestamp(exchange.getTimestamp());
		if (exchange.getNut() != null)
			detachedExchange.setNut(exchange.getNut());
		return getJdoTemplate().makePersistent(detachedExchange);
	}

	@Override
	public Collection<Exchange> showByAccount(String filter) {
		return getJdoTemplate().getObjectById(
				Account.class,
				filter.substring(filter.indexOf("'") + 1,
						filter.lastIndexOf("'"))).getExchanges();
	}

}
