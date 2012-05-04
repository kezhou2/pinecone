/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import java.util.Collection;

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.store.client.services.UserService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({ "serial", "unchecked" })
public class UserServiceImpl extends AbstractService implements UserService {
	
	private UserAPI userAPI;

	/**
	 * 
	 */
	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
		userAPI = new UserAPI(HOST, PORT, CONTEXT);
	}

	@Override
	public User login(String email, String password) throws Exception {
		// TODO Auto-generated method stub
		String filter = "email=='"+email+"'&&password=='"+password+"'";
		APIResponse response = userAPI.show(filter);
		if (response.isDone()) {
			Collection<User> users = (Collection<User>) response.getMessage();
			if (users.size() == 1) {
				return (User) users.toArray()[0];
			}
		}
		return null;
	}

	@Override
	public User register(User user) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = userAPI.create(user);
		if (response.isDone()) {
			return (User) response.getMessage();	
		} else {
			return null;
		}
	}

}
