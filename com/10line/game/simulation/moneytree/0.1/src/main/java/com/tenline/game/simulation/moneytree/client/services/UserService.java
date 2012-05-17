/**
 * 
 */
package com.tenline.game.simulation.moneytree.client.services;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.User;

/**
 * @author Bill
 *
 */
@RemoteServiceRelativePath("user/service")
public interface UserService extends AbstractService {
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User create(User user) throws Exception;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User update(User user) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<User> show(String filter) throws Exception;

}
