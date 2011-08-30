package com.tenline.pinecone.fishshow.application.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.fishshow.application.shared.renren.Friend;

public interface RenrenApiAsync {

	void login(String sessionKey, AsyncCallback<Void> callback);

	void areFriends(String sessionKey, String users1, String users2,
			AsyncCallback<String> callback);

	void getAppFriends(String sessionKey, String fields,
			AsyncCallback<String> callback);

	void getFriendIds(String sessionKey, int page, int count,
			AsyncCallback<List<Integer>> callback);

	void getFriends(String sessionKey, int page, int count,
			AsyncCallback<String> callback);

	void hasAppPermission(String sessionKey, String extPerm, int userId,
			AsyncCallback<Boolean> callback);

	void getInfo(String sessionKey, String userIds, String fields,
			AsyncCallback<String> callback);

	void getLoggedInUser(String sessionKey, AsyncCallback<Integer> callback);

	void isAppUser(String sessionKey, String userId,
			AsyncCallback<Boolean> callback);

	void createLink(String sessionKey, int domain, AsyncCallback<String> callback);

	void send(String sessionKey,String toIds, String notification, AsyncCallback<Boolean> callback);

	void getAllFriends(String sessionKey, AsyncCallback<List<Friend>> callback);

}
