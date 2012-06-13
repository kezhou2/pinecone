/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import java.util.Collection;

import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class ConsumerServiceImpl extends AbstractService implements ConsumerService {

	private ConsumerAPI consumerAPI;
	
	/**
	 * 
	 */
	public ConsumerServiceImpl() {
		// TODO Auto-generated constructor stub
		consumerAPI = new ConsumerAPI(HOST, PORT, CONTEXT);
	}

	@Override
	public boolean delete(String id) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = consumerAPI.delete(id);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Consumer> show(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = consumerAPI.show(filter);
		if (response.isDone()) return (Collection<Consumer>) response.getMessage();
		else return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<Consumer> showByCategory(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = consumerAPI.showByCategory(filter);
		if (response.isDone()) return (Collection<Consumer>) response.getMessage();
		else return null;
	}

	@Override
	public Consumer create(Consumer consumer) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = consumerAPI.create(consumer);
		if (response.isDone()) return (Consumer) response.getMessage();
		else return null;
	}

	@Override
	public Consumer update(Consumer consumer) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = consumerAPI.update(consumer);
		if (response.isDone()) return (Consumer) response.getMessage();
		else return null;
	}

}
