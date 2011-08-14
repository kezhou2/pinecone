package com.tenline.pinecone.fishshow.application.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeDevice;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeItem;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeUser;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeVariable;
import com.tenline.pinecone.fishshow.application.shared.pinecone.UserDeviceInfo;

public interface DeviceServiceAsync {

	void getDevice(String userId, AsyncCallback<PineconeDevice[]> callback);

	void getItem(String variableId, AsyncCallback<PineconeItem[]> callback);

	void getUser(String snsId, AsyncCallback<PineconeUser> callback);

	void getVariable(String deviceId, AsyncCallback<PineconeVariable[]> callback);

	void getStatus(String type,String varId, AsyncCallback<String> callback);

	void setStatus(String varId, String value, AsyncCallback<Void> callback);

	void getUserDeviceInfo(String id, AsyncCallback<UserDeviceInfo> callback);

}
