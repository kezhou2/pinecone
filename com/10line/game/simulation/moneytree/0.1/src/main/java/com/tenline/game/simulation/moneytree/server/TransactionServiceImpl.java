/**
 * 
 */
package com.tenline.game.simulation.moneytree.server;

import java.util.Collection;

import com.tenline.game.simulation.moneytree.client.services.TransactionService;
import com.tenline.pinecone.platform.model.Transaction;
import com.tenline.pinecone.platform.sdk.TransactionAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class TransactionServiceImpl extends AbstractService implements TransactionService {
	
	private TransactionAPI transactionAPI;

	/**
	 * 
	 */
	public TransactionServiceImpl() {
		// TODO Auto-generated constructor stub
		transactionAPI = new TransactionAPI(HOST, PORT, CONTEXT);
	}

	@Override
	public boolean delete(String id) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = transactionAPI.delete(id);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	public Transaction create(Transaction transaction) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = transactionAPI.create(transaction);
		if (response.isDone()) return (Transaction) response.getMessage();
		else return null;
	}

	@Override
	public Transaction update(Transaction transaction) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = transactionAPI.update(transaction);
		if (response.isDone()) return (Transaction) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Transaction> show(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = transactionAPI.show(filter);
		if (response.isDone()) return (Collection<Transaction>) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Transaction> showByApplication(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = transactionAPI.showByApplication(filter);
		if (response.isDone()) return (Collection<Transaction>) response.getMessage();
		else return null;
	}

}
