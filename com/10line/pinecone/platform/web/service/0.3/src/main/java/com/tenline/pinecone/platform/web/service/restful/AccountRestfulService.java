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

import com.tenline.pinecone.platform.model.Account;
import com.tenline.pinecone.platform.model.Bank;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.service.AccountService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class AccountRestfulService extends JdoDaoSupport implements AccountService {

	/**
	 * 
	 */
	@Autowired
	public AccountRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Account.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Account create(Account account) {
		// TODO Auto-generated method stub
		account.setUser(getJdoTemplate().getObjectById(User.class, account.getUser().getId()));
		account.setBank(getJdoTemplate().getObjectById(Bank.class, account.getBank().getId()));
		return getJdoTemplate().makePersistent(account);
	}

	@Override
	public Account update(Account account) {
		// TODO Auto-generated method stub
		Account detachedAccount = getJdoTemplate().getObjectById(Account.class, account.getId());
		if (account.getNumber() != null) detachedAccount.setNumber(account.getNumber());
		return getJdoTemplate().makePersistent(detachedAccount);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Account> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Account.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<Account> showByUser(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(User.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getAccounts();
	}

	@Override
	public Collection<Account> showByBank(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(Bank.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getAccounts();
	}

}
