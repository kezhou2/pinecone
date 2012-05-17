/**
 * 
 */
package com.tenline.game.simulation.moneytree.client.services;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.Transaction;

/**
 * @author Bill
 *
 */
@RemoteServiceRelativePath("transaction/service")
public interface TransactionService extends AbstractService {

	/**
	 * 
	 * @param transaction
	 * @return
	 * @throws Exception
	 */
	public Transaction create(Transaction transaction) throws Exception;
	
	/**
	 * 
	 * @param transaction
	 * @return
	 * @throws Exception
	 */
	public Transaction update(Transaction transaction) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Transaction> show(String filter) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Transaction> showByApplication(String filter) throws Exception;
	
}
