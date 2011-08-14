package com.tenline.pinecone.fishshow.application.shared.renren;

public class User {
	/**<uid> 	表示用户id*/
	private String uid;
	/**<name> 	表示用户名*/
	private String name;
	/**<sex> 	表示性别，值1表示男性；值0表示女性*/
	private String sex;
	/**<star> 	表示是否为星级用户，校内中值1表示是；值0表示不是,开心中0表示非真实姓名和头像，1表示非真实姓名，2表示非真实头像，3表示真实用户*/
	private String star;
	/**<zidou> 	表示是否为vip用户，值1表示是；值0表示不是*/
	private String zidou;
	/**<vip> 	表示是否为vip用户等级，前提是zidou节点必须为1*/
	private String vip;
	/**<birthday> 	表示出生时间，格式为：yyyy-mm-dd，需要自行格式化日期显示格式。注：年份60后，实际返回1760-mm-dd；70后，返回1770-mm-dd；80后，返回1780-mm-dd；90后，返回1790-mm-dd*/
	private String birthday;
	/**<email_hash> 	用户经过验证的email的信息字符串：email通过了connect.registerUsers接口。字符串包含的email经过了crc32和md5的编码*/
	private String email_hash;
	/**<tinyurl> 	表示头像链接 50*50大小*/
	private String tinyurl;
	/**<headurl> 	表示头像链接 100*100大小*/
	private String headurl;
	/**<mainurl> 	表示头像链接 200*200大小*/
	private String mainurl;
	/**<hometown_location> 	表示家乡信息*/
	private String hometown_location;
//	/**<country>(子节点) 	表示所在国家*/
//	private String country;
//	/**<province>（子节点） 	表示所在省份*/
//	private String province;
//	/**<city>（子节点） 	表示所在城市*/
//	private String city;
//	/**<work_info> 	表示工作信息*/
//	private String work_info;
//	/**<company_name>（子节点） 	表示所在公司*/
//	private String company_name;
//	/**<description>（子节点） 	表示工作描述*/
//	private String description;
//	/**<start_date>(子节点) 	表示入职时间*/
//	private String start_date;
//	/**<end_date>（子节点） 	离职时间*/
//	private String end_date;
//	/**<university_info> 	表示就读大学信息*/
//	private String university_info;
//	/**<name>（子节点） 	表示大学名*/
//	private String
//	/**<year>（子节点） 	表示入学时间*/
//	/**<department>（子节点） 	表示学院*/
//	/**<hs_info> 	表示就读高中学校信息*/
//	/**<name>（子节点） 	表示高中学校名*/
//	/**<grad_year>（子节点） 	表示入学时间*/
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getZidou() {
		return zidou;
	}
	public void setZidou(String zidou) {
		this.zidou = zidou;
	}
	public String getVip() {
		return vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getEmail_hash() {
		return email_hash;
	}
	public void setEmail_hash(String email_hash) {
		this.email_hash = email_hash;
	}
	public String getTinyurl() {
		return tinyurl;
	}
	public void setTinyurl(String tinyurl) {
		this.tinyurl = tinyurl;
	}
	public String getHeadurl() {
		return headurl;
	}
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	public String getMainurl() {
		return mainurl;
	}
	public void setMainurl(String mainurl) {
		this.mainurl = mainurl;
	}
	public String getHometown_location() {
		return hometown_location;
	}
	public void setHometown_location(String hometown_location) {
		this.hometown_location = hometown_location;
	}
}
