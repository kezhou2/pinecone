/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.services;

import com.google.gwt.user.client.rpc.RemoteService;

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
	public boolean delete(String id) throws Exception;
	
}
