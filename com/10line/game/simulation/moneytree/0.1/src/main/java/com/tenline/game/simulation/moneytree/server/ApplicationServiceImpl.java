/**
 * 
 */
package com.tenline.game.simulation.moneytree.server;

import java.util.Collection;

import com.tenline.game.simulation.moneytree.client.services.ApplicationService;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.sdk.ApplicationAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class ApplicationServiceImpl extends AbstractService implements ApplicationService {

	private ApplicationAPI applicationAPI;
	
	/**
	 * 
	 */
	public ApplicationServiceImpl() {
		// TODO Auto-generated constructor stub
		applicationAPI = new ApplicationAPI(HOST, PORT, CONTEXT);
	}

	@Override
	public boolean delete(String id) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = applicationAPI.delete(id);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Application> show(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = applicationAPI.show(filter);
		if (response.isDone()) return (Collection<Application>) response.getMessage();
		else return null;
	}

	@Override
	public Application create(Application application) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = applicationAPI.create(application);
		if (response.isDone()) return (Application) response.getMessage();
		else return null;
	}

	@Override
	public Application update(Application application) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = applicationAPI.update(application);
		if (response.isDone()) return (Application) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Application> showByUser(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = applicationAPI.showByUser(filter);
		if (response.isDone()) return (Collection<Application>) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Application> showByConsumer(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = applicationAPI.showByConsumer(filter);
		if (response.isDone()) return (Collection<Application>) response.getMessage();
		else return null;
	}

}
