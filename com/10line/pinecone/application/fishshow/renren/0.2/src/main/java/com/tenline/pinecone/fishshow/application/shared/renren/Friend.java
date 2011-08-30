package com.tenline.pinecone.fishshow.application.shared.renren;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;

public class Friend extends BaseModel implements ModelData{
//	/**<id> 	子节点表示好友的用户ID*/
//	private String id;
//	/**<name> 	子节点表示好友的名字*/
//	private String name;
//	/**<headurl> 	子节点表示好友的头像*/
//	private String headurl;
//	/**<headurl_with_logo> 	带有校内logo的头像*/
//	private String headurl_with_logo;
//	/**<tinyurl_with_logo> 	带有校内logo的小头像*/
//	private String tinyurl_with_logo;

	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id",id);
	}
	public String getName() {
		return get("name");
	}
	public void setName(String name) {
		set("name",name);
	}
	public String getHeadurl() {
		return get("headurl");
	}
	public void setHeadurl(String headurl) {
		set("headurl",headurl);
	}
	public String getHeadurl_with_logo() {
		return get("headurl_with_logo");
	}
	public void setHeadurl_with_logo(String headurl_with_logo) {
		set("headurl_with_logo",headurl_with_logo);
	}
	public String getTinyurl_with_logo() {
		return get("tinyurl_with_logo");
	}
	public void setTinyurl_with_logo(String tinyurl_with_logo) {
		set("tinyurl_with_logo",tinyurl_with_logo);
	}
}
