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
import com.tenline.pinecone.platform.model.Transaction;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.service.TransactionService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class TransactionRestfulService extends JdoDaoSupport implements TransactionService {

	/**
	 * 
	 */
	@Autowired
	public TransactionRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Transaction.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Transaction create(Transaction transaction) {
		// TODO Auto-generated method stub
		transaction.setUser(getJdoTemplate().getObjectById(User.class, transaction.getUser().getId()));
		transaction.setConsumer(getJdoTemplate().getObjectById(Consumer.class, transaction.getConsumer().getId()));
		return getJdoTemplate().makePersistent(transaction);
	}

	@Override
	public Transaction update(Transaction transaction) {
		// TODO Auto-generated method stub
		Transaction detachedTransaction = getJdoTemplate().getObjectById(Transaction.class, transaction.getId());
		if (transaction.getType() != null) detachedTransaction.setType(transaction.getType());
		if (transaction.getTimestamp() != null) detachedTransaction.setTimestamp(transaction.getTimestamp());
		if (transaction.getNut() != null) detachedTransaction.setNut(transaction.getNut());
		return getJdoTemplate().makePersistent(detachedTransaction);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Transaction> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Transaction.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<Transaction> showByUser(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(User.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getTransactions();
	}

	@Override
	public Collection<Transaction> showByConsumer(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(Consumer.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getTransactions();
	}

}
