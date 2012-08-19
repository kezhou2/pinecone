/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import java.util.Collection;

import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class ConsumerServiceImpl extends AbstractService implements ConsumerService {
	
	/**
	 * 
	 */
	public ConsumerServiceImpl() {
		super();
	}

	@Override
	public boolean delete(Entity entity) throws Exception {
		APIResponse response = modelAPI.delete(entity);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Consumer> show(String filter) throws Exception {
		APIResponse response = modelAPI.show(Consumer.class,filter);
		if (response.isDone()) return (Collection<Consumer>) response.getMessage();
		else return null;
	}
	
//	@Override
//	@SuppressWarnings("unchecked")
//	public Collection<Consumer> showByCategory(String filter) throws Exception {
//		APIResponse response = consumerAPI.showByCategory(filter);
//		if (response.isDone()) return (Collection<Consumer>) response.getMessage();
//		else return null;
//	}

	@Override
	public Consumer create(Consumer consumer) throws Exception {
		APIResponse response = modelAPI.create(consumer);
		if (response.isDone()) return (Consumer) response.getMessage();
		else return null;
	}

	@Override
	public Consumer update(Consumer consumer) throws Exception {
		APIResponse response = modelAPI.update(consumer);
		if (response.isDone()) return (Consumer) response.getMessage();
		else return null;
	}

}
