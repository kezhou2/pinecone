package com.tenline.pinecone.fishshow.application.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;

@RemoteServiceRelativePath("deviceService")
public interface DeviceService extends RemoteService {
	
	User getUser(String snsId);
	Device[] getDevice(String userId);
	Variable[] getVariable(String deviceId);
	Item[] getItem(String variableId);
	Device getStatus(String type,String deviceId);
	void setStatus(String deviceId, Device value);
}
