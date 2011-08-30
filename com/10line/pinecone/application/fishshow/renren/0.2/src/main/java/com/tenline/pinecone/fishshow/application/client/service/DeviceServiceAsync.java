package com.tenline.pinecone.fishshow.application.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;

public interface DeviceServiceAsync {

	void getDevice(String userId, AsyncCallback<Device[]> callback);

	void getItem(String variableId, AsyncCallback<Item[]> callback);

	void getUser(String snsId, AsyncCallback<User> callback);

	void getVariable(String deviceId, AsyncCallback<Variable[]> callback);

	void getStatus(String type,String deviceId, AsyncCallback<Device> callback);

	void setStatus(String deviceId, Device value, AsyncCallback<Void> callback);

}
