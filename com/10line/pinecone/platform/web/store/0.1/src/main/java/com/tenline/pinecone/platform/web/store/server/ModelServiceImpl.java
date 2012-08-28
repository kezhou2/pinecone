/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import java.util.Collection;

import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.sdk.ModelAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.store.client.services.ModelService;

/**
 * @author Bill
 *
 */
public class ModelServiceImpl extends AbstractServiceImpl implements ModelService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 108442490030149812L;
	
	/**
	 * 
	 */
	private ModelAPI modelAPI;

	/**
	 * 
	 */
	public ModelServiceImpl() {
		// TODO Auto-generated constructor stub
		modelAPI = new ModelAPI(HOST, PORT, CONTEXT);
	}

	@Override
	public boolean delete(Entity entity) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = modelAPI.delete(entity);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	public Entity create(Entity entity) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = modelAPI.create(entity);
		if (response.isDone()) return (Entity) response.getMessage();
		else return null;
	}

	@Override
	public Entity update(Entity entity) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = modelAPI.update(entity);
		if (response.isDone()) return (Entity) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Entity> show(String entityClass, String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = modelAPI.show(Class.forName(entityClass), filter);
		if (response.isDone()) return (Collection<Entity>) response.getMessage();
		else return null;
	}

}
