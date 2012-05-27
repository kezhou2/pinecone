/**
 * 
 */
package com.tenline.game.simulation.moneytree.server;

import java.util.Collection;

import com.tenline.game.simulation.moneytree.client.services.ExchangeService;
import com.tenline.pinecone.platform.model.Exchange;
import com.tenline.pinecone.platform.sdk.ExchangeAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class ExchangeServiceImpl extends AbstractService implements ExchangeService {
	
	private ExchangeAPI exchangeAPI;

	/**
	 * 
	 */
	public ExchangeServiceImpl() {
		// TODO Auto-generated constructor stub
		exchangeAPI = new ExchangeAPI(HOST, PORT, CONTEXT);
	}

	@Override
	public boolean delete(String id) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = exchangeAPI.delete(id);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	public Exchange create(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = exchangeAPI.create(exchange);
		if (response.isDone()) return (Exchange) response.getMessage();
		else return null;
	}

	@Override
	public Exchange update(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = exchangeAPI.update(exchange);
		if (response.isDone()) return (Exchange) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Exchange> show(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = exchangeAPI.show(filter);
		if (response.isDone()) return (Collection<Exchange>) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Exchange> showByAccount(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = exchangeAPI.showByAccount(filter);
		if (response.isDone()) return (Collection<Exchange>) response.getMessage();
		else return null;
	}

}
