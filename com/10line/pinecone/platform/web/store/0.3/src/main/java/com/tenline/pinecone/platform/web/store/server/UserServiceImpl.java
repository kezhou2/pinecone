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
@SuppressWarnings("serial")
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
	public boolean delete(String id) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = userAPI.delete(id);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	public User create(User user) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = userAPI.create(user);
		if (response.isDone()) return (User) response.getMessage();
		else return null;
	}

	@Override
	public User update(User user) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = userAPI.update(user);
		if (response.isDone()) return (User) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<User> show(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = userAPI.show(filter);
		if (response.isDone()) return (Collection<User>) response.getMessage();
		else return null;
	}

	
//	public static void main(String[] args){
//		UserServiceImpl imp = new UserServiceImpl();
//		try {
//			Collection<User> users = imp.show("all");
//			for(User u: users){
//				System.out.println("****************************");
//				System.out.println("id="+u.getId());
//				System.out.println("name="+u.getName());
//				System.out.println("password="+u.getPassword());
//				System.out.println("email="+u.getEmail());
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
