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
import com.tenline.pinecone.model.Variable;
import com.tenline.pinecone.service.VariableService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class VariableRestfulService extends JdoDaoSupport implements VariableService {
	
	/**
	 * 
	 */
	@Autowired
	public VariableRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.VariableService#create(com.tenline.pinecone.model.Variable)
	 */
	@Override
	public Variable create(Variable variable) {
		// TODO Auto-generated method stub
		variable.setDevice((Device) getJdoTemplate().getObjectById(Device.class, variable.getDevice().getId()));
		return (Variable) getJdoTemplate().makePersistent(variable);
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.VariableService#show(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Collection<Variable> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Variable.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return (Collection<Variable>) getJdoTemplate().find(queryString);
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.AbstractService#delete(java.lang.String)
	 */
	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Variable.class, id));
		return Response.status(Status.OK).build();
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.VariableService#update(com.tenline.pinecone.model.Variable)
	 */
	@Override
	public Variable update(Variable variable) {
		// TODO Auto-generated method stub
		Variable detachedVariable = (Variable) getJdoTemplate().getObjectById(Variable.class, variable.getId());
		if (variable.getName() != null) detachedVariable.setName(variable.getName());
		if (variable.getType() != null) detachedVariable.setType(variable.getType());
		return (Variable) getJdoTemplate().makePersistent(detachedVariable);
	}

}
