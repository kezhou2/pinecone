package com.tenline.game.simulation.moneytree.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.game.simulation.moneytree.shared.ExUser;
import com.tenline.pinecone.platform.model.Application;
/**
 * 该对象中的所有UserId均指人人网用户的UserId
 * @author liugy
 *
 */
@RemoteServiceRelativePath("dataservice")
public interface DataService extends RemoteService {
	/**
	 * 获取种子播种时间
	 * @param app
	 * @return
	 */
	public Date getPlantDate(Application app);
	/**
	 * 种植摇钱树
	 * @param app
	 * @return
	 */
	public boolean plantGoldTree(Application app);
	/**
	 * 获取摇钱树收获
	 * @param app
	 * @return
	 */
	public int harvest(Application app);

	/**
	 * 获取摇钱树状态
	 * @param app
	 * @return
	 */
	public String getGoldTreeStatus(Application app);
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public ExUser getUser(String userId);
	/**
	 * 利用人人豆购买种子
	 * @param renrenSessionKey
	 * @param renrenUserId
	 * @param renrenDouNum
	 * @return
	 */
	public boolean submitRenrenOrder(String renrenSessionKey, String token, int renrenDouNum, long orderId);
	/**
	 * 调用人人网API，生成订单，并获得订单Token
	 * @param renrenSessionKey
	 * @param renrenUserId
	 * @param renrenDouNum
	 * @return
	 */
	public String getRenrenOrderToken(String renrenSessionKey, String renrenUserId, int renrenDouNum, long orderId);
	/**
	 * 创建Pinecone平台的User,以及用户的application，如果该用户在Pinecone平台上没有用户的话
	 * @param userId
	 */
	public Application initUser(String userId);
	
	public void buy(String key);
}
