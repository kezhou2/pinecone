package cc.pinecone.renren.devicecontroller.dao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.param.impl.AccessToken;
import com.renren.api.client.utils.JsonUtils;
import com.tenline.pinecone.platform.model.User;

public class RenrenApi {

	public User getRenrenUser(String id, String access_token) {
		RenrenApiClient api = RenrenApiClient.getInstance();
		String fields = "name,email_hash, sex,star,birthday,tinyurl,headurl,mainurl,hometown_location,hs_history,university_history,work_history,contact_info";
		JSONArray users = api.getUserService().getInfo("251760162", fields,
				new AccessToken(access_token));
		if (users.size() > 0) {
			JSONObject u = JsonUtils.getIndexJSONObject(users, 0);
			String name = JsonUtils.getValue(u, "name", String.class);
			String email_hash = JsonUtils.getValue(u, "email_hash",
					String.class);
			User user = new User();
			user.setName(name);
			user.setEmail(email_hash);
			return user;
		} else {
			return null;
		}
	}
}
