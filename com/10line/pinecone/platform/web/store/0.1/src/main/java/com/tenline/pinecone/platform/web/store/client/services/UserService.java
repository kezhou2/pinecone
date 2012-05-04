/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.User;

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
	public User login(String email, String password) throws Exception;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User register(User user) throws Exception;

}
