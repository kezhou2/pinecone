/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import java.util.Collection;

import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.store.client.services.ApplicationService;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class ApplicationServiceImpl extends AbstractService implements ApplicationService {

	
	/**
	 * 
	 */
	public ApplicationServiceImpl() {
		super();
	}


	public boolean delete(Entity entity) throws Exception {
		APIResponse response = modelAPI.delete(entity);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Application> show(String filter) throws Exception {
		APIResponse response = modelAPI.show(Application.class,filter);
		if (response.isDone()) return (Collection<Application>) response.getMessage();
		else return null;
	}

	@Override
	public Application create(Application application) throws Exception {
		APIResponse response = modelAPI.create(application);
		if (response.isDone()) return (Application) response.getMessage();
		else return null;
	}

	@Override
	public Application update(Application application) throws Exception {
		APIResponse response = modelAPI.update(application);
		if (response.isDone()) return (Application) response.getMessage();
		else return null;
	}

//	@Override
//	@SuppressWarnings("unchecked")
//	public Collection<Application> showByUser(String filter) throws Exception {
//		APIResponse response = modelAPI.showByUser("");
//		if (response.isDone()) return (Collection<Application>) response.getMessage();
//		else return null;
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public Collection<Application> showByConsumer(String filter) throws Exception {
//		APIResponse response = applicationAPI.showByConsumer(filter);
//		if (response.isDone()) return (Collection<Application>) response.getMessage();
//		else return null;
//	}

}
