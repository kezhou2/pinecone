/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Bill
 *
 */
@RemoteServiceRelativePath("user/service")
public interface UserService extends RemoteService {
	
	/**
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean login(String email, String password) throws Exception;

}
