package cc.pinecone.renren.devicecontroller.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.param.impl.AccessToken;
import com.renren.api.client.utils.JsonUtils;

public class TestRenrenAPI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		RenrenApiClient api = RenrenApiClient.getInstance();
		String fields = "name,email_hash, sex,star,birthday,tinyurl,headurl,mainurl,hometown_location,hs_history,university_history,work_history,contact_info";
		JSONArray users = api.getUserService().getInfo("251760162", fields,new AccessToken("230784|6.b587841bc9ed25e211c2e6a9771465ef.2592000.1373781600-251760162"));
		JSONObject u = JsonUtils.getIndexJSONObject(users, 0);
		String name = JsonUtils.getValue(u, "name", String.class);
		String email_hash = JsonUtils.getValue(u, "email_hash", String.class);
		System.out.println(name);
		
		JSONArray friends = api.getFriendsService().getFriends(0, 20, new AccessToken("230784|6.b587841bc9ed25e211c2e6a9771465ef.2592000.1373781600-251760162") );
		System.out.println(friends.toJSONString());
		for(int i=0;i<friends.size();i++){
			System.out.println("==============================");
			JSONObject f = (JSONObject)friends.get(i);
			System.out.println(f.get("id"));
			System.out.println(f.get("tinyurl"));
			System.out.println(f.get("sex"));
			System.out.println(f.get("name"));
			System.out.println(f.get("headurl"));
		}
	}

}
