/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import java.util.ArrayList;
import java.util.List;

import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Dependency;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.model.User;


/**
 * 测试数据
 * 
 * @author liugy
 * 
 */
public class Database {

	private static List<Application> applicationList = null;
	private static List<Consumer> consumerList = null;
	private static List<Category> categoryList = null;
	private static List<Dependency> dependencyList = null;
	private static List<Device> deviceList = null;
	private static List<Friend> friendList = null;
	private static List<Mail> mailList = null;
	private static List<User> userList = null;
	
	static{
		//定义用户
		userList = new ArrayList<User>();

		User user1 = new User();
		user1.setId("B2234EB12AEas");
		user1.setEmail("liugy503@gmail.com");
		user1.setName("liugy");
		user1.setPassword("198297");
		user1.setAvatar(new byte[100]);
		user1.setSentFriends(new ArrayList<Friend>());
		user1.setSentMails(new ArrayList<Mail>());
		user1.setReceivedFriends(new ArrayList<Friend>());
		user1.setReceivedMails(new ArrayList<Mail>());
		user1.setDevices(new ArrayList<Device>());
		user1.setApplications(new ArrayList<Application>());
		userList.add(user1);
	
		User user2 = new User();
		user2.setId("B2F84EB12AEwq");
		user2.setEmail("wangyq@gmail.com");
		user2.setName("wangyq");
		user2.setPassword("wangyq");
		user2.setAvatar(new byte[100]);
		user2.setSentFriends(new ArrayList<Friend>());
		user2.setSentMails(new ArrayList<Mail>());
		user2.setReceivedFriends(new ArrayList<Friend>());
		user2.setReceivedMails(new ArrayList<Mail>());
		user2.setDevices(new ArrayList<Device>());
		user2.setApplications(new ArrayList<Application>());
		userList.add(user2);
		
		User user3 = new User();
		user3.setId("B9F04EB14AF");
		user3.setEmail("xuel@gmail.com");
		user3.setName("xuel");
		user3.setPassword("xuel");
		user3.setAvatar(new byte[100]);
		user3.setSentFriends(new ArrayList<Friend>());
		user3.setSentMails(new ArrayList<Mail>());
		user3.setReceivedFriends(new ArrayList<Friend>());
		user3.setReceivedMails(new ArrayList<Mail>());
		user3.setDevices(new ArrayList<Device>());
		user3.setApplications(new ArrayList<Application>());
		userList.add(user3);
	
		User user4 = new User();
		user4.setId("BBB90EB12AE3");
		user4.setEmail("1@gmail.com");
		user4.setName("1");
		user4.setPassword("1");
		user4.setAvatar(new byte[100]);
		user4.setSentFriends(new ArrayList<Friend>());
		user4.setSentMails(new ArrayList<Mail>());
		user4.setReceivedFriends(new ArrayList<Friend>());
		user4.setReceivedMails(new ArrayList<Mail>());
		user4.setDevices(new ArrayList<Device>());
		user4.setApplications(new ArrayList<Application>());
		userList.add(user4);
		
		User user5 = new User();
		user5.setId("BBB90EB1212AE2");
		user5.setEmail("2@gmail.com");
		user5.setName("2");
		user5.setPassword("2");
		user5.setAvatar(new byte[100]);
		user5.setSentFriends(new ArrayList<Friend>());
		user5.setSentMails(new ArrayList<Mail>());
		user5.setReceivedFriends(new ArrayList<Friend>());
		user5.setReceivedMails(new ArrayList<Mail>());
		user5.setDevices(new ArrayList<Device>());
		user5.setApplications(new ArrayList<Application>());
		userList.add(user5);
			
		//定义邮件
		mailList = new ArrayList<Mail>();
		Mail m = new Mail();
		m.setId("ADREBRHT@!%$@");
		m.setRead(false);
		m.setTitle("Say Hi to you!");
		m.setContent("This is a test mail.\n The content is:!@#!@EDQWDDSCADCADCACAA");
		m.setReceiver(user4);
		m.setSender(user3);
		user3.getSentMails().add(m);
		user4.getReceivedMails().add(m);
		mailList.add(m);
		
		Mail m1 = new Mail();
		m1.setId("ADREBRHT@!%12SD");
		m1.setRead(false);
		m1.setTitle("Say Hi to you!");
		m1.setContent("This is a test mail.");
		m1.setReceiver(user3);
		m1.setSender(user4);
		user4.getSentMails().add(m1);
		user3.getReceivedMails().add(m1);
		mailList.add(m1);
		
		//定义好友
		friendList = new ArrayList<Friend>();
		Friend f = new Friend();
		f.setDecided(true);
		f.setId("AasSDVAS!@$12CSDNT");
		f.setReceiver(user4);
		f.setSender(user3);
		f.setType("Friend");
		user4.getReceivedFriends().add(f);
		user3.getSentFriends().add(f);
		friendList.add(f);
		
		Friend f1 = new Friend();
		f1.setDecided(true);
		f1.setId("ASDdsadV14!@$#SDNT");
		f1.setReceiver(user4);
		f1.setSender(user2);
		f1.setType("Friend");
		user4.getReceivedFriends().add(f1);
		user2.getSentFriends().add(f1);
		friendList.add(f1);
		
		Friend f2 = new Friend();
		f2.setDecided(true);
		f2.setId("ASDazVA12!@$#SDNT");
		f2.setReceiver(user4);
		f2.setSender(user1);
		f2.setType("Friend");
		user4.getReceivedFriends().add(f2);
		user1.getSentFriends().add(f2);
		friendList.add(f2);
		
		Friend f3 = new Friend();
		f3.setDecided(false);
		f3.setId("ASDVAew12!@!@$#SDNT");
		f3.setReceiver(user5);
		f3.setSender(user4);
		f3.setType("Common");
		user4.getSentFriends().add(f3);
		user5.getReceivedFriends().add(f3);
		friendList.add(f3);
		
		//定义类型
		categoryList = new ArrayList<Category>();
		Category cate = new Category();
		cate.setId("SADCADCQ@!CSA");
		cate.setName("Cate1");
		cate.setSubdomain("Camera");
		cate.setDomain("Security");
		cate.setType("ORG");
		categoryList.add(cate);
		
		Category cate1 = new Category();
		cate1.setId("SADCADCQ@!REA");
		cate1.setName("Cate2");
		cate1.setSubdomain("Sensor");
		cate.setDomain("Security");
		cate1.setType("ORG");
		categoryList.add(cate1);
		
		//定义设备
		deviceList = new ArrayList<Device>();
		Device d = new Device();
		d.setDefault(true);
		d.setId("SADF@QCAS@#ADVCAVV@!!@");
		d.setStatus("Open");
		d.setUser(user4);
		user4.getDevices().add(d);
		deviceList.add(d);
		{
			Driver driver = new Driver();
			driver.setAlias("ASDDSA");
			driver.setCategory(cate);
			driver.setIcon(new byte[120]);
			driver.setId("CADFEWVREW");
			driver.setName("com.huishi.security.camera.huishi");
			driver.setVersion("1.0.0");
			d.setDriver(driver);
		}
		
		//定义系统所有的应用
		consumerList = new ArrayList<Consumer>();
		
		Consumer con = new Consumer();
		con.setAlias("dsa");
		con.setCategory(cate);
		con.setConnectURI("http://www.sina.cn");
		con.setIcon(new byte[100]);
		con.setId("JYTEBWQC@!EDXWQ");
		con.setKey("F$#@$FTRCWCSA@#C@Q");
		con.setName("Sina");
		con.setPermissions(new String[1]);
		con.setScopes(new String[2]);
		con.setSecret("ASDCDSA");
		con.setVersion("1.0.0");
		consumerList.add(con);
		
		Consumer con1 = new Consumer();
		con1.setAlias("dsa");
		con1.setCategory(cate);
		con1.setConnectURI("http://www.163.com");
		con1.setIcon(new byte[100]);
		con1.setId("JYTEBWQC@!EDXWQ1");
		con1.setKey("F$#@$FTRCWCSA@#C@Q");
		con1.setName("163");
		con1.setPermissions(new String[1]);
		con1.setScopes(new String[2]);
		con1.setSecret("ASDCDSA");
		con1.setVersion("1.0.0");
		consumerList.add(con1);
		
		Consumer con2 = new Consumer();
		con2.setAlias("dsaw");
		con2.setCategory(cate1);
		con2.setConnectURI("http://www.sohu.com");
		con2.setIcon(new byte[100]);
		con2.setId("JYTEBWQC@!EDXWQ111");
		con2.setKey("F$#@$FTRCWCSA@#C@Q22");
		con2.setName("Sohu");
		con2.setPermissions(new String[1]);
		con2.setScopes(new String[2]);
		con2.setSecret("ASDCDSAsd");
		con2.setVersion("1.0.0");
		consumerList.add(con2);
		
		applicationList = new ArrayList<Application>();
	}

	public static List<Application> getApplicationList() {
		return applicationList;
	}

	public static List<Consumer> getConsumerList() {
		return consumerList;
	}

	public static List<Category> getCategoryList() {
		return categoryList;
	}

	public static List<Dependency> getDependencyList() {
		return dependencyList;
	}

	public static List<Device> getDeviceList() {
		return deviceList;
	}

	public static List<Friend> getFriendList() {
		return friendList;
	}

	public static List<Mail> getMailList() {
		return mailList;
	}

	public static List<User> getUserList() {
		return userList;
	}
	
}
