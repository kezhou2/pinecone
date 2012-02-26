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

import com.tenline.pinecone.platform.model.Bank;
import com.tenline.pinecone.platform.web.service.BankService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class BankRestfulService extends JdoDaoSupport implements BankService {

	/**
	 * 
	 */
	@Autowired
	public BankRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Bank.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Bank create(Bank bank) {
		// TODO Auto-generated method stub
		return getJdoTemplate().makePersistent(bank);
	}

	@Override
	public Bank update(Bank bank) {
		// TODO Auto-generated method stub
		Bank detachedBank = getJdoTemplate().getObjectById(Bank.class, bank.getId());
		if (bank.getName() != null) detachedBank.setName(bank.getName());
		if (bank.getAddress() != null) detachedBank.setAddress(bank.getAddress());
		return getJdoTemplate().makePersistent(detachedBank);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Bank> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Bank.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

}
