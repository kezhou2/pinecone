package com.tenline.pinecone.fishshow.application.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.fishshow.application.shared.renren.Friend;

@RemoteServiceRelativePath("renrenapi")
public interface RenrenApi extends RemoteService {
	
	public void login(String sessionKey);
	/**
     * 判断两个List中对应的用户是否是好友。
     * 
     * @param users1 逗号分隔的用户ID
     * @param users2 逗号分隔的用户ID
     * @return
     */
    public String areFriends(String sessionKey,String users1, String users2);

    /**
     * 得到当前登录用户的好友ID列表。
     * 
     * @param page
     * @param count
     * @return
     */
    public List<Integer> getFriendIds(String sessionKey,int page, int count);

    /**
     * 得到当前登录用户的好友列表。
     * 
     * @param page
     * @param count
     * @return
     */
    public String getFriends(String sessionKey,int page, int count);
    
    /**
     * 得到当前登录用户的所有的好友列表。
     * 
     * @return
     */
    public List<Friend> getAllFriends(String sessionKey);

    /**
     * 查询当前用户安装某个应用的好友ID列表。
     * 
     * @param fields 返回的好友包含的属性，用逗号分隔(如：name,tinyurl,headurl)。
     * @return
     */
    public String getAppFriends(String sessionKey,String fields);
    
    /**
     * 获取一批用户的信息
     * 
     * @param userIds 逗号分隔的用户ID
     * @param fields 逗号分隔的用户属性，如：name,email_hash,
     *        sex,star,birthday,tinyurl,
     *        headurl,mainurl,hometown_location,hs_history
     *        ,university_history,work_history,contact_info
     * @return
     */
    public String getInfo(String sessionKey,String userIds, String fields);

    /**
     * 检查用户是否授予应用扩展权限。
     * 
     * @param extPerm 用户可操作的扩展授权，例如email。
     * @param userId 未登录时检测该值。
     * @return
     */
    public boolean hasAppPermission(String sessionKey,String extPerm, int userId);

    /**
     * 获取当前登录用户的ID
     * 
     * @return
     */
    public int getLoggedInUser(String sessionKey);

    /**
     * 判断用户是否是app的用户。
     * 
     * @param userId 未登录时判断该用户。
     * @return
     */
    public boolean isAppUser(String sessionKey,String userId);
    
    /**
     * 生成用户站外邀请用户注册的链接地址,应用可以引导用户通过QQ或者msn等渠道邀请好友加入应用。
     * 
     * @param domain
     * @return
     */
    public String createLink(String sessionKey,int domain);
    /**
     * 给当前登录者的好友或也安装了同样应用的用户发通知。
     * 
     * @param toIds 这些用户必须是当前登录者的好友或也安装了此应用；多个可以是逗号分隔，最多20个。
     * @param notification 通知的内容，支持XNML<xn:name/>和<a/>。
     * @return
     */
    public boolean send(String sessionKey,String toIds, String notification);
}
