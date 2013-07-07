package cc.pinecone.renren.devicecontroller.controller;

public final class AppConfig {
	public static final String APP_ID = "230784";
	public static final String API_KEY = "24f4342504db4e2f855ea33cffb2385e";
	public static final String APP_SECRET = "8fd5683ba94041c7b373438a73f17a8b";
	
//	public static final String REST_URL = "http://pinecone-service.cloudfoundry.com";
	public static final String REST_URL = "http://www.pinecone.cc";
//	public static final String CHANNEL_URL = "tcp://m2m.eclipse.org:1883";
	public static final String CHANNEL_URL = "tcp://www.pinecone.cc:1883";
	public static final String HISTORY_URL = "www.pinecone.cc";
	private static String userid = "";
	private static String access_token = "";
	private static String sessionkey = "";
	private static final String CACHE_PATH="config-cache";
	
	public static String getCachePath() {
		return CACHE_PATH;
	}
	public static String getUserid() {
		return userid;
	}
	public static void setUserid(String userid) {
		AppConfig.userid = userid;
	}
	public static String getAccess_token() {
		return access_token;
	}
	public static void setAccess_token(String access_token) {
		AppConfig.access_token = access_token;
	}
	public static String getSessionkey() {
		return sessionkey;
	}
	public static void setSessionkey(String sessionkey) {
		AppConfig.sessionkey = sessionkey;
	}
}
