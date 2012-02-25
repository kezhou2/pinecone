package com.tenline.pinecone.platform.web.store.server;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import com.google.api.client.auth.oauth.OAuthCallbackUrl;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.ApplicationAPI;
import com.tenline.pinecone.platform.sdk.CategoryAPI;
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.FriendAPI;
import com.tenline.pinecone.platform.sdk.MailAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.sdk.oauth.AuthorizationAPI;
import com.tenline.pinecone.platform.web.store.client.PineconeService;
import com.tenline.pinecone.platform.web.store.shared.ApplicationInfo;
import com.tenline.pinecone.platform.web.store.shared.CategoryInfo;
import com.tenline.pinecone.platform.web.store.shared.ConsumerInfo;
import com.tenline.pinecone.platform.web.store.shared.DeviceInfo;
import com.tenline.pinecone.platform.web.store.shared.FriendInfo;
import com.tenline.pinecone.platform.web.store.shared.MailInfo;
import com.tenline.pinecone.platform.web.store.shared.UserInfo;

public class PineconeServiceImpl extends RemoteServiceServlet implements
		PineconeService {

	private static final long serialVersionUID = -6247861007434096857L;
	
	private static final String HOST = "pinecone.cloudfoundry.com";
	private static final String PORT = "80";
	
	@SuppressWarnings("unused")
	private static final String APP_STORE_CONSUMER_ID = "3";
	@SuppressWarnings("unused")
	private static final String APP_STORE_CATEGORY_ID = "2";
	private static final String APP_STORE_CONSUMER_KEY = "ed9be7a8-6162-48c7-af7d-279ebaff3d1a";
	private static final String APP_STORE_CONSUMER_SECRET = "4069574f-4134-41ce-85f3-48fddadd0fea";
	private static String APP_STORE_TOKEN = "";
	private static String APP_STORE_SECRET = "";
	private static String APP_STORE_VERIFIER = "";
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		AuthorizationAPI authorizationAPI = new AuthorizationAPI(HOST, PORT, null);
		APIResponse response = authorizationAPI.requestToken(APP_STORE_CONSUMER_KEY, APP_STORE_CONSUMER_SECRET, null);
		if (response.isDone()) {
			APP_STORE_TOKEN = ((OAuthCredentialsResponse) response.getMessage()).token;
			APP_STORE_SECRET = ((OAuthCredentialsResponse) response.getMessage()).tokenSecret;
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = authorizationAPI.authorizeToken(APP_STORE_TOKEN);
		if (response.isDone()) {
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = authorizationAPI.confirmAuthorization(APP_STORE_TOKEN, "yes");
		if (response.isDone()) {
			APP_STORE_TOKEN = ((OAuthCallbackUrl) response.getMessage()).token;
			APP_STORE_VERIFIER = ((OAuthCallbackUrl) response.getMessage()).verifier;
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = authorizationAPI.accessToken(
						APP_STORE_CONSUMER_KEY, 
						APP_STORE_CONSUMER_SECRET,
						APP_STORE_TOKEN, 
						APP_STORE_SECRET, 
						APP_STORE_VERIFIER);
		if (response.isDone()) {
			APP_STORE_TOKEN = ((OAuthCredentialsResponse) response.getMessage()).token;
			APP_STORE_SECRET = ((OAuthCredentialsResponse) response.getMessage()).tokenSecret;
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User login(String name, String pwd) {
		System.out.println("func:login");
		UserAPI api = new UserAPI(HOST,PORT,null);
		try {
			APIResponse response = api.show("name=='"+name+"'&&password=='"+pwd+"'");
			
			if (response.isDone()) {
				Collection<User> users = (Collection<User>)response.getMessage();
				for(User u:users){
					System.out.println("**************************");
					System.out.println("User Name:"+u.getName());
					return u;
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void logout(String id) {}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ApplicationInfo> getAppInfo(UserInfo userInfo) {
		System.out.println("func:getAppInfo");
		
		List<ApplicationInfo> list = new ArrayList<ApplicationInfo>();
		ApplicationAPI api = new ApplicationAPI(HOST,PORT,null);
		
		try {
			APIResponse response = api.showByUser("id=='"+userInfo.getUser().getId()+"'");
			
			if (response.isDone()) {
				Collection<Application> apps = (Collection<Application>)response.getMessage();
				for(Application app:apps){
					System.out.println("**************************");
					System.out.println("Application id:"+app.getId());
					System.out.println("Application consumer name:"+app.getConsumer().getName());
					System.out.println("Application Status:"+app.getStatus());
				}	
				
				for(Application app:apps){
					list.add(new ApplicationInfo(app));
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerInfo> getAllConsumerInfo() {
		System.out.println("func:getAllConsumers");
		
		List<ConsumerInfo> list = new ArrayList<ConsumerInfo>();
		ConsumerAPI api = new ConsumerAPI(HOST,PORT,null);
		
		try {
			APIResponse response = api.show("all");
			
			if (response.isDone()) {
				Collection<Consumer> cons = (Collection<Consumer>)response.getMessage();
				for(Consumer con:cons){
					System.out.println("**************************");
					System.out.println("Consumer Name:"+con.getName());
					System.out.println("Consumer Uri:"+con.getConnectURI());
				}
				
				for(Consumer con:cons){
					list.add(new ConsumerInfo(con));
				}
				
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public void deleteAppInfo(UserInfo userInfo, ApplicationInfo appInfo) {
		System.out.println("func:deleteApplication");
		ApplicationAPI api = new ApplicationAPI(HOST,PORT,null);
		
		try {
			APIResponse response = api.delete(appInfo.getApplication().getId());
			
			if (response.isDone()) {
				System.out.println(response.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addApplication(UserInfo userInfo, ConsumerInfo consumerInfo) {
		System.out.println("func:addApplication");
		ApplicationAPI api = new ApplicationAPI(HOST,PORT,null);
		
		Application app = new Application();
		app.setConsumer(consumerInfo.getConsumer());
		app.setDefault(false);
		app.setStatus("Close");
		app.setUser(userInfo.getUser());
		
		try {
			APIResponse response = api.create(app);
			
			if (response.isDone()) {
				System.out.println(response.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsumerInfo getConsumerById(String consumerId) {
		System.out.println("func:getConsumerById");
		ConsumerAPI api = new ConsumerAPI(HOST,PORT,null);
		
		try {
			APIResponse response = api.show("id=='"+consumerId+"'");
			
			if (response.isDone()) {
				Collection<Consumer> cons = (Collection<Consumer>)response.getMessage();
				for(Consumer con:cons){
					System.out.println("**************************");
					System.out.println("Consumer Name:"+con.getName());
					System.out.println("Consumer Uri:"+con.getConnectURI());
					return new ConsumerInfo(con);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FriendInfo> getFriends(UserInfo userInfo) {
		System.out.println("func:getFriends");
		FriendAPI api = new FriendAPI(HOST,PORT,null);
		
		ArrayList<FriendInfo> list = new ArrayList<FriendInfo>(); 
		
		try {
			APIResponse response = api.showByReceiver("id=='"+userInfo.getUser().getId()+"'");
			if (response.isDone()) {
				Collection<Friend> friends = (Collection<Friend>)response.getMessage();
				for(Friend f:friends){
					list.add(new FriendInfo(f));
				}
			}
			response = api.showBySender("id='"+userInfo.getUser().getId()+"'");
			if (response.isDone()) {
				Collection<Friend> friends = (Collection<Friend>)response.getMessage();
				for(Friend f:friends){
					list.add(new FriendInfo(f));
				}
			}
			for(FriendInfo f:list){
				System.out.println("**************************");
				System.out.println("Friend type:"+f.getType());
				System.out.println("Friend sender name:"+f.getSender().getName());
				System.out.println("Friend receiver name:"+f.getReceiver().getName());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public UserInfo register(UserInfo userInfo) {
		System.out.println("func:register");
		UserAPI api = new UserAPI(HOST,PORT,null);
		
		User user = new User();
		user.setAvatar(userInfo.getAvatar());
		user.setEmail(userInfo.getEmail());
		user.setName(userInfo.getName());
		user.setPassword(userInfo.getPassword());
		try {
			APIResponse response = api.create(user);
			
			if (response.isDone()) {
				System.out.println(response.getMessage());
				User u = (User)response.getMessage();
				return new UserInfo(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isUserExist(UserInfo userInfo) {
		System.out.println("func:isUserExist");
		UserAPI api = new UserAPI(HOST,PORT,null);
		try {
			APIResponse response = api.show("name=='"+userInfo.getUser().getName()+"' && password=='"+userInfo.getUser().getPassword()+"'");
			
			if (response.isDone()) {
				Collection<User> col = (Collection<User>)response.getMessage();
				System.out.println("is exist?:"+(col.size()>0?true:false));
				if(col.size() > 0){
					return true;
				}else{
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceInfo> getAllDevice(UserInfo userInfo) {
		System.out.println("func:getAllDevice");
		
		List<DeviceInfo> list = new ArrayList<DeviceInfo>();
		DeviceAPI api = new DeviceAPI(HOST,PORT,null);
		
		try {
			APIResponse response = api.showByUser(
					"id=='"+userInfo.getUser().getId()+"", 
					APP_STORE_CONSUMER_KEY,
					APP_STORE_CONSUMER_SECRET,
					APP_STORE_TOKEN, 
					APP_STORE_SECRET);
			if (response.isDone()) {
				Collection<Device> devices = (Collection<Device>)response.getMessage();
				for(Device d:devices){
					System.out.println("**************************");
					System.out.println("Device status:"+d.getStatus());
					System.out.println("Device driver name:"+d.getDriver().getName());
				}
				
				for(Device d:devices){
					list.add(new DeviceInfo(d));
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> getUsersByPage(int start, int pageSize) {
		System.out.println("func:getUsersByPage");
		
		List<UserInfo> list = new ArrayList<UserInfo>();
		UserAPI api = new UserAPI(HOST,PORT,null);
		
		try {
			APIResponse response = api.show("all");
			if (response.isDone()) {
				Collection<User> users = (Collection<User>)response.getMessage();
				for(User u:users){
					System.out.println("**************************");
					System.out.println("User name:"+u.getName());
					System.out.println("User password:"+u.getPassword());
					System.out.println("User email:"+u.getEmail());
				}
				
				for(User u:users){
					list.add(new UserInfo(u));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> getUsersByPage1(UserInfo userInfo, int start, int pageSize) {
		System.out.println("func:getUsersByPage1");
		
		List<UserInfo> list = new ArrayList<UserInfo>();
		UserAPI api = new UserAPI(HOST,PORT,null);
		
		try {
			APIResponse response = api.show("all");
			if (response.isDone()) {
				Collection<User> users = (Collection<User>)response.getMessage();
				for(User u:users){
					if(u.getId().equals(userInfo.getUser().getId())){
						users.remove(u);
						break;
					}
				}
				
				for(User u:users){
					System.out.println("**************************");
					System.out.println("User name:"+u.getName());
					System.out.println("User password:"+u.getPassword());
					System.out.println("User email:"+u.getEmail());
				}
				
				for(User u:users){
					list.add(new UserInfo(u));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public FriendInfo addFriend(UserInfo sender, UserInfo receiver, String type) {		
		System.out.println("func:addFriend");
		
		FriendAPI api = new FriendAPI(HOST,PORT,null);
		
		Friend f = new Friend();
		f.setDecided(false);
		f.setReceiver(receiver.getUser());
		f.setSender(sender.getUser());
		f.setType(type);
		
		try {
			APIResponse response = api.create(f);
			if (response.isDone()) {
				System.out.println("Friend:"+response.getMessage());
				Friend friend = (Friend)response.getMessage();
				return new FriendInfo(friend);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean deleteFriend(UserInfo userInfo, FriendInfo friendInfo) {
		System.out.println("func:deleteFriend");
		
		FriendAPI api = new FriendAPI(HOST,PORT,null);
		
		try {
			APIResponse response = api.delete(friendInfo.getFriend().getId());
			if (response.isDone()) {
				System.out.println("Friend:"+response.getMessage());
			}
			return response.isDone();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryInfo> getCategorys() {
		System.out.println("func:getCategorys");
		List<CategoryInfo> list = new ArrayList<CategoryInfo>();
		CategoryAPI api = new CategoryAPI(HOST,PORT,null);
		
		try {
			APIResponse response = api.show("all");
			if (response.isDone()) {
				Collection<Category> col = (Collection<Category>)response.getMessage();
				for(Category c:col){
					System.out.println("**************************");
					System.out.println("Category name:"+c.getName());
					System.out.println("Category type:"+c.getType());
				}
				
				for(Category c:col){
					list.add(new CategoryInfo(c));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerInfo> getConsumerByCategory(CategoryInfo category) {
		System.out.println("func:getConsumerByCategory");
		
		List<ConsumerInfo> list = new ArrayList<ConsumerInfo>();
		ConsumerAPI api = new ConsumerAPI(HOST,PORT,null);
		
		try {
			APIResponse response = api.showByCategory("id=='"+category.getCategory().getId()+"'");
			if (response.isDone()) {
				Collection<Consumer> col = (Collection<Consumer>)response.getMessage();
				for(Consumer c:col){
					System.out.println("**************************");
					System.out.println("Consumer name:"+c.getName());
					System.out.println("Consumer Uri:"+c.getConnectURI());
				}
				
				for(Consumer c:col){
					list.add(new ConsumerInfo(c));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public void changeAppStatus(ApplicationInfo appInfo, String status) {
		System.out.println("func:changeAppStatus");
		ApplicationAPI api = new ApplicationAPI(HOST,PORT,null);
		
		appInfo.getApplication().setStatus(status);
		try {
			APIResponse response = api.update(appInfo.getApplication());
			
			if (response.isDone()) {
				System.out.println(response.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean sendMail(UserInfo sender, UserInfo receiver, MailInfo mailInfo) {
		System.out.println("func:sendMail");
		MailAPI api = new MailAPI(HOST,PORT,null);
		
		mailInfo.getMail().setSender(sender.getUser());
		mailInfo.getMail().setReceiver(receiver.getUser());
		try {
			APIResponse response = api.create(mailInfo.getMail());
			System.out.println(response.getMessage());
			return response.isDone();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MailInfo> getReceiveMails(UserInfo userInfo) {
		System.out.println("func:getReceiveMails");
		List<MailInfo> list = new ArrayList<MailInfo>();
		MailAPI api = new MailAPI(HOST,PORT,null);
		
		try {
			APIResponse response = api.showByReceiver("id=='"+userInfo.getUser().getId()+"'");
			if(response.isDone()){
				Collection<Mail> mails = (Collection<Mail>)response.getMessage();
				for(Mail m:mails){
					System.out.println("**************************");
					System.out.println("Mail title:"+m.getTitle());
					System.out.println("Mail content:"+m.getContent());
				}
				for(Mail m:mails){
					list.add(new MailInfo(m));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MailInfo> getSendMails(UserInfo userInfo) {
		System.out.println("func:getSendMails");

		List<MailInfo> list = new ArrayList<MailInfo>();
		MailAPI api = new MailAPI(HOST,PORT,null);
		
		try {
			APIResponse response = api.showBySender("id=='"+userInfo.getUser().getId()+"'");
			if(response.isDone()){
				Collection<Mail> mails = (Collection<Mail>)response.getMessage();
				for(Mail m:mails){
					System.out.println("**************************");
					System.out.println("Mail title:"+m.getTitle());
					System.out.println("Mail content:"+m.getContent());
				}
				for(Mail m:mails){
					list.add(new MailInfo(m));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MailInfo> getUnreadMails(UserInfo userInfo) {
		System.out.println("func:getUnreadMails");
		MailAPI api = new MailAPI(HOST,PORT,null);
		
		ArrayList<MailInfo> list = new ArrayList<MailInfo>();
		try {
			APIResponse response = api.showByReceiver("id=='"+userInfo.getUser().getId()+"'");
			if(response.isDone()){
				Collection<Mail> mails = (Collection<Mail>)response.getMessage();
				for(Mail m:mails){
					if(!m.isRead()){
						list.add(new MailInfo(m));
					}
				}
			}
			
			for(MailInfo m:list){
				System.out.println("**************************");
				System.out.println("Mail title:"+m.getTitle());
				System.out.println("Mail content:"+m.getContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public void setMailRead(MailInfo mailInfo, boolean isRead) {
		System.out.println("func:setMailRead");
		MailAPI api = new MailAPI(HOST,PORT,null);
		
		mailInfo.getMail().setRead(isRead);
		try {
			APIResponse response = api.update(mailInfo.getMail());
			if(response.isDone()){
				System.out.println(response.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FriendInfo> getUnDecideFriends(UserInfo userInfo) {
		System.out.println("func:getUnDecideFriends");
		FriendAPI api = new FriendAPI(HOST,PORT,null);
		
		ArrayList<FriendInfo> list = new ArrayList<FriendInfo>();
		try {
			APIResponse response = api.showByReceiver("id=='"+userInfo.getUser().getId()+"'");
			if(response.isDone()){
				Collection<Friend> col = (Collection<Friend>)response.getMessage();
				for(Friend f:col){
					if(!f.isDecided()){
						list.add(new FriendInfo(f));
					}
				}
				
				for(FriendInfo f:list){
					System.out.println("**************************");
					System.out.println("Friend type:"+f.getType());
					System.out.println("Friend sender name:"+f.getSender().getName());
					System.out.println("Friend receiver name:"+f.getReceiver().getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void acceptFriendInvite(FriendInfo friendInfo) {
		System.out.println("func:acceptFriendInvite");
		FriendAPI api = new FriendAPI(HOST,PORT,null);
		
		friendInfo.getFriend().setDecided(true);
		
		try {
			APIResponse response = api.update(friendInfo.getFriend());
			if(response.isDone()){
				System.out.println(response.getMessage());
			}else{
				System.out.println(response.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
