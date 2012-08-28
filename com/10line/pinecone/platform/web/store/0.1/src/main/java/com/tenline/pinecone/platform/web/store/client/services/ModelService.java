/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.services;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.Entity;

/**
 * @author Bill
 *
 */
@RemoteServiceRelativePath("model/service")
public interface ModelService extends RemoteService {
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Entity create(Entity entity) throws Exception;
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public boolean delete(Entity entity) throws Exception;
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Entity update(Entity entity) throws Exception;
	
	/**
	 * 
	 * @param entityClass
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Entity> show(String entityClass, String filter) throws Exception;

}
