/**
 * 
 */
package com.tenline.game.simulation.moneytree.client.services;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.Application;

/**
 * @author Bill
 *
 */
@RemoteServiceRelativePath("application/service")
public interface ApplicationService extends AbstractService {
	
	/**
	 * 
	 * @param application
	 * @return
	 * @throws Exception
	 */
	public Application create(Application application) throws Exception;
	
	/**
	 * 
	 * @param application
	 * @return
	 * @throws Exception
	 */
	public Application update(Application application) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Application> show(String filter) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Application> showByUser(String filter) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Application> showByConsumer(String filter) throws Exception;

}
