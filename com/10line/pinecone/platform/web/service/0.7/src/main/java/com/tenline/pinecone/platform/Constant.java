package com.tenline.pinecone.platform;

public final class Constant {
	
	public static final String REST_URL = "http://localhost:8080/service";
//	public static final String REST_URL = "http://www.pinecone.cc";
//	public static final String CHANNEL_URL = "tcp://m2m.eclipse.org:1883";
	public static final String CHANNEL_URL = "tcp://www.pinecone.cc:1883";
	public static final String HISTORY_URL = "www.pinecone.cc";
	private static String userid = "";
	private static String access_token = "";
	private static String sessionkey = "";
	private static final String CACHE_PATH="config-cache";
	private static final String ADMIN_ID="admin";
	private static final String ADMIN_PASSWORD="admin";
	
	public static String getAdminId() {
		return ADMIN_ID;
	}
	public static String getAdminPassword() {
		return ADMIN_PASSWORD;
	}
	public static String getCachePath() {
		return CACHE_PATH;
	}
	public static String getUserid() {
		return userid;
	}
	public static void setUserid(String userid) {
		Constant.userid = userid;
	}
	public static String getAccess_token() {
		return access_token;
	}
	public static void setAccess_token(String access_token) {
		Constant.access_token = access_token;
	}
	public static String getSessionkey() {
		return sessionkey;
	}
	public static void setSessionkey(String sessionkey) {
		Constant.sessionkey = sessionkey;
	}
}
