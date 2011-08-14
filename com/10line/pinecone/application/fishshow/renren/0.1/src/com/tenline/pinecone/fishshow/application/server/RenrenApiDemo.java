package com.tenline.pinecone.fishshow.application.server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.tenline.pinecone.fishshow.application.server.renrenapi.RenrenApiClient;
import com.tenline.pinecone.fishshow.application.server.renrenapi.services.FriendsService;

public class RenrenApiDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RenrenApiClient client = new RenrenApiClient("2.4c700b0fde36a79f30a3164d870c6dec.3600.1312729200-251760162");
		FriendsService fs = client.getFriendsService();
		
		JSONArray res = fs.getFriends(0, 500);
		JSONObject o = (JSONObject) res.get(0);
		
		System.out.println(o);
	}

}
