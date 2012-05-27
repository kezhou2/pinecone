/**
 * 
 */
package com.tenline.game.simulation.moneytree.client.services;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.Consumer;

/**
 * @author Bill
 *
 */
@RemoteServiceRelativePath("consumer/service")
public interface ConsumerService extends AbstractService {

	/**
	 * 
	 * @param consumer
	 * @return
	 * @throws Exception
	 */
	public Consumer create(Consumer consumer) throws Exception;
	
	/**
	 * 
	 * @param consumer
	 * @return
	 * @throws Exception
	 */
	public Consumer update(Consumer consumer) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Consumer> show(String filter) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Consumer> showByCategory(String filter) throws Exception;
	
}
