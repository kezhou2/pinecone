package com.tenline.game.simulation.moneytree.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.game.simulation.moneytree.shared.ExUser;
import com.tenline.pinecone.platform.model.Application;

public interface DataServiceAsync {

	void getPlantDate(Application app, AsyncCallback<Date> callback);

	void plantGoldTree(Application app, AsyncCallback<Boolean> callback);

	void harvest(Application app, AsyncCallback<Integer> callback);

	void getGoldTreeStatus(Application app, AsyncCallback<String> callback);

	void getUser(String userId, AsyncCallback<ExUser> callback);

	void initUser(String userId, AsyncCallback<Application> callback);

	void buy(String key,AsyncCallback<Void> callback);

	void submitRenrenOrder(String renrenSessionKey, String token,
			int renrenDouNum, long orderId, AsyncCallback<Boolean> callback);

	void getRenrenOrderToken(String renrenSessionKey, String renrenUserId,
			int renrenDouNum, long orderId, AsyncCallback<String> callback);
}
