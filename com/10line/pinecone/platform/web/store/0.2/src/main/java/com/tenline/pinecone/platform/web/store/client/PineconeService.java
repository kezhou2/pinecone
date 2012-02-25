package com.tenline.pinecone.platform.web.store.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.shared.ApplicationInfo;
import com.tenline.pinecone.platform.web.store.shared.CategoryInfo;
import com.tenline.pinecone.platform.web.store.shared.ConsumerInfo;
import com.tenline.pinecone.platform.web.store.shared.DeviceInfo;
import com.tenline.pinecone.platform.web.store.shared.FriendInfo;
import com.tenline.pinecone.platform.web.store.shared.MailInfo;
import com.tenline.pinecone.platform.web.store.shared.UserInfo;

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
	public UserInfo register(UserInfo userInfo);
	/**
	 * 判断用户是否存在
	 * @param userInfo
	 * @return
	 */
	public boolean isUserExist(UserInfo userInfo);
	
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
	public List<FriendInfo> getFriends(UserInfo userInfo);
	
	/**
	 * 获取指定用户安装的应用
	 * @param userInfo
	 * @return
	 */
	public List<ApplicationInfo> getAppInfo(UserInfo userInfo);
	
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
	public void addApplication(UserInfo userInfo, ConsumerInfo consumerInfo);
	/**
	 * 卸载指定用户的一个指定应用
	 * @param appInfo
	 */
	public void deleteAppInfo(UserInfo userInfo, ApplicationInfo appInfo);
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
	/**
	 * 获取所有的用户信息
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<UserInfo> getUsersByPage(int start, int pageSize);
	/**
	 * 获取所有的用户信息，除了当前登录用户
	 * @param userInfo
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<UserInfo> getUsersByPage1(UserInfo userInfo, int start, int pageSize);
	
	/**
	 * 为用户添加好友
	 * @param sender
	 * @param receiver
	 * @param type
	 * @return
	 */
	public FriendInfo addFriend(UserInfo sender, UserInfo receiver, String type);
	/**
	 * 删除用户的好友
	 * @param userInfo
	 * @param friendInfo
	 * @return
	 */
	public boolean deleteFriend(UserInfo userInfo, FriendInfo friendInfo);

	/**
	 * 获取所有的种类信息
	 * @return
	 */
	public List<CategoryInfo> getCategorys();
	/**
	 * 根据类型信息获取应用
	 * @param category
	 * @return
	 */
	public List<ConsumerInfo> getConsumerByCategory(CategoryInfo category);
	/**
	 * 修改用户应用的状态
	 * @param appInfo
	 * @param status
	 */
	public void changeAppStatus(ApplicationInfo appInfo, String status);
	/**
	 * 发送邮件
	 * @param sender
	 * @param receiver
	 */
	public boolean sendMail(UserInfo sender, UserInfo receiver, MailInfo mailInfo);
	/**
	 * 获取用户接收的邮件
	 * @param userInfo
	 * @return
	 */
	public List<MailInfo> getReceiveMails(UserInfo userInfo);
	/**
	 * 获取用户发送的邮件
	 * @param userInfo
	 * @return
	 */
	public List<MailInfo> getSendMails(UserInfo userInfo);
	/**
	 * 获取用户没有读取过的邮件
	 * @param userInfo
	 * @return
	 */
	public List<MailInfo> getUnreadMails(UserInfo userInfo);
	/**
	 * 设置邮件是否已经被用户读过
	 * @param mailInfo
	 * @param isRead
	 */
	public void setMailRead(MailInfo mailInfo,boolean isRead);
	/**
	 * 获取用户收到的好友请求
	 * @param userInfo
	 * @return
	 */
	public List<FriendInfo> getUnDecideFriends(UserInfo userInfo);
	/**
	 * 确认用户信息
	 * @param friendInfo
	 */
	public void acceptFriendInvite(FriendInfo friendInfo);
}
