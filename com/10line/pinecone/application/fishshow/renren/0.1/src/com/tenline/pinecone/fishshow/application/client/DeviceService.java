package com.tenline.pinecone.fishshow.application.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeDevice;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeItem;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeUser;
import com.tenline.pinecone.fishshow.application.shared.pinecone.PineconeVariable;
import com.tenline.pinecone.fishshow.application.shared.pinecone.UserDeviceInfo;

@RemoteServiceRelativePath("deviceService")
public interface DeviceService extends RemoteService {
	
	PineconeUser getUser(String snsId);
	PineconeDevice[] getDevice(String userId);
	PineconeVariable[] getVariable(String deviceId);
	PineconeItem[] getItem(String variableId);
	String getStatus(String type,String varId);
	void setStatus(String varId, String value);
	UserDeviceInfo getUserDeviceInfo(String id);
}
