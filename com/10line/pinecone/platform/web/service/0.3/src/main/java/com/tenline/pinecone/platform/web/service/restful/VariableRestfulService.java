/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.io.IOException;
import java.util.Collection;

import javax.jdo.PersistenceManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.web.service.VariableService;
import com.tenline.pinecone.platform.web.service.oauth.OAuthProvider;
import com.tenline.pinecone.platform.web.service.oauth.OAuthUtils;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class VariableRestfulService extends JdoDaoSupport implements VariableService, ApplicationContextAware {
	
	private OAuthProvider provider;
	
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
		variable.setDevice(getJdoTemplate().getObjectById(Device.class, variable.getDevice().getId()));
		return getJdoTemplate().makePersistent(variable);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Variable> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Variable.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
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
		Variable detachedVariable = (Variable) getJdoTemplate().getObjectById(Variable.class, variable.getId());
		if (variable.getName() != null) detachedVariable.setName(variable.getName());
		if (variable.getType() != null) detachedVariable.setType(variable.getType());
		return getJdoTemplate().makePersistent(detachedVariable);
	}

	@Override
	public Collection<Variable> showByDevice(String filter,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			OAuthUtils.doFilter(request, response, provider);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getJdoTemplate().getObjectById(Device.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getVariables();
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		provider = OAuthUtils.getOAuthProvider(arg0);
	}

}
