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
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.web.service.VariableService;

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

	@Override
	public Variable create(Variable variable) {
		// TODO Auto-generated method stub
		try {
			variable.setName(URLDecoder.decode(variable.getName(), "utf-8"));
			variable.setDevice((Device) getJdoTemplate().getObjectById(Device.class, variable.getDevice().getId()));
			variable = (Variable) getJdoTemplate().makePersistent(variable);
			variable.setName(URLEncoder.encode(variable.getName(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return variable;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Variable> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Variable.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		Collection<Variable> variables = getJdoTemplate().find(queryString);
		for (Iterator<Variable> iterator = variables.iterator(); iterator.hasNext();) {
			Variable variable = iterator.next();
			try {
				variable.setName(URLEncoder.encode(variable.getName(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return variables;
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Variable.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Variable update(Variable variable) {
		// TODO Auto-generated method stub
		try {
			Variable detachedVariable = (Variable) getJdoTemplate().getObjectById(Variable.class, variable.getId());
			if (variable.getName() != null) detachedVariable.setName(URLDecoder.decode(variable.getName(), "utf-8"));
			if (variable.getType() != null) detachedVariable.setType(variable.getType());
			variable = (Variable) getJdoTemplate().makePersistent(detachedVariable);
			variable.setName(URLEncoder.encode(variable.getName(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return variable;
	}

	@Override
	public Collection<Variable> showByDevice(String filter) {
		// TODO Auto-generated method stub
		Device device = getJdoTemplate().getObjectById(Device.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")));
		Collection<Variable> variables = device.getVariables();
		for (Iterator<Variable> iterator = variables.iterator(); iterator.hasNext();) {
			Variable variable = iterator.next();
			try {
				variable.setName(URLEncoder.encode(variable.getName(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return variables;
	}

}
