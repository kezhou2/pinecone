package com.tenline.pinecone.platform.osgi.monitor.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.UserAPI;

public class UserService {
	private static Logger logger = Logger.getLogger(UserService.class);
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private UserAPI api;
	private static UserService instance = null;

	private UserService() {
		api = new UserAPI("pinecone.web.service.10line.cc", "80",
				new APIListener() {
					@SuppressWarnings("unchecked")
					@Override
					public void onMessage(Object message) {
						ArrayList<User> userList = (ArrayList<User>) message;
						if (userList.size() != 0) {
							logger.info("userapi get: "
									+ userList.get(0).getId());
							setUser(userList.get(0));
						}else{
							setUser(null);
							logger.info("userapi get: 0");
						}
					}

					@Override
					public void onError(String error) {
						logger.error("getuser error: " + error);
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

	public User getUserBySnsId(String snsId) {
		try {
			this.api.show("snsId=='" + snsId + "'");
			return getUser();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
