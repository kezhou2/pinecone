/**
 * 
 */
package com.tenline.pinecone.platform.web.service.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.jdo.PersistenceManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tenline.pinecone.platform.web.service.ModelService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class ModelServiceImpl extends JdoDaoSupport implements ModelService {

	/**
	 * 
	 */
	@Autowired
	public ModelServiceImpl(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}
	
	@Override
	public void create(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			ObjectInputStream input = new ObjectInputStream(request.getInputStream());
			ObjectOutputStream output = new ObjectOutputStream(response.getOutputStream());
			output.writeObject(getJdoTemplate().detachCopy(getJdoTemplate().makePersistent(input.readObject())));
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Response delete(String entityClass, String id) {
		// TODO Auto-generated method stub
		try {
			getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Class.forName(entityClass), id));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(Status.OK).build();
	}

	@Override
	public void update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			ObjectInputStream input = new ObjectInputStream(request.getInputStream());
			ObjectOutputStream output = new ObjectOutputStream(response.getOutputStream());
			output.writeObject(getJdoTemplate().detachCopy(getJdoTemplate().makePersistent(input.readObject())));
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public void show(String entityClass, String filter, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			String queryString = "select from " + entityClass;
			if (!filter.equals("all")) queryString += " where " + filter;
			ObjectOutputStream output = new ObjectOutputStream(response.getOutputStream());
			output.writeObject(getJdoTemplate().detachCopyAll(getJdoTemplate().find(queryString)));
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
