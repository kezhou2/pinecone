/**
 * 
 */
package com.tenline.game.simulation.moneytree.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.User;

/**
 * @author Bill
 *
 */
@RemoteServiceRelativePath("Service")
public interface MoneyTreeService extends RemoteService {

	/**
	 * 
	 * @param user
	 * @throws Exception
	 */
	void plantTree(User user) throws Exception;
	
}
