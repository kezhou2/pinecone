/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.tenline.pinecone.platform.model.Entity;

/**
 * @author Bill
 *
 */
public interface AbstractService extends RemoteService {
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean delete(Entity entity) throws Exception;
	
}
