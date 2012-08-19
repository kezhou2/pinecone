/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import java.util.Collection;

import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.store.client.services.UserService;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class UserServiceImpl extends AbstractService implements UserService {
	/**
	 * 
	 */
	public UserServiceImpl() {
		super();
	}

	@Override
	public boolean delete(Entity entity) throws Exception {
		APIResponse response = modelAPI.delete(entity);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	public User create(User user) throws Exception {
		APIResponse response = modelAPI.create(user);
		if (response.isDone()) return (User) response.getMessage();
		else return null;
	}

	@Override
	public User update(User user) throws Exception {
		APIResponse response = modelAPI.update(user);
		if (response.isDone()) return (User) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<User> show(String filter) throws Exception {
		APIResponse response = modelAPI.show(User.class,filter);
		if (response.isDone()) return (Collection<User>) response.getMessage();
		else return null;
	}

}
