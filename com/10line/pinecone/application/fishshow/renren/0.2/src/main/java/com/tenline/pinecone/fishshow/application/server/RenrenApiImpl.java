package com.tenline.pinecone.fishshow.application.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tenline.pinecone.fishshow.application.client.service.RenrenApi;
import com.tenline.pinecone.fishshow.application.server.renrenapi.RenrenApiClient;
import com.tenline.pinecone.fishshow.application.server.renrenapi.services.FriendsService;
import com.tenline.pinecone.fishshow.application.server.renrenapi.services.InvitationsService;
import com.tenline.pinecone.fishshow.application.server.renrenapi.services.NotificationsService;
import com.tenline.pinecone.fishshow.application.server.renrenapi.services.UserService;
import com.tenline.pinecone.fishshow.application.shared.renren.Friend;

public class RenrenApiImpl extends RemoteServiceServlet implements RenrenApi {

	private static Map<String, RenrenApiClient> clientList = new LinkedHashMap<String, RenrenApiClient>();
	
	public String areFriends(String sessionKey,String users1, String users2) {
		if(clientList.get(sessionKey) != null){
			RenrenApiClient client = clientList.get(sessionKey);
			FriendsService fs = client.getFriendsService();
			return fs.areFriends(users1, users2).toJSONString();
		}
		return null;
	}

	public List<Integer> getFriendIds(String sessionKey,int page, int count) {
		if(clientList.get(sessionKey) != null){
			RenrenApiClient client = clientList.get(sessionKey);
			FriendsService fs = client.getFriendsService();
			return fs.getFriendIds(page, count);
		}
		return null;
	}

	public String getFriends(String sessionKey,int page, int count) {
		if(clientList.get(sessionKey) != null){
			RenrenApiClient client = clientList.get(sessionKey);
			FriendsService fs = client.getFriendsService();
			return fs.getFriends(page, count).toJSONString();
		}
		return null;
	}

	public String getAppFriends(String sessionKey,String fields) {
		if(clientList.get(sessionKey) != null){
			RenrenApiClient client = clientList.get(sessionKey);
			FriendsService fs = client.getFriendsService();
			return fs.getAppFriends(fields).toJSONString();
		}
		return null;
	}

	public void login(String sessionKey) {
		if(clientList.get(sessionKey) == null){
			RenrenApiClient client = new RenrenApiClient(sessionKey);
			clientList.put(sessionKey, client);
		}
	}

	public String getInfo(String sessionKey,String userIds, String fields) {
		if(clientList.get(sessionKey) != null){
			RenrenApiClient client = clientList.get(sessionKey);
			UserService us = client.getUserService();
			return us.getInfo(userIds, fields).toJSONString();
		}
		return null;
	}

	public boolean hasAppPermission(String sessionKey,String extPerm, int userId) {
		if(clientList.get(sessionKey) != null){
			RenrenApiClient client = clientList.get(sessionKey);
			UserService us = client.getUserService();
			return us.hasAppPermission(extPerm, userId);
		}
		return false;
	}

	public int getLoggedInUser(String sessionKey) {
		if(clientList.get(sessionKey) != null){
			RenrenApiClient client = clientList.get(sessionKey);
			UserService us = client.getUserService();
			return us.getLoggedInUser();
		}
		return 0;
	}

	public boolean isAppUser(String sessionKey,String userId) {
		if(clientList.get(sessionKey) != null){
			RenrenApiClient client = clientList.get(sessionKey);
			UserService us = client.getUserService();
			try{
				int id = Integer.parseInt(userId);
				return us.isAppUser(id);
			}catch(NumberFormatException e){
				return us.isAppUser(0);
			}
		}
		return false;
	}

	public String createLink(String sessionKey, int domain) {
		if(clientList.get(sessionKey) != null){
			RenrenApiClient client = clientList.get(sessionKey);
			InvitationsService is = client.getInvitationsService();
			return is.createLink(0);
		}
		return null;
	}

	public boolean send(String sessionKey, String toIds, String notification) {
		if(clientList.get(sessionKey) != null){
			RenrenApiClient client = clientList.get(sessionKey);
			NotificationsService ns = client.getNotificationsService();
			if(ns.send(toIds, notification) > 0){
				return true;
			}else{
				return false;
			}
		}
		
		return false;
	}

	public List<Friend> getAllFriends(String sessionKey) {
	
		ArrayList<Friend> list = new ArrayList<Friend>();
		
		if(clientList.get(sessionKey) != null){
			RenrenApiClient client = clientList.get(sessionKey);
			FriendsService fs = client.getFriendsService();
			
			JSONArray ja = new JSONArray();
			int iCount = 0;
			JSONArray res = new JSONArray();
			while((res = fs.getFriends(iCount+1, 500)) .size() > 0){
				List<Object> l = res.subList(0, res.size()-1);
				for(Object o:l){
					ja.add(o);
				}
				iCount++;
			}
			
			for(int i=0;i<ja.size();i++){
				JSONObject o = (JSONObject) ja.get(i);
				Friend f = new Friend();
				f.setId(o.get("id").toString());
				f.setName(o.get("name").toString());
				f.setHeadurl(o.get("headurl").toString());
				f.setTinyurl_with_logo(o.get("tinyurl").toString());
				list.add(f);
			}
			
		}
		
		return list;
	}
}
