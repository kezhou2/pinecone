/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;


/**
 * 测试数据
 * 
 * @author liugy
 * 
 */
public class Database {

//	private static List<Consumer> allConsumerList = null;
//	private static Map<String, List<Application>> userAppMap = null;
//	private static Map<String, List<UserRelation>> userRelatMap = null;
//	private static List<User> userList = null;
//	
//	static{
//		prepareData();
//	}
//
//	public static List<Consumer> getAllConsumerList() {
//		return allConsumerList;
//	}
//
//	public static List<UserRelation> getUserRelatList(User user) {
//		if(userRelatMap.get(user.getId()) == null){
//			userRelatMap.put(user.getId(), new ArrayList<UserRelation>());
//		}
//		return userRelatMap.get(user.getId());
//	}
//
//	public static List<User> getUserList() {
//		return userList;
//	}
//
//	public static List<Application> getUserApplications(User user) {
//		if (userAppMap == null) {
//			userAppMap = new LinkedHashMap<String, List<Application>>();
//		}
//
//		if (userAppMap.get(user.getId()) == null) {
//			userAppMap.put(user.getId(), new ArrayList<Application>());
//		}
//		return userAppMap.get(user.getId());
//	}
//
//	public static void prepareData() {
//		//prepare user info
//		userList = new ArrayList<User>();
//
//		{
//			User user = new User();
//			user.setId("1242EDF");
//			user.setName("bill");
//			user.setAvatarUrl("http://avatar/1");
//			user.setEmail("billmse@gmail.com");
//			user.setPassword("19821027");
//			user.setType("individual");
//
//			userList.add(user);
//		}
//
//		{
//			User user = new User();
//			user.setId("1242E3F");
//			user.setName("liugy");
//			user.setAvatarUrl("http://avatar/1");
//			user.setEmail("liugy@gmail.com");
//			user.setPassword("198297");
//			user.setType("individual");
//
//			userList.add(user);
//		}
//
//		{
//			User user = new User();
//			user.setId("1242E32");
//			user.setName("1");
//			user.setAvatarUrl("http://avatar/1");
//			user.setEmail("l1@gmail.com");
//			user.setPassword("1");
//			user.setType("individual");
//
//			userList.add(user);
//		}
//
//		{
//			User user = new User();
//			user.setId("1252E3F");
//			user.setName("wangyq");
//			user.setAvatarUrl("http://avatar/1");
//			user.setEmail("WangYQ@gmail.com");
//			user.setPassword("19840000");
//			user.setType("individual");
//
//			userList.add(user);
//		}
//		
//		//User Relation
//		userRelatMap = new LinkedHashMap<String,List<UserRelation>>();
//		for(User user:userList){
//			userRelatMap.put(user.getId(), new ArrayList<UserRelation>());
//			for(User u:userList){
//				if(!u.getId().equals(user.getId())){
//					UserRelation ur = new UserRelation();
//					ur.setId(user.getId());
//					ur.setType("Friend");
//					ur.setUserId(u.getId());
//					ur.setOwner(user);
//					userRelatMap.get(user.getId()).add(ur);
//				}
//			}
//		}
//		
//		//
//		userRelatMap = new LinkedHashMap<String,List<UserRelation>>();
//		
//		//Consumer
//		allConsumerList = new ArrayList<Consumer>();
//		{
//			Consumer con = new Consumer();
//			con.setConnectURI("http://www.sina.cn");
//			con.setDisplayName("Sina");
//			con.setId("DEA");
//			con.setKey("EEEE");
//			con.setSecret("ABCD");
//			allConsumerList.add(con);
//		}
//		{
//			Consumer con = new Consumer();
//			con.setConnectURI("http://www.163.com");
//			con.setDisplayName("163");
//			con.setId("DEF");
//			con.setKey("FFFF");
//			con.setSecret("DBCA");
//			allConsumerList.add(con);
//		}
//		
//	}

}
