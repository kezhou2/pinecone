package com.tenline.pinecone.platform.web.store.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.model.AppInfo;
import com.tenline.pinecone.platform.web.store.client.model.ConsumerInfo;
import com.tenline.pinecone.platform.web.store.client.model.DeviceInfo;
import com.tenline.pinecone.platform.web.store.client.model.UserInfo;

@RemoteServiceRelativePath("PineconeService")
public interface PineconeService extends RemoteService {

	/**
	 * 用户登录
	 * @param name
	 * @param pwd
	 * @return
	 */
	public User login(String name, String pwd);
	/**
	 * 注册用户
	 * @param userInfo
	 * @return
	 */
	public User register(User user);
	/**
	 * 判断用户是否存在
	 * @param user
	 * @return
	 */
	public boolean isUserExist(User user);
	
	/**
	 * 用户注销
	 * @param id
	 */
	public void logout(String id);
	/**
	 * 获取指定用户的好友信息
	 * @param userInfo
	 * @return
	 */
	public List<UserInfo> getUserRelation(UserInfo userInfo);
	
	/**
	 * 获取指定用户安装的应用
	 * @param userInfo
	 * @return
	 */
	public List<ConsumerInfo> getConsumerInfo(UserInfo userInfo);
	/**
	 * 获取指定用户安装的应用
	 * @param userInfo
	 * @return
	 */
	public List<AppInfo> getAppInfo(UserInfo userInfo);
	
	/**
	 * 获取平台可供用户选择的所有consumer
	 * @return
	 */
	public List<ConsumerInfo> getAllConsumerInfo();
	/**
	 * 给指定用户安装一个consumer
	 * @param userInfo
	 * @param consumerInfo
	 */
	public void addConsumerToUser(UserInfo userInfo, ConsumerInfo consumerInfo);
	/**
	 * 卸载指定用户的一个指定应用
	 * @param appInfo
	 */
	public void deleteAppInfo(AppInfo appInfo);
	/**
	 * 根据id获取Consumer信息
	 * @param consumerId
	 * @return
	 */
	public ConsumerInfo getConsumerById(String consumerId);
	
	/**
	 * 获取指定用户的Device信息
	 */
	public List<DeviceInfo> getAllDevice(UserInfo userInfo);
}
