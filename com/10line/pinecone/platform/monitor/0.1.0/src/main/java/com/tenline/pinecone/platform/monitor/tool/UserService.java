package com.tenline.pinecone.platform.monitor.tool;

import java.util.ArrayList;

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.monitor.IConstants;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.UserAPI;

public class UserService {
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private UserAPI addApi;
	private UserAPI queryApi;
	private static UserService instance = null;

	private UserService() {
		addApi = new UserAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT,
				new APIListener() {
					@Override
					public void onMessage(Object message) {
						User user = (User) message;
						System.out.println("userapi add ok: " + user.getId());
						setUser(user);
					}

					@Override
					public void onError(String error) {
						System.out.println("getuser error: " + error);
						setUser(null);
					}
				});
		queryApi = new UserAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT,
				new APIListener() {
					@SuppressWarnings("unchecked")
					@Override
					public void onMessage(Object message) {
						ArrayList<User> userList = (ArrayList<User>) message;
						if (userList.size() != 0) {
							System.out.println("userapi get: "
									+ userList.get(0).getId());
							setUser(userList.get(0));
						} else {
							setUser(null);
							System.out.println("userapi get: 0");
						}
					}

					@Override
					public void onError(String error) {
						System.out.println("getuser error: " + error);
					}
				});
	}

	/**
	 * @return
	 */
	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	@SuppressWarnings("finally")
	public User saveUser(User user) {
		try {
			this.addApi.create(user);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return this.user;
		}
	}

	public User getUserBySnsId(String snsId) {
		try {
			this.queryApi.show("snsId=='" + snsId + "'");
			return getUser();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
