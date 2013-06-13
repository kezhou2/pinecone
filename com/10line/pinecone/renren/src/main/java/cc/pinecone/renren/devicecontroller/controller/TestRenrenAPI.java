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
		JSONArray users = api.getUserService().getInfo("251760162", fields,new AccessToken("2.3241ab66ef2345cdadbc89a0572b5de0.3600.1371139200-251760162"));
		JSONObject u = JsonUtils.getIndexJSONObject(users, 0);
		String name = JsonUtils.getValue(u, "name", String.class);
		String email_hash = JsonUtils.getValue(u, "email_hash", String.class);
		System.out.println(name);
	}

}
