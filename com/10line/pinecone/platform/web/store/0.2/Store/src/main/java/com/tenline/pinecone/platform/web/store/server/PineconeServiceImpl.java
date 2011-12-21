package com.tenline.pinecone.platform.web.store.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;

import com.google.api.client.auth.oauth.OAuthCallbackUrl;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.UserRelation;
import com.tenline.pinecone.platform.sdk.ApplicationAPI;
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.UserRelationAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.sdk.oauth.AuthorizationAPI;
import com.tenline.pinecone.platform.web.store.client.PineconeService;
import com.tenline.pinecone.platform.web.store.client.model.AppInfo;
import com.tenline.pinecone.platform.web.store.client.model.ConsumerInfo;
import com.tenline.pinecone.platform.web.store.client.model.DeviceInfo;
import com.tenline.pinecone.platform.web.store.client.model.UserInfo;

public class PineconeServiceImpl extends RemoteServiceServlet implements
		PineconeService {

	private static final long serialVersionUID = -6247861007434096857L;
	
	private final String PLATFORM_NAME = "AppStore";
	private final String CONSUMER_KEY = "8f448244-abb8-4b71-bb4c-93eb7c2125ca";
	private final String CONSUMER_SECRET = "25a12cb5-cc67-4534-adcd-53693c3ec3b9";
	private final String HOST = "localhost";
	private final String PORT = "8080";
	private final String CONTEXT = null;

	protected String token;
	protected String tokenSecret;
	private String verifier;
	
	@Override
	public void init() throws ServletException {
		super.init();
		initOAuth();
	}

	private void initOAuth(){
		
		AuthorizationAPI authorizationAPI = new AuthorizationAPI(HOST, PORT,CONTEXT);
		APIResponse response = authorizationAPI.requestToken(CONSUMER_KEY, CONSUMER_SECRET, null);
		if (response.isDone()) {
			token = ((OAuthCredentialsResponse) response.getMessage()).token;
			tokenSecret = ((OAuthCredentialsResponse) response.getMessage()).tokenSecret;
		} else {
			System.out.println(response.getMessage().toString());
		}
		
		response = authorizationAPI.authorizeToken(token);
		if (response.isDone()) {
			System.out.println("authorizationAPI:"+response.getMessage().toString());
		} else {
			System.out.println(response.getMessage().toString());
		}
		response = authorizationAPI.confirmAuthorization(token, "yes");
		if (response.isDone()) {
			token = ((OAuthCallbackUrl) response.getMessage()).token;
			verifier = ((OAuthCallbackUrl) response.getMessage()).verifier;
		} else {
			System.out.println(response.getMessage().toString());
		}
		
		response = authorizationAPI.accessToken(CONSUMER_KEY, CONSUMER_SECRET, token, tokenSecret, verifier);
		if (response.isDone()) {
			token = ((OAuthCredentialsResponse) response.getMessage()).token;
			tokenSecret = ((OAuthCredentialsResponse) response.getMessage()).tokenSecret;
		} else {
			System.out.println(response.getMessage().toString());
		}
	}
	
	@Override
	public User login(String name, String pwd) {
		System.out.println("login");
		UserAPI userAPI = new UserAPI(HOST,PORT,CONTEXT);
		
		try {
			APIResponse response = userAPI.show("all", CONSUMER_KEY, CONSUMER_SECRET, token, tokenSecret);
			if(response.isDone()){
				@SuppressWarnings("unchecked")
				Collection<User> users = (Collection<User>) response.getMessage();
				for(User u:users){
					if(u.getName().equals(name) && u.getPassword().equals(pwd)){
						return u;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void logout(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AppInfo> getAppInfo(UserInfo userInfo) {
		ApplicationAPI applicationAPI = new ApplicationAPI(HOST,PORT,CONTEXT);
		
		List<AppInfo> list = new ArrayList<AppInfo>();
	
		try{

			APIResponse response = applicationAPI.showByUser("id=='"+
					userInfo.getUser().getId()+"'", CONSUMER_KEY, CONSUMER_SECRET, token, tokenSecret);
			if(response.isDone()){
				@SuppressWarnings("unchecked")
				Collection<Application> apps = (Collection<Application>) response.getMessage();
				for(Application app:apps){
					list.add(new AppInfo(app));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ConsumerInfo> getConsumerInfo(UserInfo userInfo) {
		ConsumerAPI consumerAPI = new ConsumerAPI(HOST,PORT,CONTEXT);
		ApplicationAPI applicationAPI = new ApplicationAPI(HOST,PORT,CONTEXT);
		
		List<ConsumerInfo> list = new ArrayList<ConsumerInfo>();
		
		try{
//			System.out.println("User DI:"+userInfo.getUser().getId());
			APIResponse response = applicationAPI.showByUser("id=='"+
					userInfo.getUser().getId()+"'", CONSUMER_KEY, CONSUMER_SECRET, token, tokenSecret);
			if(response.isDone()){
				@SuppressWarnings("unchecked")
				Collection<Application> apps = (Collection<Application>) response.getMessage();
				for(Application app:apps){
					response = consumerAPI.show(app.getId(), 
							CONSUMER_KEY, CONSUMER_SECRET, token, tokenSecret);
					if(response.isDone()){
						@SuppressWarnings("unchecked")
						Collection<Consumer> cons = (Collection<Consumer>) response.getMessage();
						for(Consumer con:cons){
							list.add(new ConsumerInfo(con));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ConsumerInfo> getAllConsumerInfo() {
		ConsumerAPI consumerAPI = new ConsumerAPI(HOST,PORT,CONTEXT);
		
		List<ConsumerInfo> list = new ArrayList<ConsumerInfo>();
		
		try {
			APIResponse response = consumerAPI.show("all", CONSUMER_KEY, CONSUMER_SECRET, token, tokenSecret);
			if(response.isDone()){
				@SuppressWarnings("unchecked")
				Collection<Consumer> consumers = (Collection<Consumer>) response.getMessage();
				for(Consumer con:consumers){
					if(!con.getDisplayName().equals(PLATFORM_NAME)){
						list.add(new ConsumerInfo(con));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void deleteAppInfo(AppInfo appInfo) {
		ApplicationAPI applicationAPI = new ApplicationAPI(HOST,PORT,CONTEXT);
		
		try {
			APIResponse response = applicationAPI.delete(appInfo.getApplication().getId());
			if (response.isDone()) {
				System.out.println(response.getMessage().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addConsumerToUser(UserInfo userInfo, ConsumerInfo consumerInfo) {
		ApplicationAPI applicationAPI = new ApplicationAPI(HOST,PORT,CONTEXT);
		
		Application app = new Application();
		app.setConsumerId(consumerInfo.getConsumer().getId());
		app.setUser(userInfo.getUser());
		
		APIResponse response;
		try {
			response = applicationAPI.create(app);
			if (response.isDone()) {
				response.getMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public ConsumerInfo getConsumerById(String consumerId) {
		ConsumerAPI consumerAPI = new ConsumerAPI(HOST,PORT,CONTEXT);
		try {
			APIResponse response = consumerAPI.show("id=='"+consumerId+"'", CONSUMER_KEY, CONSUMER_SECRET, token, tokenSecret);
			if(response.isDone()){
				@SuppressWarnings("unchecked")
				Collection<Consumer> cons = (Collection<Consumer>)response.getMessage();
				for(Consumer con:cons){
					return new ConsumerInfo(con);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<UserInfo> getUserRelation(UserInfo userInfo) {
		UserRelationAPI userRelationAPI = new UserRelationAPI(HOST,PORT,CONTEXT);
		UserAPI userAPI = new UserAPI(HOST,PORT,CONTEXT);
		
		List<UserInfo> list = new ArrayList<UserInfo>();
		try {
			System.out.println("**User ID:"+userInfo.getUser().getId());
			APIResponse response = userRelationAPI.showByUser("id=='"+userInfo.getUser().getId()+"'", CONSUMER_KEY, CONSUMER_SECRET, token, tokenSecret);
			if(response.isDone()){
				@SuppressWarnings("unchecked")
				Collection<UserRelation> userRelations = (Collection<UserRelation>)response.getMessage();
				System.out.println("userRelations size:"+userRelations.size());
				for(UserRelation ur:userRelations){
					response = userAPI.show("id=='"+ur.getUserId()+"'", CONSUMER_KEY, CONSUMER_SECRET, token, tokenSecret);
					if(response.isDone()){
						@SuppressWarnings("unchecked")
						Collection<User> users = (Collection<User>) response.getMessage();
						System.out.println("users size:"+users.size());
						for(User u:users){
							list.add(new UserInfo(u));
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public User register(User user) {
		UserAPI userAPI = new UserAPI(HOST,PORT,CONTEXT);
		try {
			APIResponse response = userAPI.show("all", CONSUMER_KEY, CONSUMER_SECRET, token, tokenSecret);
			if(response.isDone()){
				@SuppressWarnings("unchecked")
				Collection<User> users = (Collection<User>) response.getMessage();
				for(User u:users){
					if(u.getName().equals(user.getName()) && u.getPassword().equals(user.getPassword())){
						return null;
					}
				}
				
				response = userAPI.create(user);
				if(response.isDone()){
					return (User) response.getMessage();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean isUserExist(User user) {
		UserAPI userAPI = new UserAPI(HOST,PORT,CONTEXT);
		try {
			APIResponse response = userAPI.show("all", CONSUMER_KEY, CONSUMER_SECRET, token, tokenSecret);
			if(response.isDone()){
				@SuppressWarnings("unchecked")
				Collection<User> users = (Collection<User>) response.getMessage();
				for(User u:users){
					if(u.getName().equals(user.getName())){
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public List<DeviceInfo> getAllDevice(UserInfo userInfo) {
		DeviceAPI api = new DeviceAPI(HOST,PORT,CONTEXT);
		
		List<DeviceInfo> list = new ArrayList<DeviceInfo>();
		try {
			APIResponse response = api.showByUser("id=='"+userInfo.getUser().getId()+"'", CONSUMER_KEY, CONSUMER_SECRET, token, tokenSecret);
			if(response.isDone()){
				@SuppressWarnings("unchecked")
				Collection<Device> devices = (Collection<Device>) response.getMessage();
				for(Device d:devices){
					DeviceInfo di = new DeviceInfo(d);
					list.add(di);
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
