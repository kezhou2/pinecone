/**
 * 
 */
package com.tenline.game.simulation.moneytree.client.services;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.Exchange;

/**
 * @author Bill
 *
 */
@RemoteServiceRelativePath("exchange/service")
public interface ExchangeService extends AbstractService {

	/**
	 * 
	 * @param exchange
	 * @return
	 * @throws Exception
	 */
	public Exchange create(Exchange exchange) throws Exception;
	
	/**
	 * 
	 * @param exchange
	 * @return
	 * @throws Exception
	 */
	public Exchange update(Exchange exchange) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Exchange> show(String filter) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Exchange> showByAccount(String filter) throws Exception;
	
}
